package org.zerock.web;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zerock.anno.GetMapping;
import org.zerock.anno.PostMapping;

/**
 * Servlet implementation class TestController
 */
@WebServlet(value = "*.do", initParams = { @WebInitParam(name = "board", value = "org.zerock.web.BoardController") })
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Map<String, Object> controllerMap;

	@Override
	public void init() throws ServletException {
		System.out.println("init called....");

		controllerMap = new HashMap<>();

		Enumeration<String> en = this.getInitParameterNames();

		while (en.hasMoreElements()) {
			String path = en.nextElement();
			String value = this.getInitParameter(path);
			System.out.println("path: " + path + "value: " + value);
			try {
				controllerMap.put(path, Class.forName(value).newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FrontController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	
	
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String httpMethod = request.getMethod();
		String path = request.getServletPath();

		String prefix = "/WEB-INF/views";

		String next = path.replaceAll(".do", ".jsp");

		String nextPath = prefix + next;

		System.out.println(nextPath);

		// path /board/register.do

		// /WEB-INF/views/board/register.jsp

		String controllerPath = path.split("/")[1];

		System.out.println("class path: " + controllerPath);

		Object obj = controllerMap.get(controllerPath);

		Class clz = obj.getClass();

		Method[] methods = clz.getDeclaredMethods();

		for (Method method2 : methods) {
			System.out.println("method ?: " + method2 + "\n");
			String annoValue = null;
			if (httpMethod.equals("GET")) {
				GetMapping mapping = method2.getAnnotation(GetMapping.class);
				if (mapping == null)continue;
				annoValue = mapping.value();
			} else if (httpMethod.equals("POST")) {
				PostMapping pMapping = method2.getAnnotation(PostMapping.class);
				if (pMapping == null)continue;
				annoValue = pMapping.value();
			}
			if (annoValue.equals(path.split("/")[2])) {
				try {
					Object result = method2.invoke(obj, request, response);
					System.out.println(result);
					if (result == null || result.getClass() == Void.class) {
						System.out.println("void return");
						request.getRequestDispatcher(nextPath).forward(request, response);
					} else if (result.getClass() == String.class) {
						System.out.println("hello?");
						response.sendRedirect((String) result);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} // end catch
			} // end if
		} // end if
	}// end if
}
