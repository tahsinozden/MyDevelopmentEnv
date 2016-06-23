/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   DBObjectHandler.h
 * Author: tahsin
 *
 * Created on 09 Haziran 2016 Perşembe, 18:06
 */

#ifndef CDATABASEOBJECTHANDLER_H
#define CDATABASEOBJECTHANDLER_H
#include "IDBEntity.h"
#include "Common.h"
#include <vector>
#include <map>
#include <cstdlib>
#include <iostream>
#include <fstream>
#include <mutex>

enum WRITER_MODE {
	CREATE,
	APPEND
};

/// <summary>
/// It is a database handler, it performs adding, deleting, updating and query operations. 
/// </summary>
class CDatabaseObjectHandler {
  
private:
    typedef std::vector<std::pair<std::string, std::string> > PairList;
    std::string m_dbObjectName;
    std::vector<std::string> m_dbObjectFields;
    PairList m_dbObjFieldPairs;
    std::ofstream m_dbWriter;
    std::ifstream m_dbReader;
	std::vector<int>* m_matchedIndexes;
	std::string currentEntityObjectFile;
	std::mutex m_fileLockMutex;

	/// <summary>
	/// Check the existence of the specified filename.
	/// </summary>
	/// <param name="filename">The filename.</param>
	/// <returns></returns>
	bool fexists(std::string filename);

	/// <summary>
	/// Checks the object header exist. This header is database fields of the corresponding object.
	/// </summary>
	/// <param name="fileName">Name of the file.</param>
	/// <param name="expectedHeader">The expected header.</param>
	/// <returns></returns>
	bool checkObjectHeaderExist(std::string fileName, std::string expectedHeader);

	/// <summary>
	/// Gets the elements from string. Lines are seperated with | sign, it parses and returns elements as a string vector.
	/// </summary>
	/// <param name="str">The string.</param>
	/// <returns></returns>
	std::vector<std::string> getElementsFromString(std::string str);

	/// <summary>
	/// Gets the match indexes. It checks the database file with the passed object and returns the file line numbers.
	/// </summary>
	/// <param name="queryObj">The query object.</param>
	/// <returns></returns>
	std::vector<int>* getMatchIndexes(IDBEntity* queryObj);

	/// <summary>
	/// Gets the entity object writer. The writer used for writing to a database file
	/// </summary>
	/// <param name="obj">The entity object.</param>
	/// <param name="mode">The mode. Valid modes are APPEND and CREATE</param>
	/// <returns></returns>
	std::ofstream* getEntityObjectWriter(IDBEntity* obj, WRITER_MODE mode);

	/// <summary>
	/// Gets the entity object reader. Used for reading entities from database file.
	/// </summary>
	/// <param name="obj">The reference entity object.</param>
	/// <returns></returns>
	std::ifstream* getEntityObjectReader(IDBEntity* obj);

	/// <summary>
	/// Closes the object writer.
	/// </summary>
	void closeObjectWriter();

	/// <summary>
	/// Closes the object reader.
	/// </summary>
	void closeObjectReader();

	/// <summary>
	/// Writes the object to database.
	/// </summary>
	/// <param name="refEntityObject">The reference entity object.</param>
	/// <param name="openMode">The open mode. APPEND and CREATE</param>
	/// <param name="toBeWritten">String To be written. Depending on teh mode APPEND and CREATE, all lines need to be passed for CREATE, just addition line needs to be passed for APPEND.</param>
	void writeObjectToDB(IDBEntity* refEntityObject, WRITER_MODE openMode, std::string toBeWritten);
	
	void readObjectFromDB();

	/// <summary>
	/// Gets all entities from database.
	/// </summary>
	/// <param name="refObj">The reference object.</param>
	/// <returns>Returns all relevant objects from database file.</returns>
	DBEntityList getAllEntitiesFromDB(IDBEntity* refObj);

	/// <summary>
	/// Creates the record from entity object.
	/// </summary>
	/// <param name="obj">The object.</param>
	/// <returns>It returns a record string in the following format. Field1|Field2|Field3</returns>
	std::string createRecordFromEntityObject(IDBEntity* obj);

	/// <summary>
	/// Creates the header from entity object.
	/// </summary>
	/// <param name="obj">The object.</param>
	/// <returns>It returns a record string in the following format. Header1|Header2|Header3</returns>
	std::string createHeaderFromEntityObject(IDBEntity* obj);

public:
    enum DB_OBJECT_FIELD_TYPE{
        INT,
        DOUBLE,
        TEXT        
    };
    
	CDatabaseObjectHandler();
	/// <summary>
	/// Saves the entity object.
	/// </summary>
	/// <param name="">It gets IDBEntity object and saves it to the database file.</param>
	void saveEntityObject(IDBEntity*);

	/// <summary>
	/// Saves multiple entity objects.
	/// </summary>
	/// <param name="">It gets IDBEntity list and saves it to the database file.</param>
	void saveEntityObjects(DBEntityList);

	/// <summary>
	/// Queries the with entity object.
	/// </summary>
	/// <param name="queryObj">The query object.</param>
	/// <returns></returns>
	DBEntityList queryWithEntityObject(IDBEntity* queryObj);

	/// <summary>
	/// Queries all entities with the reference object.
	/// </summary>
	/// <param name="queryObj">The query object.</param>
	/// <returns></returns>
	DBEntityList queryAll(IDBEntity* queryObj);


	/// <summary>
	/// Updates the record with update entity object.
	/// </summary>
	/// <param name="queryObj">The query object.</param>
	/// <param name="newObj">The new object.</param>
	void updateWithEntityObject(IDBEntity* queryObj, IDBEntity* newObj);


	/// <summary>
	/// Deletes the entity object.
	/// </summary>
	/// <param name="objToBeDeleted">The object to be deleted.</param>
	void deleteEntityObject(IDBEntity* objToBeDeleted);
};

#endif /* CDATABASEOBJECTHANDLER_H */

