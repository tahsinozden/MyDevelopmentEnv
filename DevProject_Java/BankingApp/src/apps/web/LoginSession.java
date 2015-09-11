package apps.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import apps.tools.*;
import models.*;
/**
 * Servlet implementation class LoginSession
 */
@WebServlet(name = "login", urlPatterns = { "/login" })
public class LoginSession extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LoginSession() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().write("you called GET method!");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String pswd = request.getParameter("pswd");
		//Utilities ut = new Utilities();
		boolean authResult = Utilities.getAuthResultV2(userName, pswd);
		if(authResult){
			request.getSession().setAttribute("userName", userName);
			request.getSession().setAttribute("pswd", pswd);
			CustAuth auth = new CustAuth();
			int usrId = auth.getCustAuthByUsrName(userName).getOwnerCustId();
			System.out.println("Custid = " + Integer.toString(usrId) );
			Customer cust = Customer.getCustomerById(usrId);
			if(cust != null){
				HttpSession ses = request.getSession();
				/*
				ses.setAttribute("custId", cust.getId());
				ses.setAttribute("FullName", cust.getName());
				ses.setAttribute("DefAcct", cust.getDefAcctId());
				*/
				System.out.println("Custid = " + Integer.toString(cust.getId()) );
				request.getSession().setAttribute("custId", cust.getId());
				request.getSession().setAttribute("FullName", cust.getName());
				request.getSession().setAttribute("DefAcct", cust.getDefAcctId());
				// set current customer
				request.getSession().setAttribute("Ses_CurrentCustomer", cust);
				
				
				int amount = 0;
				ArrayList<CmAcct> cm = CmAcct.getByAcctID(cust.getDefAcctId());
				if ( cm != null && cm.size() != 0){
					request.getSession().setAttribute("Amount", cm.get(0).getAmount());
					request.getSession().setAttribute("Currency", cm.get(0).getCurrecyType());
				}
				else{
					CmAcct cm_new = new CmAcct(cust.getId(),789987, cust.getDefAcctId(), cust.getEffTime(), cust.getExpTime(), 100, "TRY");
					if (CmAcct.addRecord(cm_new) ){
						request.getSession().setAttribute("Amount", cm_new.getAmount());
						request.getSession().setAttribute("Currency", cm_new.getCurrecyType());
					}
				}
				request.getSession().setAttribute("Ses_CurrentCmAcctLst", cm);
			}
			if(userName.equals("admin")){
				// redirect to admin page
				ArrayList<Customer> lstCust = Customer.getAllCustomers();
				request.setAttribute("lstCust", lstCust);
				response.sendRedirect("adminPage.jsp");
			}
			else{
				// redirect to user page
				response.sendRedirect("loginSuccessPage.jsp");
			}

		}
		else{
			response.sendRedirect("login.html");
		}
		
	}

}
