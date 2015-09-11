package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBModel {
	/*
	private static ArrayList< ArrayList<Object>> getQueryResult(String queryText){
		Connection conn = null;
		PreparedStatement preStm = null;
		Statement stm = null;
		Acct2Cust acct2cust = null;
		
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
				this.custId = rs.getInt("CUST_ID");
				this.acctId = rs.getInt("ACCT_ID");
				this.effTime = rs.getDate("EFF_TIME");
				this.expTime = rs.getDate("EXP_TIME");
				int custId = rs.getInt("CUST_ID");
				int acctId = rs.getInt("ACCT_ID");
				Date effTime = rs.getDate("EFF_TIME");
				Date expTime = rs.getDate("EXP_TIME");
				acct2cust =  new Acct2Cust(custId, acctId, effTime, expTime);
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
		
	}*/
}
