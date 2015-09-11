package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.sql.DataSource;

public class CustAuth {
	private String userName;
	private String password;
	private int ownerCustId;
	private Date effTime;
	private Date expTime;
	
	public CustAuth(String name,
					String pswd,
					int custId,
					Date effT,
					Date expT) {
		this.userName = name;
		this.password = pswd;
		this.ownerCustId = custId;
		this.effTime = effT;
		this.expTime = expT;
		
	}
	public CustAuth() {
		// default constructor
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getOwnerCustId() {
		return ownerCustId;
	}
	public void setOwnerCustId(int ownerCustId) {
		this.ownerCustId = ownerCustId;
	}
	public Date getEffTime() {
		return effTime;
	}
	public void setEffTime(Date effTime) {
		this.effTime = effTime;
	}
	public Date getExpTime() {
		return expTime;
	}
	public void setExpTime(Date expTime) {
		this.expTime = expTime;
	}
	
	public CustAuth getCustAuthByUsrName(String name){
		/*	
		DataSource ds = null;
			
			  try {
					Context ctx = new InitialContext();
					ds = (DataSource)ctx.lookup("admin.db");
				  } catch (NamingException e) {
					e.printStackTrace();
				  }
			*/
		   CustAuth tmpCustAuth = null;
		   Connection c = null;
		    Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:admin.db");
		      //c = ds.getConnection();
		      System.out.println("Working Directory = " +
		              System.getProperty("user.dir"));
		      c.setAutoCommit(false);
		      System.out.println("Opened database successfully");

		      stmt = c.createStatement();
		      ResultSet rs = stmt.executeQuery( "SELECT * FROM CUST_AUTH WHERE USER_NAME = '" + name + "';" );
		      while ( rs.next() ) {
		    	 tmpCustAuth = new CustAuth();
		         String  pswd = rs.getString("PASSWORD");
		         int custId = rs.getInt("OWN_CUST_ID");
		         Date effT = Date.valueOf(rs.getString("EFF_TIME"));
		         Date expT = Date.valueOf(rs.getString("EXP_TIME"));
		        
		         tmpCustAuth.ownerCustId = custId;
		         tmpCustAuth.userName = name;
		         tmpCustAuth.password = pswd;
		         tmpCustAuth.effTime = effT;
		         tmpCustAuth.expTime = expT;
		         
		         System.out.println( "PASSWORD = " + pswd );
		         System.out.println();
		      }
		      if(!rs.isClosed()) rs.close();
		      if(!stmt.isClosed())  stmt.close();
		      if(!c.isClosed())  c.close();
		      
			return tmpCustAuth;
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      //System.exit(0);
		      return null;
		    }
		

	}
}
