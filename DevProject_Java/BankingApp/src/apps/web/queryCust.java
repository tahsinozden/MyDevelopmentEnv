package apps.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.http.HTTPException;

import models.Customer;

/**
 * Servlet implementation class queryCust
 */
@WebServlet(name = "query_customer", urlPatterns = { "/query_customer" })
public class queryCust extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int custId = Integer.parseInt(request.getParameter("CustId"));
		Customer cust =  Customer.getCustomerById(custId);
		if(cust != null){
			request.getSession().setAttribute("CustId", cust.getId());
			request.getSession().setAttribute("CustName", cust.getName());
			request.getSession().setAttribute("DEfAcctId", cust.getDefAcctId());
			request.getSession().setAttribute("CustId", cust.getId());
			request.getSession().setAttribute("EffTime", cust.getEffTime());
			request.getSession().setAttribute("ExpTime", cust.getExpTime());
			response.sendRedirect("queryResult.jsp");
		}
		else{
			//response.getWriter().write("No matches!");
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			
			//response.sendRedirect("queryResult.jsp");
		}
	}

}
