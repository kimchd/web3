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

/**
 * Servlet implementation class TestController
 */
@WebServlet(value = "*.do", initParams = { @WebInitParam(name = "board", value = "org.zerock.web.BoardController") })
public class TestController extends HttpServlet {
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
	public TestController() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());

		String httpMethod = request.getMethod();
		String path = request.getServletPath();

		String controllerPath = path.split("/")[1];

		System.out.println(controllerPath);

		Object obj = controllerMap.get(controllerPath);

		Class clz = obj.getClass();

		Method[] methods = clz.getDeclaredMethods();

		for (Method method2 : methods) {

			if (httpMethod.equals("GET")) {
				GetMapping mapping = method2.getAnnotation(GetMapping.class);

				if (mapping != null) {
					String annoValue = mapping.value();

					if (annoValue.equals(path.split("/")[2])) {

						try {
							method2.invoke(obj, null);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

		}

	}
}
