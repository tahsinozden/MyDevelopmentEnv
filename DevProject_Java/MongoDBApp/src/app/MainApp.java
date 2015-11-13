package app;

import java.lang.annotation.Documented;
import java.util.ArrayList;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.util.JSON;
import com.mongodb.MongoClient;

import controller.DBConnector;
import controller.DBConnector.OperationMode;
import controller.DBConnector.OperationMode;

import org.bson.*;
import org.bson.json.JsonReader;

import model.UserList;

public class MainApp {

	public static void main(String[] args) {
		DBConnector con = new DBConnector("localhost", 27017, "mongo_data");
		UserList user = new UserList("Tugce", "t@gmail.com", "TO", 24, "tr", "female");
		con.insert("userlist", user.getUserListDocument());
		
		MongoClient client = new MongoClient("localhost", 27017);
		MongoDatabase db = client.getDatabase("mongo_data");

		MongoCollection collection = db.getCollection("userlist");
//		collection.deleteOne(new Document("username", "Tugce"));
		collection.deleteMany(new Document("username", "NEW_USER"));
		client.close();
		System.out.println("operation ended!");
		
		con.delete("userlist", new Document("username", "tahsin"), OperationMode.ONE_RECORD);
	}
	
	public static void main1() {
		// try {
		// MongoClient client = new MongoClient("localhost", 27017);
		// } catch (Exception e) {
		// System.out.println(e.getMessage());
		// }

		MongoClient client = new MongoClient("localhost", 27017);
		MongoDatabase db = client.getDatabase("mongo_data");

		MongoCollection collection = db.getCollection("userlist");
		// BasicDBObject dbObj = new BasicDBObject("username","test1");

		BasicDBObject dbObj = new BasicDBObject("username", new BasicDBObject(
				"$ne", null));
		// dbObj.put("age", 27);
		dbObj.put("age", new Integer(27));
		System.out.println(dbObj.toString());
		FindIterable iter = collection.find(dbObj);
		MongoCursor cur = iter.iterator();
		while (cur.hasNext()) {
			// String item = cur.next().toString();
			Document doc = (Document) cur.next();
			String item = doc.toString();

			String username = doc.getString("username");
			System.out.println("Query Result : " + item);
			System.out.println("username =  : " + username);
		}

		UserList newUser = new UserList("tahsin", "tahsin@gmail.com",
				"tahsn ozden", 25, "Turkey", "Male");
		BasicDBObject insertObj = new BasicDBObject(newUser.getUserListMap());
		db.getCollection("userlist").insertOne(newUser.getUserListDocument());
		client.close();

		System.out.println("New Imp.");
		DBConnector connector = new DBConnector("localhost", 27017,
				"mongo_data");
		ArrayList<Document> resDocs = connector.query("userlist", dbObj);

		if (resDocs.size() > 0)
			System.out.println(resDocs.get(0).toString());

		System.out.println("New INSERT.");
		DBConnector connector1 = new DBConnector("localhost", 27017,
				"mongo_data");
		UserList newUser1 = new UserList("tahsin", "tahsin@gmail.com",
				"new ozden", 25, "Turkey", "Male");
		BasicDBObject insertObj1 = new BasicDBObject(newUser.getUserListMap());
		connector1.insert("userlist", newUser1.getUserListDocument());

		UserList user = new UserList();
		if (connector1.query("userlist1", dbObj, user).size() > 0) {
			System.out.println(((UserList) connector1.query("userlist1", dbObj,
					user).get(0)).getFullName());
		} else {
			System.out.println("Array NULL!");
		}

		Document querybasicObj = new Document("fullname", "Bob Smith");
		Document updatebasicObj = new Document("fullname", "hell yeah");

		connector1.update("userlist", querybasicObj, updatebasicObj);
	}

}
