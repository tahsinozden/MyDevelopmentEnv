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

public class DBAgent {
	public static ArrayList<GeneralModel> getQuery(String text,GeneralModel mdl){
		Connection conn = null;
		PreparedStatement preStm = null;
		Statement stm = null;
		
		ArrayList<GeneralModel> mdls = new ArrayList<GeneralModel>();
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:mydb.db");
			stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(text);
			
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
			return null;
		}
		
	}
}
