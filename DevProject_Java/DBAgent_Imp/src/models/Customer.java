package models;
import java.sql.ResultSet;
import java.sql.SQLException;

import apps.general.DBAgent;
import interfacepkg.*;

public class Customer extends DBAgent implements GeneralModel{
	
	private int id;
	private String name;
	
	public Customer(int id, String name) {
		this.id = id;
		this.setName(name);
	}
	public Customer(){
		
	}
	@Override
	public void addResultSet(ResultSet rs) {
		try {
			this.id = (int)rs.getObject("ID");
			this.setName((String)rs.getObject("NAME"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public Object getObj() {
		return this;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
