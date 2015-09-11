import java.util.ArrayList;

import models.*;

public class ImpAcct2CustUnitTest {
	public static void main(String[] args){
		try {
			
			ArrayList<Acct2Cust> cust = Acct2Cust.getAccountByCustomer(1111);
			if(cust != null){
				System.out.println(((Acct2Cust)cust.get(0)).getAcctId());
			}
			else{
				System.out.println("Fetch no records!");
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + "  " + e.getMessage());
			
		}
		
		Customer cust = Customer.getCustomerById(10004);
		if (Acct2Cust.addAcct2Cust(cust)){
			System.out.println("SUCCESS!");
		}
		else{
			System.out.println("FAILED!");
		}
	}
}
