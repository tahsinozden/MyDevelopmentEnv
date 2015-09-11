package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.text.DefaultEditorKit.CutAction;

public class OLDAcct2Cust {
	private int custId;
	private int acctId;
	private Date effTime;
	private Date expTime;
	
	public OLDAcct2Cust(){}
	
	public OLDAcct2Cust(int custId,
					 int acctId,
					 Date efftime,
					 Date exptime){
		this.custId = custId;
		this.acctId = acctId;
		this.effTime = efftime;
		this.expTime = exptime;
		
	}
	
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public int getAcctId() {
		return acctId;
	}
	public void setAcctId(int acctId) {
		this.acctId = acctId;
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
	
	private static OLDAcct2Cust getQueryResult(String queryText){
		Connection conn = null;
		PreparedStatement preStm = null;
		Statement stm = null;
		OLDAcct2Cust acct2cust = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:admin.db");
			stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(queryText);
			while(rs.next()){
/*				this.custId = rs.getInt("CUST_ID");
				this.acctId = rs.getInt("ACCT_ID");
				this.effTime = rs.getDate("EFF_TIME");
				this.expTime = rs.getDate("EXP_TIME");*/
				int custId = rs.getInt("CUST_ID");
				int acctId = rs.getInt("ACCT_ID");
				Date effTime = rs.getDate("EFF_TIME");
				Date expTime = rs.getDate("EXP_TIME");
				acct2cust =  new OLDAcct2Cust(custId, acctId, effTime, expTime);
			}
			rs.close();
			stm.close();
			conn.close();
			return acct2cust;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	public static OLDAcct2Cust getAcctByCustomer(int custId){
		String query = "SELECT * FROM ACCT2CUST WHERE CUST_ID = " + Integer.toString(custId) + ";";
		System.out.println(query);
		OLDAcct2Cust acct2cust =  getQueryResult(query);
		return acct2cust;
		
	}
	
}
