/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apps;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import controller.DBConnector;
import java.util.ArrayList;
import model.Users;
import org.bson.Document;
import org.json.JSONException;

import org.json.JSONObject;
import java.net.UnknownHostException;
/**
 *
 * @author tahsin
 */

public class ConvertHelper {
    private Client client = Client.create();
    // currency rates inside
    private final String URL = "http://api.fixer.io/latest";
        //set the appropriate URL
    //String getUrl = "http://localhost:8080/JAXRS-JSON/rest/student/data/get";

    public JSONObject getRequest() throws JSONException, UnknownHostException {

        WebResource webResource = client.resource(URL);
        // accept json type response
        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
        //a successful response returns 200
        if (response.getStatus() != 200) {
            throw new RuntimeException("HTTP Error: " + response.getStatus());
        }

        String result = response.getEntity(String.class);

        System.out.println("Response from the Server: ");
        System.out.println(result);
        JSONObject jsonObj = new JSONObject(result);
        return jsonObj;
    }
    
    public void DBConnect(){
        DBConnector connector = new DBConnector("localhost", 27017, "applicationdb");
        Users user = new Users("tahsin","2424", true);
        ArrayList<Document> res = connector.query(Users.COLLECTION_NAME_USERS, new BasicDBObject(Users.FIELD_NAME_USERNAME, user.getUserName()));
	if (res.size() >= 0)
            user.setUserName(user.getUserName() + 1);
        connector.insert(Users.COLLECTION_NAME_USERS, user.getModelDocument());
	
//	MongoClient client = new MongoClient("localhost", 27017);
//	MongoDatabase db = client.getDatabase("mongo_data");
//
//		MongoCollection collection = db.getCollection("userlist");
////		collection.deleteOne(new Document("username", "Tugce"));
//		collection.deleteMany(new Document("username", "NEW_USER"));
//	client.close();
    }

}
