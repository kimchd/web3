package org.zerock.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zerock.anno.GetMapping;
import org.zerock.anno.PostMapping;
import org.zerock.domain.Board;
import org.zerock.domain.BoardDAO;
import org.zerock.domain.Pager;
import org.zerock.domain.RequestConverter;

public class BoardController {

	@GetMapping("register.do")
	public void registerGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	}
	@PostMapping("register.do")
	public String registerPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
System.out.println("post reg");
		BoardDAO dao = new BoardDAO();
		Board vo = null;
		try {
			vo = (Board)RequestConverter.convertin(request,"org.zerock.domain.Board");
			dao.insertBoardRow(vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "/web3/board/list.do?msg=success";
	}
	
	@GetMapping("list.do")
	public void listShow(HttpServletRequest request, HttpServletResponse response){
		BoardDAO dao = new BoardDAO();
		List<Board> list = null;
		int pageNum = 1;
		int size = 10;
		double ddisplay=0;
		int display = 5;
		
		try {
			pageNum = Integer.parseInt(request.getParameter("page"));// 현재페이지.
			size = Integer.parseInt(request.getParameter("size"));
			ddisplay = Double.parseDouble(request.getParameter("display"));
			display=(int)ddisplay;
		} catch (Exception e) {

		}

		try {

			list = dao.getPage(size,pageNum);

		} catch (Exception e) {
			e.printStackTrace();

		}

		request.setAttribute("list", list);// 페이지에 표시될 리스트.

		try {
			Pager pager = new Pager(pageNum, dao.getCount(),size,display);
			
			request.setAttribute("pageNum",pager );// 하단
 			
			//if()삭제시 빈페이지 발생 -> 처리해야된다. 								// 페이지
																			// 정보.

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		System.out.println("hey?");
		
		
	}
	@PostMapping("list.do")
	public String listPost(HttpServletRequest request, HttpServletResponse response)throws Exception{
	
		
		return "/web3/board/register.do";
	}
	@GetMapping("context.do")
	public void contextget(HttpServletRequest request, HttpServletResponse response)throws Exception{
		BoardDAO dao = new BoardDAO();
		Board vo = null;
		try {
			vo = dao.getLow(Integer.parseInt(request.getParameter("bn")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("BoardVO", vo);
	}
	@PostMapping("context.do")
	public String contextPost(HttpServletRequest request, HttpServletResponse response)throws Exception{
		BoardDAO dao = new BoardDAO();
		String str = request.getParameter("pageNum");
		int x = 0;
		//잘못되면 이새끼
		x=Integer.parseInt(request.getParameter("bn"));
		try {
			dao.delete(x);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "/web3/board/list.do?msg=delete_Success&page="+str;
	}
	
	@GetMapping("update.do")
	public void updateGet(HttpServletRequest request, HttpServletResponse response)throws Exception{
		BoardDAO dao = new BoardDAO();
		
		System.out.println( request.getParameter("bn"));
		Board vo = null;
		try {
			vo = dao.getLow(Integer.parseInt(request.getParameter("bn")));
			request.setAttribute("BoardVO", vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@PostMapping("update.do")
	public String updatePost(HttpServletRequest request, HttpServletResponse response)throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		BoardDAO dao = new BoardDAO();
		Board vo = new Board();
		vo.setBn(Integer.parseInt(request.getParameter("bn")));
		vo.setTitle(request.getParameter("title"));
		vo.setContnd(request.getParameter("contnd"));
		
		try {
			dao.update(vo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "list.do";
	}

}
