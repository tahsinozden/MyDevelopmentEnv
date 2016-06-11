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

    bool fexists(std::string filename);
    bool checkObjectHeaderExist(std::string fileName, std::string expectedHeader);
    std::vector<std::string> getElementsFromString(std::string str);
	std::vector<int>* getMatchIndexes(IDBEntitry* queryObj);
	std::ofstream* getEntityObjectWriter(IDBEntitry* obj, WRITER_MODE mode);
	std::ifstream* getEntityObjectReader(IDBEntitry* obj);
	void closeObjectWriter();
	void closeObjectReader();

public:
    enum DB_OBJECT_FIELD_TYPE{
        INT,
        DOUBLE,
        TEXT        
    };
    
	CDatabaseObjectHandler();
    void saveEntityObject(IDBEntitry*);
    DBEntityList queryWithEntityObject(IDBEntitry* queryObj);
	void updateWithEntityObject(IDBEntitry* queryObj, IDBEntitry* newObj);
    
};

#endif /* CDATABASEOBJECTHANDLER_H */

