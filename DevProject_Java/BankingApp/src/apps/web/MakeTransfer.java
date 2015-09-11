package apps.web;

import java.io.IOException;
import java.net.HttpRetryException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.CmAcct;
import models.Customer;

/**
 * Servlet implementation class MakeTransfer
 */
@WebServlet(name = "makeTransfer", urlPatterns = { "/makeTransfer" })
public class MakeTransfer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int reqCustId = Integer.parseInt(request.getParameter("reqCustId"));
		int reqAmt = Integer.parseInt(request.getParameter("reqAmount"));
		String reqCurrency = request.getParameter("reqCurrency");
		Customer currentCust = null;
		ArrayList<CmAcct> curCmAcctLst = null;
		CmAcct curCmAcct = null;
		
		try {
			currentCust = (Customer)request.getSession().getAttribute("Ses_CurrentCustomer");
			curCmAcctLst = (ArrayList<CmAcct>)(request.getSession().getAttribute("Ses_CurrentCmAcctLst"));
		} catch (NullPointerException e) {
			response.getWriter().println("Session attributes are NULL!");
			System.err.println("Session attributes are NULL!");
			return;
		}
		
		// check session attributes
		if( curCmAcctLst == null || currentCust == null){
			response.getWriter().println("Session attributes are NULL!");
			System.err.println("Session attributes are NULL!");
			return;
		}
		// check the transferee
		//int currentCustId = Integer.parseInt((String)request.getSession().getAttribute("custId"));
		int currentCustId = (int)request.getSession().getAttribute("custId");
		if( reqCustId == currentCustId){
			response.sendRedirect("loginSuccessPage.jsp");
			System.err.println("Customer cannot transfer money to himself!!");
			return;
		}
		
		// check transferer has requested currency type account
		boolean hasIt = false;
		for (CmAcct item : curCmAcctLst){
			if(item.getCurrecyType().equals(reqCurrency)){
				hasIt = true;
				curCmAcct = item;
				break;
			}
		}
		if(!hasIt){
			response.getWriter().println("Current customer does not have destination currency type account!");
			System.err.println("Current customer does not have destination currency type account!");
			return;
		}
		
		// check customer exists
		if (Customer.getCustomerById(reqCustId) == null){
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		Customer cust = Customer.getCustomerById(reqCustId);
		// check destionation customer has balance cum record
		CmAcct cm = null;
		// get the balance by customer id
		ArrayList<CmAcct> lstCm = CmAcct.getByCustId(cust.getId());
		if (lstCm == null || lstCm.size() == 0){
			// if there are no records, create a new one with 0 amount
			cm = new CmAcct(cust.getId(), (int)(Math.random()*1000000), cust.getDefAcctId(), cust.getEffTime(),
					cust.getExpTime(), 0, reqCurrency);
			if (!CmAcct.addRecord(cm)){
				response.getWriter().println("Cm record cannot be added!");
			}	
		}
		else{
			for(CmAcct c : lstCm){
				// get the destination currency account
				if (c.getCurrecyType().equals(reqCurrency)){
					cm = c;
					break;
				}
			}
		}
		if(cm == null){
			response.getWriter().println("Cm is null!");
			return;
		}
		
		cm.setAmount(cm.getAmount() + reqAmt);
		curCmAcct.setAmount(curCmAcct.getAmount() - reqAmt);
		
		if(CmAcct.updateByBalId(cm) && CmAcct.updateByBalId(curCmAcct)){
			
			request.getSession().setAttribute("DEfAcctId", cm.getAcctId());
			request.getSession().setAttribute("CustId", 0);
			request.getSession().setAttribute("CustName", "");
			request.getSession().setAttribute("CustId", 0);
			request.getSession().setAttribute("EffTime", cm.getEffTime());
			request.getSession().setAttribute("ExpTime", cm.getExpTime());
			
			response.sendRedirect("queryResult.jsp");
		}
		else{
			response.getWriter().println("Operation Failed!!");
		}
	}

}
