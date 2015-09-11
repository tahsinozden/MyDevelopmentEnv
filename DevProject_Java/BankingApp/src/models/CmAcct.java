package models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class CmAcct {
	private int custId;
	private int balanceId;
	private int acctId;
	private Date effTime;
	private Date expTime;
	private int amount;
	private String currecyType;
	public int getBalanceId() {
		return balanceId;
	}
	public void setBalanceId(int balanceId) {
		this.balanceId = balanceId;
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
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getCurrecyType() {
		return currecyType;
	}
	public void setCurrecyType(String currecyType) {
		this.currecyType = currecyType;
	}
	
	public CmAcct(){
		
	}
	public CmAcct(	 
	 int custId,
	 int balanceId,
	 int acctId,
	 Date effTime,
	 Date expTime,
	 int amount,
	 String currecyType ){
		this.custId = custId;
		this.balanceId = balanceId;
		this.acctId = acctId;
		this.effTime = effTime;
		this.expTime = expTime;
		this.amount = amount;
		this.currecyType = currecyType;
	}
	
	public static ArrayList<CmAcct> getByBalanceID(int balanceid){
		String queryText = "SELECT *  FROM CM_ACCT WHERE BALANCE_ID = " + Integer.toString(balanceid) + " ;";
		ArrayList<HashMap<String, Object>> lstVals = AllModels.getQueryResult(queryText);
		ArrayList<CmAcct> cmacct = new ArrayList<CmAcct>();
		// return null if no matches
		if(lstVals.size() == 0){
			return null;
		}
		
		for( int i=0; i < lstVals.size(); i++){
			 int custId = (int)lstVals.get(i).get("CUST_ID");
			 int balanceId = (int)lstVals.get(i).get("BALANCE_ID");
			 int acctId = (int)lstVals.get(i).get("ACCT_ID");
			 Date effTime = Date.valueOf((String)lstVals.get(i).get("EFF_TIME"));
			 Date expTime = Date.valueOf((String)lstVals.get(i).get("EXP_TIME"));;
			 int amount = (int)lstVals.get(i).get("AMOUNT");
			 String currecyType = (String)lstVals.get(i).get("CURRENCY_TYPE");
			 
			 cmacct.add(new CmAcct(custId,balanceid, acctId, effTime, expTime, amount, currecyType));
		}
		return cmacct;
	}
	public static ArrayList<CmAcct> getByAcctID(int acctid){
		String queryText = "SELECT *  FROM CM_ACCT WHERE ACCT_ID = " + Integer.toString(acctid) + " ;";
		ArrayList<HashMap<String, Object>> lstVals = AllModels.getQueryResult(queryText);
		ArrayList<CmAcct> cmacct = new ArrayList<CmAcct>();
		// return null if no matches
		if(lstVals.size() == 0){
			return null;
		}
		
		for( int i=0; i < lstVals.size(); i++){
			 int custId = (int)lstVals.get(i).get("CUST_ID");
			 int balanceId = (int)lstVals.get(i).get("BALANCE_ID");
			 int acctId = (int)lstVals.get(i).get("ACCT_ID");
			 Date effTime = Date.valueOf((String)lstVals.get(i).get("EFF_TIME"));
			 Date expTime = Date.valueOf((String)lstVals.get(i).get("EXP_TIME"));;
			 int amount = (int)lstVals.get(i).get("AMOUNT");
			 String currecyType = (String)lstVals.get(i).get("CURRENCY_TYPE");
			 
			 CmAcct cm1 = new CmAcct(custId,balanceId, acctId, effTime, expTime, amount, currecyType);
			 cmacct.add(cm1);
		}
		return cmacct;
	}
	
	public static ArrayList<CmAcct> getByCustId(int custid){
		String queryText = "SELECT *  FROM CM_ACCT WHERE CUST_ID = " + Integer.toString(custid) + " ;";
		ArrayList<HashMap<String, Object>> lstVals = AllModels.getQueryResult(queryText);
		ArrayList<CmAcct> cmacct = new ArrayList<CmAcct>();
		// return null if no matches
		if(lstVals.size() == 0){
			return null;
		}
		
		for( int i=0; i < lstVals.size(); i++){
			 int custId = (int)lstVals.get(i).get("CUST_ID");
			 int balanceId = (int)lstVals.get(i).get("BALANCE_ID");
			 int acctId = (int)lstVals.get(i).get("ACCT_ID");
			 Date effTime = Date.valueOf((String)lstVals.get(i).get("EFF_TIME"));
			 Date expTime = Date.valueOf((String)lstVals.get(i).get("EXP_TIME"));;
			 int amount = (int)lstVals.get(i).get("AMOUNT");
			 String currecyType = (String)lstVals.get(i).get("CURRENCY_TYPE");
			 
			 CmAcct cm1 = new CmAcct(custId,balanceId, acctId, effTime, expTime, amount, currecyType);
			 cmacct.add(cm1);
		}
		return cmacct;
	}
	public static boolean addRecord(CmAcct cm){
		String insertText = String.format("INSERT INTO CM_ACCT VALUES(%d, %d, %d, '%s', '%s', %d, '%s');",
				cm.getCustId(),cm.getBalanceId(), cm.getAcctId(), cm.getEffTime().toString(), cm.getExpTime().toString(),
				cm.getAmount(), cm.getCurrecyType());
		
		return AllModels.insertData(insertText);
	}
	
	public static boolean updateByBalId(CmAcct cmUp){
		CmAcct cm = CmAcct.getByBalanceID(cmUp.getBalanceId()).get(0);
		if(cm == null){
			System.err.println("Cm not exist!");
			return false;
		}
		
		String delSql = String.format("DELETE FROM CM_ACCT WHERE BALANCE_ID = %d ;", cmUp.getBalanceId());
		if (AllModels.deleteData(delSql)){
			if(CmAcct.addRecord(cmUp)){
				System.out.println("Insert SUCCESS!");
				return true;
			}
			else{
				System.err.println("Cm not exist!");
				return false;
			}
		}
		else{
			return true;
		}
	}
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	
}
