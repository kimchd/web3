package org.zerock.domain;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class BoardDAO {


	private Connection conn()throws Exception{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.218:1521:XE", "ssipsangtachi", "ssipsangtachi");
		return con;
	}
    
	public void insertBoardRow(Board vo )throws Exception{
		Connection con=conn();

		String sql= "insert into tbl_board(bn,title,contnd,writer) "
				+ "values(seq_board.nextval,?,?,?)";
		
		PreparedStatement psmt= con.prepareStatement(sql);
		psmt.setString(1, vo.getTitle());
		psmt.setString(2, vo.getContnd());
		psmt.setString(3, vo.getWriter());
		psmt.executeUpdate();
		
		psmt.close();
		con.close();
	}

	public static void main(String[] args)throws Exception {
		Class clz =Class.forName("org.zerock.domain.Board");
	
		
		BeanInfo info= Introspector.getBeanInfo(clz);
		PropertyDescriptor[] props= info.getPropertyDescriptors();
		
		for (PropertyDescriptor pro : props) {
		
		   Class propType= pro.getPropertyType();
		   System.out.println(propType);
		}
	}
	
	private Board makeBoardVO(ResultSet rs)throws Exception
	{
		Class clz =Class.forName("org.zerock.domain.Board");
		Object obj=clz.newInstance();
		BeanInfo info= Introspector.getBeanInfo(clz);
		PropertyDescriptor[] props= info.getPropertyDescriptors();
		
		for (PropertyDescriptor pro : props) {
			String name = pro.getName();
			name.toLowerCase();
			Method setMethod = pro.getWriteMethod();
			if(setMethod ==null)continue;
			Class propType= pro.getPropertyType();
		
			if(propType.getName().equals("java.lang.String")){
				setMethod.invoke(obj, rs.getString(name));
			}
			else if(propType.getName().equals("int"))
				setMethod.invoke(obj,rs.getInt(name));
			else if(propType.getName().equals("java.sql.Date")){
				setMethod.invoke(obj,rs.getDate(name));
			}
		}
		return (Board)obj;
	}
	
	public List<Board> getPage(int size, int pageNum)throws Exception{
		String sql=
				"select * from (select /*+INDEX_DESC(tbl_board ,PK_BOARD)*/  rownum rw, title,contnd, bn,regdate,writer from tbl_board"
				+" where rownum<="+size+"*"+pageNum
				+")where rw>"+size+"*("+pageNum+"-1)";
		
		Connection con=conn();
		List<Board> ret= new LinkedList<>();
		
		PreparedStatement psmt= con.prepareStatement(sql);
		psmt.executeUpdate();
		ResultSet rs= psmt.getResultSet();
		
		while(rs.next()){
			ret.add(makeBoardVO(rs));
		}
		//개별 close. 근데 throws를 못하는데? 그럼 try해야되나?
		rs.close();
		psmt.close();
		con.close();
		
	return ret;
	}
	
	public List<Board> getPage(int pageNum)throws Exception {
		
		String sql=
				"select * from (select /*+INDEX_DESC(tbl_board ,PK_BOARD)*/  rownum rw, title,contnd, bn,regdate,writer from tbl_board"
				+" where rownum<=10*"+pageNum
				+")where rw>10*("+pageNum+"-1)";
		
		Connection con=conn();
		List<Board> ret= new LinkedList<>();
		
		PreparedStatement psmt= con.prepareStatement(sql);
		psmt.executeUpdate();
		ResultSet rs= psmt.getResultSet();
		
		while(rs.next()){
			ret.add(makeBoardVO(rs));
		}
		rs.close();
		psmt.close();
		con.close();//개별 close.
		
	return ret;
	}
	
	public Board getLow(int bn) throws Exception{
		Connection con=conn();
		String sql= "select * from tbl_board where bn=?";
		
		PreparedStatement psmt= con.prepareStatement(sql);
		psmt.setInt(1, bn);
		psmt.executeUpdate();
		ResultSet rs= psmt.getResultSet();
		rs.next();
		return makeBoardVO(rs);
	}

	
	public List<Board> select()throws Exception{
		Connection con=conn();

		List<Board> ret= new LinkedList<>();
		
		String sql= "select * from tbl_board";
		
		PreparedStatement psmt= con.prepareStatement(sql);
		psmt.executeUpdate();
		ResultSet rs= psmt.getResultSet();
		
	while(rs.next()){
		ret.add(makeBoardVO(rs));
	 }
	  return ret;
	}
	
	public void delete(int bn)throws Exception{
		Connection con=conn();
		
		String sql= "delete from tbl_board where bn = ?";
		
		PreparedStatement psmt= con.prepareStatement(sql);
		
		psmt.setInt(1, bn);
		
		psmt.executeUpdate();
	}
	public void update(Board vo)throws Exception{
		Connection con = conn();
		String sql = "update tbl_board set title = ?, "
									+ "contnd = ? ,"
									+ "regdate = ?"
									+ "where bn = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, vo.getTitle());
		pstmt.setString(2, vo.getContnd());
		pstmt.setDate(3, new Date(System.currentTimeMillis()));
		pstmt.setInt(4,vo.getBn());
		
		pstmt.executeUpdate();
	}
	public int getCount() throws Exception{
		Connection con = conn();
		String sql = "select count(*) from tbl_board where bn>0";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getResultSet();
		rs.next();
		int count = rs.getInt(1);
		
		return count;
		
	}

	
	
}
