package apps.general;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import interfacepkg.*;
/**
 * It is database connection tool for model classes. It uses GeneralModel interface to be implemented by other db models
 * @author tahsin
 */
public class DBAgent {
        /**
         *         Returns the query result in GeneralModel type
         *         if the query does not match any records, it returns null.
         * @param text
         * @param mdl
         * @return 
         */
	public static ArrayList<GeneralModel> getQuery(String text,GeneralModel mdl){
		Connection conn = null;
		PreparedStatement preStm = null;
		Statement stm = null;
		
		ArrayList<GeneralModel> mdls = null;
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
                    // mydb.db must be in the server. apache/bin
			conn = DriverManager.getConnection("jdbc:sqlite:mydb.db");
//                        String dir = System.getProperty("user.dir");
//                        System.out.println(dir);
			stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(text);
                        
                        // check there are rows after query, then initialize the array
                        if (rs.isBeforeFirst()){
                             mdls = new ArrayList<GeneralModel>();
                        }
                        
			while(rs.next()){
				mdl.addResultSet(rs);
				mdls.add(mdl);
			}
			rs.close();
			stm.close();
			conn.close();
			return mdls;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
                        String msg = e.getMessage();
                        System.out.println(msg);
			return null;
		}
		
	}
        
       public static boolean insertData(GeneralModel gm){
            boolean res = true;
            Connection conn = null;
            PreparedStatement preStm = null;
            Statement stm = null;
		
            try {
		Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
		e.printStackTrace();
            }
            try {
		conn = DriverManager.getConnection("jdbc:sqlite:mydb.db");
//                String dir = System.getProperty("user.dir");
//                  System.out.println(dir);
                    // get the insert text of the model
                    String sqlText = gm.getInsertText();
                    stm = conn.createStatement();
                    stm.executeUpdate(sqlText);
                    
                    if (!stm.isClosed())  stm.close();
                    if (!conn.isClosed()) conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
                        String msg = e.getMessage();
                        System.out.println(msg);
                        res = false;
		}
            return res;
        }
}
