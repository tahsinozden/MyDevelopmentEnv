/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.bson.Document;

/**
 *
 * @author tahsin
 */
public class Users implements DBModel{

    public static final String COLLECTION_NAME_USERS = "users";
    public static final String FIELD_NAME_USERNAME = "username";
    public static final String FIELD_NAME_ISACTIVE = "isactive";
    static final public String FIELD_NAME_PASSWORD = "password";
    
    private String userName;
    private String password;
    private boolean isActive;

    public Users(String name, String pswd, boolean isActive) {
        this.setUserName(name);
        this.setPassword(pswd);
        this.setIsActive(isActive);
    }
    
    public Users(){}
    
    @Override
    public Document getModelDocument() {
        Document doc = new Document();
        doc.append(FIELD_NAME_USERNAME, this.getUserName());
        doc.append(FIELD_NAME_PASSWORD, this.getPassword());
        doc.append(FIELD_NAME_ISACTIVE, this.isIsActive());
        return doc;
    }

    @Override
    public void fillObject(Document objDoc) {
        this.setUserName(objDoc.getString(this.FIELD_NAME_USERNAME));
        this.setUserName(objDoc.getString(this.FIELD_NAME_PASSWORD));
        this.setIsActive(objDoc.getBoolean(this.isIsActive()));
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the isActive
     */
    public boolean isIsActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    
}
