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
import models.Customer;

/**
 *
 * @author tahsin
 */
@WebService(serviceName = "CreateCustomer")
public class CreateCustomer {

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
    @WebMethod(operationName = "CreateCustomerReq")
    public String CreateCustomerReq(@WebParam(name = "custID") int custID, @WebParam(name = "name") String name, @WebParam(name = "acctID") int acctID, @WebParam(name = "effTime") String effTime, @WebParam(name = "expTime") String expTime) {
        
        Date effT;
        Date expT;
        if (custID <= 0 ) {
            return String.format("Invalid Customer ID! : %d",custID);
        }
        if (name == ""){
             return String.format("Invalid Customer ID! : %s",name);
        }
        if (acctID <= 0 ) {
            return String.format("Invalid Customer ID! : %d",acctID);
        }
        try {
            effT = Date.valueOf(effTime);
            expT = Date.valueOf(expTime);
                
        } catch (Exception e) {
            return "Invalid Dates!";
        }
        
        // check the customer exists or not
        ArrayList<GeneralModel> lst = DBAgent.getQuery(String.format("SELECT * FROM CUSTOMER WHERE ID = %d ;", custID), new Customer());
        if (lst != null || lst.size() != 0){
            return String.format("Customer %d exists!", custID);
        }
        
        Customer newCust = new Customer(custID, name, acctID, effT, expT);
        
        if (DBAgent.insertData(newCust)){
            return "Result Success!";
        }
        else{
            return "Internal Error!";
        }

    }

}
