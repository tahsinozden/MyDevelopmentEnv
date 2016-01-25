package controller;

import java.util.ArrayList;

import model.DBModel;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.List;

public class DBConnector {
	private String hostName;
	private int port;
	private String dbName;
	private MongoClient client;
	private MongoCollection currentCollection;

	/**
	 * it is an enumation of opeation modes on DB such as perform an operation on single matching record or all records
	 * @author tahsin
	 *
	 */
	public enum OperationMode{
		ONE_RECORD,
		MANY_RECORDS
	}
	/**
	 * It enables to create a DB connection, and help to do some opearions on DB.
	 * @param hostName
	 * @param port
	 * @param dbName
	 */
	public DBConnector(String hostName, int port, String dbName) {
		this.hostName = hostName;
		this.port = port;
		this.dbName = dbName;
		
//		this.client = new MongoClient(hostName, port);
	}
	// TODO: IMPLEMENT
//	private void openConnection(){
//		// setup the database cleint
//		client = new MongoClient(this.hostName,this.port);
//		MongoDatabase mongodb = client.getDatabase(this.dbName);
//		// specify the collection i.e. table name
//		this.currentCollection = mongodb.getCollection(collectionName);
//	}
	/**
	 * It queries data from the database with passed BasicDBObject
	 * @param collectionName
	 * @param dbObj
	 * @return list of database document
	 * 
	 */
	public ArrayList<Document> query(String collectionName, BasicDBObject dbObj){
		ArrayList<Document> lstDocs = new ArrayList<Document>();
		// setup the database cleint
		client = new MongoClient(this.hostName,this.port);
		MongoDatabase mongodb = client.getDatabase(this.dbName);
		// specify the collection i.e. table name
		MongoCollection collection = mongodb.getCollection(collectionName);
		// get the result with the query condition
		FindIterable iter = collection.find(dbObj);
		MongoCursor cursor = iter.iterator();
		while(cursor.hasNext()){
			// pass the query result as document to the list
			lstDocs.add((Document)cursor.next());
		}
		// close the connection
		client.close();
		return lstDocs;
	}
	/**
	 * It returns matching records with provided condition. 
	 * It returns a DB model arraylist
	 * @param collectionName
	 * @param dbObj
	 * @param modelObj
	 * @return
	 */
	public ArrayList<DBModel> query(String collectionName, BasicDBObject dbObj, DBModel modelObj){
		
		ArrayList<DBModel> lstModelObj = new ArrayList<DBModel>();
		try {
			// setup the database cleint
			client = new MongoClient(this.hostName,this.port);
			MongoDatabase mongodb = client.getDatabase(this.dbName);
			// specify the collection i.e. table name
			MongoCollection collection = mongodb.getCollection(collectionName);
			// get the result with the query condition
			FindIterable iter = collection.find(dbObj);
			MongoCursor cursor = iter.iterator();
			while(cursor.hasNext()){
				// pass the query result as model object to the list
				modelObj.fillObject((Document)cursor.next());
				lstModelObj.add(modelObj);
			}
			// close the connection
			client.close();
		} catch (Exception e) {
			System.out.println("Exception catched. Info : " + e.getMessage());
//			e.printStackTrace();
		}
		return lstModelObj;
	}
	
	/**
	 * 
	 * @param collectionName
	 * @param dbDoc
	 * it gets the database document and collection name to which data is added. then it insert the data to the corresponding collection
	 */
	public void insert(String collectionName, Document dbDoc){
		// setup the database client
		client = new MongoClient(this.hostName,this.port);
		MongoDatabase mongodb = client.getDatabase(this.dbName);
		// specify the collection i.e. table name
		MongoCollection collection = mongodb.getCollection(collectionName);
		collection.insertOne(dbDoc); 
		// close the connection
		client.close();

	}
	/**
	 * It accepts collection name and performs the operation with query document and update document.
	 * @param collectionName
	 * @param dbqueryCond
	 * @param dbUpdateObj
	 */
	public void update(String collectionName, Document dbqueryCond, Document dbUpdateObj){
		// setup the database client
		client = new MongoClient(this.hostName,this.port);
		MongoDatabase mongodb = client.getDatabase(this.dbName);
		// specify the collection i.e. table name
		MongoCollection collection = mongodb.getCollection(collectionName);
		// "$set" keyword is used for update
		dbUpdateObj = new Document("$set", dbUpdateObj);
//		System.out.println(dbUpdateObj.toString());
		collection.updateMany(dbqueryCond, dbUpdateObj);
		client.close();
	}
	/**
	 * it removes the matching records with an operation mode.
	 * @param collectionName
	 * @param dbDoc
	 * @param mode
	 */
	public void delete(String collectionName, Document dbDoc, OperationMode mode){
		// setup the database client
		client = new MongoClient(this.hostName,this.port);
		MongoDatabase mongodb = client.getDatabase(this.dbName);
		// specify the collection i.e. table name
		MongoCollection collection = mongodb.getCollection(collectionName);
		if ( mode == OperationMode.ONE_RECORD) collection.deleteOne(dbDoc);
		else collection.deleteMany(dbDoc);
		client.close();
	}
        
}