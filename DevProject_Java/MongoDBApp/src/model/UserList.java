package model;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

public class UserList implements DBModel{
	private String userName;
	private String eMail;
	private String fullName;
	private int age;
	private String location;
	private String gender;
	private Map membersMap;
	
	public UserList(String userName,
					String eMail,
					String fullName,
					int age,
					String location,
					String gender){
		this.userName = userName;
		this.eMail = eMail;
		this.fullName = fullName;
		this.age = age;
		this.location = location;
		this.gender = gender;
	}
	public UserList(){}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	public Document getUserListDocument(){
		Document doc = new Document();
		doc.put("username", this.userName);
		doc.put("email", this.eMail);
		doc.put("fullname", this.fullName);
		doc.put("age",new Integer(this.age));
		doc.put("gender", this.gender);
		return doc;
	}
	
	public Map getUserListMap(){
		Map userMap = new HashMap();
		userMap.put("username", this.userName);
		userMap.put("email", this.eMail);
		userMap.put("fullname", this.fullName);
		userMap.put("age",new Integer(this.age));
		userMap.put("gender", this.gender);
		return userMap;
	}
	
	public Document getModelDocument() {
		return this.getUserListDocument();
	}
	public void fillObject(Document objDoc) {
		this.userName = objDoc.getString("username");
		this.eMail = objDoc.getString("email");
		this.fullName = objDoc.getString("fullname");
//		this.age = ((Double)objDoc.getDouble("age")).intValue();
		this.age = objDoc.getDouble("age").intValue();
		this.gender = objDoc.getString("gender");
		
	}
}
