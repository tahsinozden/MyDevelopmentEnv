package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.internal.compiler.ast.ContinueStatement;

public class AllModels {
	//public <T> T addResultSet(ResultSet rs){
	public AllModels addResultSet(ResultSet rs){
		System.out.println("Not Implemeneted!");
		return null;
	}
	
	//public <T> ArrayList<T> getQueryResult(String queryText){
	public static ArrayList<HashMap<String, Object>> getQueryResult(String queryText){
		Connection conn = null;
		PreparedStatement preStm = null;
		Statement stm = null;
		OLDAcct2Cust acct2cust = null;
		ArrayList<AllModels> mdls = new ArrayList<AllModels>();
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
			ArrayList<HashMap<String, Object>> mapLst =  new ArrayList<HashMap<String, Object>>();
			
			while(rs.next()){
				
				ResultSetMetaData metadata = rs.getMetaData();
				ArrayList<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
				HashMap<String, Object> tmp = new HashMap<String, Object>();
				for (int i = 1; i <= metadata.getColumnCount(); i++) {
					String tmpColumn = metadata.getColumnName(i); 
					//if ( metadata.getColumnName(i).equals("")) 
					//		continue;
					//if (  rs.getObject(i).equals(null) )
					//	continue;
					tmp.put(metadata.getColumnName(i), rs.getObject(i));
					//map.add(tmp);
					//mapLst.add(tmp);
				}
				//mapLst.add((map);
				mapLst.add(tmp);
			}
			rs.close();
			stm.close();
			conn.close();
			return mapLst;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean insertData(String sqlText){
		Connection conn = null;
		Statement statement = null;
		
		if ( sqlText.equals("")){
			System.out.println("sql text is NULL!");
			return false;
		}
		try {
			// get the JDBC class for sqlite
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
			return false;
		}
		try {
			// open the database existing the current directory
			conn = DriverManager.getConnection("jdbc:sqlite:admin.db");
			statement = conn.createStatement();
			statement.executeUpdate(sqlText);
			statement.close();
			conn.close();
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	public static boolean deleteData(String sqlText){
		Connection conn = null;
		Statement statement = null;
		
		if ( sqlText.equals("")){
			System.out.println("sql text is NULL!");
			return false;
		}
		try {
			// get the JDBC class for sqlite
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
			return false;
		}
		try {
			// open the database existing the current directory
			conn = DriverManager.getConnection("jdbc:sqlite:admin.db");
			statement = conn.createStatement();
			statement.executeUpdate(sqlText);
			statement.close();
			conn.close();
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
		return true;
	}
}
