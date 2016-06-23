/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   IDBEntity.h
 * Author: tahsin
 *
 * Created on 09 Haziran 2016 Perşembe, 18:09
 */

#ifndef IDBENTITY_H
#define IDBENTITY_H

#include <map>
#include <vector>
#include <string>

typedef std::map<std::string, std::string> StringMap;
typedef std::vector<std::pair<std::string, std::string> > PairList;

/// <summary>
/// This is an interface for database entity objects. For database handler, entities implementing this interface are required.
/// </summary>
class IDBEntity {
public:

	/// <summary>
	/// Gets the database object fields.
	/// </summary>
	/// <returns>It returns entity object fields with their types. i.e. {"NAME": "TEXT", "SURNAME": "TEXT", "AGE": "INETEGER", "AVERAGE": "DOUBLE"}</returns>
	virtual PairList getDBObjectFields() = 0;

	/// <summary>
	/// Gets the name of the database object.
	/// </summary>
	/// <returns>Returns the entity name which will be stored as a file.</returns>
	virtual std::string getDBObjectName() = 0;

	/// <summary>
	/// Gets the mapped object.
	/// </summary>
	/// <returns>Returns a string map for entities' each field. i.e. {"NAME": "JOHN", SURNAME:"DOE", "AGE":"34", "AVERAGE":"12.45"}</returns>
	virtual StringMap getMappedObject() = 0;

	/// <summary>
	/// Gets the entity object from mapping.
	/// </summary>
	/// <param name="">String map covering all fields of the entity</param>
	/// <returns>Returns the object itself from the mapping. Reverse action for getMappedObject()</returns>
	virtual IDBEntity* getEntityObjFromMapping(StringMap) = 0;

	/// <summary>
	/// Clones this instance.
	/// </summary>
	/// <returns>Returns the casted instance of the object.</returns>
	virtual IDBEntity* clone() const = 0;

	/// <summary>
	/// Gets the class name.
	/// </summary>
	/// <returns>Return class name.</returns>
	std::string getName();
};

#endif /* IDBENTITY_H */

