package apps.web;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Customer;

/**
 * Servlet implementation class CreateCust
 */
@WebServlet(name = "createCustomer", urlPatterns = { "/createCustomer" })
public class CreateCust extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get the passed parameters from the form
		int custId = Integer.parseInt(request.getParameter("CustId"));
		String CustName = request.getParameter("CustName");
		int DefAcctId = Integer.parseInt(request.getParameter("DefAcctId"));
		Date EffTime = Date.valueOf(request.getParameter("EffTime"));
		Date ExpTime = Date.valueOf(request.getParameter("ExpTime"));
		
		// check the customer exists!
		if(Customer.getCustomerById(custId) != null){
			response.getWriter().println("Customer with custID : " + Integer.toString(custId) + " exits!");
			return;
		}
		// check the input parameters for the customer
		if(custId != 0 && 
				CustName != null &&
				DefAcctId != 0 &&
				EffTime != null &&
				ExpTime != null
				){
			//response.getWriter().println("SUCCESS");
				System.out.println("Customer id " + Integer.toString(custId));
				try {
					Customer cust = new Customer(custId, CustName, DefAcctId, EffTime, ExpTime);
					if(Customer.createCustomer(cust)){
						response.getWriter().println("SUCCESS");
						//response.sendRedirect("adminPage.jsp");
					}
					else{
						response.getWriter().println("	failed");
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
					response.getWriter().println("Operation	failed! " + e.getMessage());
				}

				
		}
		else{
			response.getWriter().println("Operation	failed");
		}
		
		//response.getWriter().println("SUCCESS");
	}

}
