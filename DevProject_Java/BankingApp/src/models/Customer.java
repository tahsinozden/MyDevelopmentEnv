package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.text.DefaultEditorKit.CutAction;

public class Customer {
	private int id;
	private String name;
	private int defAcctId;
	private Date effTime;
	private Date expTime;
	
	public Customer() {
		// TODO Auto-generated constructor stub
	}
	
	public Customer(int id,
					String name,
					int acctId,
					Date efT,
					Date exT) {
		this.name = name;
		this.id = id;
		this.defAcctId = acctId;
		this.effTime = efT;
		this.expTime = exT;
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDefAcctId() {
		return defAcctId;
	}
	public void setDefAcctId(int defAcctId) {
		this.defAcctId = defAcctId;
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
	
	public static Customer getCustomerById(int id){
		Customer cust = null;
		
		Connection c = null;
		Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:admin.db");
		      c.setAutoCommit(false);
		      System.out.println("Opened database successfully");

		      stmt = c.createStatement();
		      ResultSet rs = stmt.executeQuery( "SELECT * FROM CUSTOMER WHERE ID = '" + Integer.toString(id) + "';" );
		      while ( rs.next() ) {
		    	 cust = new Customer();
		         String  name = rs.getString("NAME");
		         int acctId = rs.getInt("DEF_ACCT_ID");
		         Date effT = rs.getDate("EFF_TIME");
		         Date expT = rs.getDate("EXP_TIME");
		         
		         cust.id = id;
		         cust.name = name;
		         cust.defAcctId = acctId;
		         cust.effTime = effT;
		         cust.expTime = expT;
		      }
		      rs.close();
		      stmt.close();
		      c.close();
			return cust;
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      //System.exit(0);
		      return null;
		    }
		    
	}
	
	public static boolean createCustomer(Customer cust){
		Connection c = null;
		if(cust == null){
			return false;			
		}
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:admin.db");
		      PreparedStatement preStatement = c.prepareStatement("INSERT INTO CUSTOMER VALUES ( ?,?,?,?,?);");
		      preStatement.setInt(1, cust.getId());
		      preStatement.setString(2, cust.getName());
		      preStatement.setInt(3, cust.getDefAcctId());
		      preStatement.setDate(4, cust.getEffTime());
		      preStatement.setDate(5, cust.getExpTime());

		      c.setAutoCommit(false);
		      
		      System.out.println("Opened database successfully");  
		      // executeUpdate must be used for insert data
		      if(preStatement.executeUpdate() >=0 ){
			      c.commit();
			      preStatement.close();
			      c.close();
			      return true;
		      }
		      else{
		    	  System.out.println("Insert error!");
		    	  return false;
		      }
		    } catch ( java.sql.SQLException | ClassNotFoundException e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      //System.out.println("ERROR Message : " + e.getMessage());
		      //System.exit(0);
		      return false;
		    }
	}
	
	public static ArrayList<Customer> getAllCustomers(){
		ArrayList<Customer> lstCust = new ArrayList<Customer>();
		Customer cust = null;
		
		Connection c = null;
		Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:admin.db");
		      c.setAutoCommit(false);
		      System.out.println("Opened database successfully");

		      stmt = c.createStatement();
		      ResultSet rs = stmt.executeQuery( "SELECT * FROM CUSTOMER;");
		      while ( rs.next() ) {
		    	 cust = new Customer();
		    	 int id = rs.getInt("ID");
		         String  name = rs.getString("NAME");
		         int acctId = rs.getInt("DEF_ACCT_ID");
		         Date effT = rs.getDate("EFF_TIME");
		         Date expT = rs.getDate("EXP_TIME");
		         
		         cust.id = id;
		         cust.name = name;
		         cust.defAcctId = acctId;
		         cust.effTime = effT;
		         cust.expTime = expT;
		         
		         lstCust.add(cust);
		      }
		      rs.close();
		      stmt.close();
		      c.close();
		      return lstCust;
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      //System.exit(0);
		      return null;
		    }
		    
		    
	}

}
