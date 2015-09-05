package apps;

import interfacepkg.GeneralModel;

import java.util.ArrayList;

import models.Customer;
import apps.general.DBAgent;

public class DBApp {

	public static void main(String[] args) {
		Customer cust = new Customer();
		ArrayList<GeneralModel> mdls = DBAgent.getQuery("SELECT * FROM CUSTOMER WHERE ID = 12;", cust);
		System.out.print(((Customer)mdls.get(0).getObj()).getName() );
	}

}
