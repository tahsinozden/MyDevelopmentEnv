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

class CDatabaseObjectHandler{
  
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

    bool fexists(std::string filename);
    bool checkObjectHeaderExist(std::string fileName, std::string expectedHeader);
    std::vector<std::string> getElementsFromString(std::string str);
	std::vector<int>* getMatchIndexes(IDBEntity* queryObj);
	std::ofstream* getEntityObjectWriter(IDBEntity* obj, WRITER_MODE mode);
	std::ifstream* getEntityObjectReader(IDBEntity* obj);
	void closeObjectWriter();
	void closeObjectReader();
	void writeObjectToDB(IDBEntity* refEntityObject, WRITER_MODE openMode, std::string toBeWritten);
	void readObjectFromDB();
	DBEntityList getAllEntitiesFromDB(IDBEntity* refObj);
	std::string createRecordFromEntityObject(IDBEntity* obj);
	std::string createHeaderFromEntityObject(IDBEntity* obj);

public:
    enum DB_OBJECT_FIELD_TYPE{
        INT,
        DOUBLE,
        TEXT        
    };
    
	CDatabaseObjectHandler();
    void saveEntityObject(IDBEntity*);
    DBEntityList queryWithEntityObject(IDBEntity* queryObj);
	DBEntityList queryAll(IDBEntity* queryObj);
	void updateWithEntityObject(IDBEntity* queryObj, IDBEntity* newObj);
	void deleteEntityObject(IDBEntity* objToBeDeleted);
};

#endif /* CDATABASEOBJECTHANDLER_H */

