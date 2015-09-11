package apps.tools;

import java.io.Reader;
import java.sql.*;

import javax.swing.text.DefaultEditorKit.CutAction;

import models.*;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;

public class Utilities {
	public boolean getAuthResult(String userName, String pswd){
		// TODO : implement logic here
		if(userName.length() > 0){
			DBConnect con = new DBConnect("admin.db");
			if(con.connect()){
				// connection successful
				boolean res = con.runStatement("SELECT * FROM CUST_AUTH WHERE USER_NAME = '" + userName + "';");
				if(res){
					ResultSet set = con.getResultSet();
					if(set != null){
						try {
							String dbPass = set.getNString("PASSWORD");
							if(dbPass == pswd){
								// auth pass
								con.disconnect();
								return true;
							}
							else {
								con.disconnect();
								return false;
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return false;
						}
					}
					else {
						con.disconnect();
						return false;
					}
				}
				else return false;
			}
			else return false;
			
		}
		else return false;
	}
	
	public static boolean getAuthResultV2(String userName, String pswd){
		// TODO : implement logic here
		boolean authRes = false;
		if(userName.length() > 0){
			CustAuth cust = new CustAuth();
			cust = cust.getCustAuthByUsrName(userName);
			if(cust != null){
				String usrPass = cust.getPassword();
				if(usrPass.equals(pswd)){
					authRes = true;
				}
			}
		}
		
		return authRes;
	}
	/*
	public static void main(String[] args){
		
		CustAuth cust = new CustAuth();
		cust = cust.getCustAuthByUsrName("tahsin1");
		if(cust != null){
			System.out.println(cust.getPassword());
		}
		else{
			System.out.println("No records found!");
		}
		
		if (getAuthResultV2("tahsin", "1988651")){
			System.out.println("YES");
		}
		else{
			System.out.println("NO");
		}
	
		    
	}
	*/
}
