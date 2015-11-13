package model;

import org.bson.Document;

public interface DBModel {
	public abstract Document getModelDocument();
	void fillObject(Document objDoc);
}
