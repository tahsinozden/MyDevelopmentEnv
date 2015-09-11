package apps.tools;
import java.sql.*;

public class DBConnect {
	/*
	 public static void main( String args[] )
	  {
	    Connection c = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:test.db");
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Opened database successfully");
	  }
	 */
	private String dbName;
	private String sqlStatement;  
	private boolean dbStatus; 
	private Connection connection;
	private ResultSet resultSet;
	
	public DBConnect(String dbName){
		this.dbName = dbName;
		this.connection = null;
		this.resultSet = null;
	}
	
	public ResultSet getResultSet() {
		return resultSet;
	}
	
	public String getDbName() {
		return dbName;
	}
	
	public String getSqlStatement() {
		return sqlStatement;
	}
	public void setSqlStatement(String sqlStatement) {
		this.sqlStatement = sqlStatement;
	}
	
	public boolean checkDBConnection(String dbName){
		 Connection c = null;
		 try{
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:" + dbName);
		 }
		 catch(Exception e){
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      return false;
		 }
		 return true;
	 }
	 
	public boolean connect(){
		 Connection c = null;
		 try{
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:" + this.dbName);
		      c.setAutoCommit(false);
		 }
		 catch(Exception e){
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.out.println(e.getMessage());
		      this.dbStatus = false;
		      return false;
		 }	
		 this.dbStatus = true;
		 
		 this.connection = c;
		 return true;
	}
	
	public boolean disconnect(){
		// if the connection exits
		if (this.connection != null && this.dbStatus == true){
			try {
				this.connection.close();
				this.dbStatus = false;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			return true;
		}
		else{
			// connection already closed
			return false;
		}
	}
	public boolean runStatement(String stat){
		Statement statement = null;
		if(this.connection != null && stat != ""){
			try {
				statement = this.connection.createStatement();
				//statement.executeQuery(stat);
				if(statement.executeQuery(stat) != null){
					//ResultSet rsSet = statement.getResultSet();
					// if the execution is successful set the result set
					this.resultSet = statement.getResultSet();
					this.resultSet.close();
					statement.close();
				}
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println(e.getMessage());
			}
			return true;
		}
		// the swl string null or the connection is closed
		else return false;
	}
	
	public ResultSet working(String stat){
		   Connection c = null;
		    Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:" + this.dbName);
		      c.setAutoCommit(false);
		      System.out.println("Opened database successfully");

		      stmt = c.createStatement();
		      String userName = "tahsin";
		      ResultSet rs = stmt.executeQuery( stat);
		      while ( rs.next() ) {
		         String  pswd = rs.getString("PASSWORD");

		         System.out.println( "PASSWORD = " + pswd );
		         System.out.println();
		         this.resultSet = rs;
		      }
		      rs.close();
		      stmt.close();
		      c.close();
		      return this.resultSet;
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      //System.exit(0);
		      return null;
		    }
		    //System.out.println("Operation done successfully");
	}




	
}
