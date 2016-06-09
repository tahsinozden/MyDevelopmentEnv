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

class CDatabaseObjectHandler{
  
private:
    typedef std::vector<std::pair<std::string, std::string> > PairList;
    std::string m_dbObjectName;
    std::vector<std::string> m_dbObjectFields;
    PairList m_dbObjFieldPairs;
    std::ofstream m_dbWriter;
    std::ifstream m_dbReader;
    
    bool fexists(std::string filename);
    bool checkObjectHeaderExist(std::string fileName, std::string expectedHeader);
    std::vector<std::string> getElementsFromString(std::string str);
public:
    enum DB_OBJECT_FIELD_TYPE{
        INT,
        DOUBLE,
        TEXT        
    };
    
    void save2DB(IDBEntitry*);
    DBEntityList queryWithEntityObject(IDBEntitry* queryObj);
//    DBEntityList getDBObjectsByQueryEntity(IDBEntitry*);
    
};

#endif /* CDATABASEOBJECTHANDLER_H */

