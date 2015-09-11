package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.DefaultEditorKit.CutAction;
import models.*;

public class Acct2Cust extends AllModels{
	private static final int ImpAcct2Cust = 0;
	private static final int ArrayList = 0;
	private int custId;
	private int acctId;
	private Date effTime;
	private Date expTime;
	
	public Acct2Cust(){}
	
	public Acct2Cust(int custId,
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
	
	//public <T> T addResultSet(ResultSet rs){
	public AllModels addResultSet(ResultSet rs){
		Acct2Cust acct2cust = new Acct2Cust();
		try {
			while(rs.next()){
				int custId = rs.getInt("CUST_ID");
				int acctId = rs.getInt("ACCT_ID");
				Date effTime = rs.getDate("EFF_TIME");
				Date expTime = rs.getDate("EXP_TIME");
				acct2cust =  new Acct2Cust(custId, acctId, effTime, expTime);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Error!");
			return null;
		}
		return acct2cust;
	}
	
	public static ArrayList<Acct2Cust>  getAccountByCustomer(int custId){
		Acct2Cust mdl =  new Acct2Cust();
		ArrayList<Acct2Cust> lstCust = new ArrayList<Acct2Cust>();
		
		String queryText = "SELECT * FROM ACCT2CUST WHERE CUST_ID = " + Integer.toString(custId) + " ;";
		ArrayList<HashMap<String, Object>> mapList =  AllModels.getQueryResult(queryText);
		
		for (int i = 0; i < mapList.size(); i++) {
				int CustId = (int)mapList.get(i).get("CUST_ID");
				int acctId = (int)mapList.get(i).get("ACCT_ID");
				Date effTime = Date.valueOf((String)mapList.get(i).get("EFF_TIME"));
				Date expTime = Date.valueOf((String)mapList.get(i).get("EXP_TIME"));
				lstCust.add(new Acct2Cust(CustId, acctId, effTime, expTime));
				//Date.valueOf((String)mapList.get(i).get("EFF_TIME"));
		}
		return lstCust;
	}
	
	public static ArrayList<Acct2Cust> getRecordsByAcct(int acctId){
		String queryText = "SELECT *  FROM ACCT2CUST WHERE ACCT_ID = " + Integer.toString(acctId) + " ;";
		ArrayList<HashMap<String, Object>> lstSet = AllModels.getQueryResult(queryText);
		ArrayList<Acct2Cust> lstCust = new ArrayList<Acct2Cust>();
		if ( lstSet.size() == 0 ){
			return null;
		}
		for (int i = 0; i < lstSet.size(); i++) {
			int CustId = (int)lstSet.get(i).get("CUST_ID");
			int AcctId = (int)lstSet.get(i).get("ACCT_ID");
			Date effTime = Date.valueOf((String)lstSet.get(i).get("EFF_TIME"));
			Date expTime = Date.valueOf((String)lstSet.get(i).get("EXP_TIME"));
			lstCust.add(new Acct2Cust(CustId, AcctId, effTime, expTime));
		}
		return lstCust;
	}
	
	public static boolean addAcct2Cust(Customer cust){
		// check the custimer exists
		if( Customer.getCustomerById(cust.getId() ) == null){
			System.out.println("Customer not exist!");
			return false;
		}
		int acctId = (int)( 1000000 * Math.random());
		if ( getRecordsByAcct(cust.getDefAcctId()) != null){
			return false;
		}
		
		String insertText = String.format("INSERT INTO ACCT2CUST VALUES (%d, %d, '%s', '%s')",
											cust.getId(), acctId,cust.getEffTime().toString(),cust.getExpTime().toString());
		return AllModels.insertData(insertText);
	}
	
	
}
