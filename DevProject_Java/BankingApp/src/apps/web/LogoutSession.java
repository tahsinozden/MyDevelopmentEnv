package apps.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.http.HTTPException;

/**
 * Servlet implementation class LogoutSession
 */
@WebServlet(name = "doLogout", urlPatterns = { "/doLogout" })
public class LogoutSession extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if ((String)request.getSession().getAttribute("userName") != "" &&
				(String)request.getSession().getAttribute("pswd") != "" ){
			request.getSession().setAttribute("userName",null);
			request.getSession().setAttribute("pswd",null);
			response.sendRedirect("login.html");
		}
		else{
			//response.getWriter().println("No session exists!");
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

}
