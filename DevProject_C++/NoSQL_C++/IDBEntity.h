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

class IDBEntitry{
public:
    virtual PairList getDBObjectFields() = 0;
    virtual std::string getDBObjectName() = 0;
    virtual StringMap getMappedObject() = 0;
    virtual IDBEntitry* getEntityObjFromMapping(StringMap) = 0;
    std::string getName();
};

#endif /* IDBENTITY_H */

