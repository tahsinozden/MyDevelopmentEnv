/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apps.webservices;

import apps.general.DBAgent;
import interfacepkg.GeneralModel;
import java.sql.Date;
import java.util.ArrayList;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.ws.Holder;
import models.Customer;
import org.json.simple.JSONObject;

/**
 *
 * @author tahsin
 */
@WebService(serviceName = "QueryCustomer")
public class QueryCustomer {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "CheckCustomerExist")
    public java.lang.Boolean CheckCustomerExist(@WebParam(name = "custId") int custId, @WebParam(name = "effTime") String effTime, @WebParam(name = "expTime") String expTime) {
        Customer cust = new Customer();
        ArrayList<GeneralModel> lst = DBAgent.getQuery(String.format("SELECT * FROM CUSTOMER WHERE ID = %d ;", custId), cust);
        if (lst != null && lst.size() != 0){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "GetCustomer")
    public String GetCustomer(@WebParam(name = "custId") int custId) {
        Customer cust = new Customer();
        ArrayList<GeneralModel> lst = DBAgent.getQuery(String.format("SELECT * FROM CUSTOMER WHERE ID = %d ;", custId), cust);
        if( lst == null){
//            Customer newCust = new Customer(custId, String.valueOf(custId), custId, Date.valueOf("2000-01-01"), Date.valueOf("2010-01-01"));
//            DBAgent.insertData(newCust);
            return "Customer Not Exist!";
        }
        else{
            JSONObject custObj = new JSONObject();
            Customer cst = (Customer) lst.get(0).getObj();
            custObj.put("CUST. ID", cst.getId());
            custObj.put("NAME", cst.getName());
            custObj.put("ACCT. ID", cst.getAcct_id());
            custObj.put("EFF_TIME", cst.getEff_time().toString());
            custObj.put("EXP_TIME", cst.getExp_time().toString());
            
            return custObj.toJSONString();
        }

    }
    
}
