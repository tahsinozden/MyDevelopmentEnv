/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

#include "CDatabaseObjectHandler.h"
#include "CEmployee.h"

void CDatabaseObjectHandler::save2DB(IDBEntitry* dbEntity) {
    std::string objName = dbEntity->getDBObjectName() + ".tbl";
    PairList lst = dbEntity->getDBObjectFields();
    std::string header = "";
    std::string record = "";

    bool addHeaderFlag = false;
    StringMap recordMapping = dbEntity->getMappedObject();

    // create header
    for (int i = 0; i < lst.size(); i++) {
        header += lst.at(i).first + "|";
        record += recordMapping[lst.at(i).first] + "|";
    }

    if (!checkObjectHeaderExist(objName, header)) {
        m_dbWriter.open(objName.c_str());
        addHeaderFlag = true;
    } else {
        m_dbWriter.open(objName.c_str(), std::ios::app);
    }

    std::cout << "Header : " << header << std::endl;
    std::cout << "Record : " << record << std::endl;
    if (addHeaderFlag)
        m_dbWriter << header << std::endl;

    m_dbWriter << record << std::endl;
    m_dbWriter.close();

}

bool CDatabaseObjectHandler::fexists(std::string filename) {
    std::ifstream ifile;
    ifile.open(filename.c_str(), std::ios::in);
    // check we can open the file or not
    if (ifile.is_open()) {
        // if exists, close the file and return true
        ifile.close();
        return true;
    } else {
        return false;
    }
}

bool CDatabaseObjectHandler::checkObjectHeaderExist(std::string fileName, std::string expectedHeader) {
    // check file exist
    if (!fexists(fileName)) {
        //        throw ExceptionFileNotFound();
        return false;
    }

    std::ifstream ifile;
    ifile.open(fileName.c_str(), std::ios::in);

    std::string header;
    // read the first line for header
    ifile >> header;
    //    std::cout << "Header in the file : " << header << std::endl;
    //    std::cout << "Expected Header : " << expectedHeader << std::endl;
    ifile.close();

    return header == expectedHeader;
}

DBEntityList CDatabaseObjectHandler::queryWithEntityObject(IDBEntitry* queryObj) {
    std::string objName = queryObj->getDBObjectName() + ".tbl";
    PairList lst = queryObj->getDBObjectFields();
    StringMap queryObjMap = queryObj->getMappedObject();
    
    std::string header = "";
    std::string record = "";
    DBEntityList entityList;
    PairList pairsFromDB;
    StringMap mapFromDB;
    
    // create header to check
    for (int i = 0; i < lst.size(); i++) {
        header += lst.at(i).first + "|";
        // initialize the pair
        pairsFromDB.push_back(StringPair(lst.at(i).first, ""));
        mapFromDB.insert(StringPair(lst.at(i).first, ""));
        //        record += queryObj[lst.at(i).first] + "|";
    }

    if (!checkObjectHeaderExist(objName, header)) {
        // if the file is malformed or not exist,
        // create, format the file and exit
        m_dbWriter.open(objName.c_str());
        m_dbWriter << header << std::endl;
        m_dbWriter.close();
//        return;
    }
    std::string line = "";
    m_dbReader.open(objName.c_str());
    // read the header file and ignore it as a record
    std::getline(m_dbReader, record);
            
    if (m_dbReader.is_open()) {
        while (std::getline(m_dbReader, record)) {
            std::vector<std::string> tmpRec = getElementsFromString(record);
            
            for (int i = 0; i < tmpRec.size(); i++) {
                // fill records with the values from db
                pairsFromDB.at(i).second = tmpRec.at(i);
                mapFromDB[pairsFromDB.at(i).first] = tmpRec.at(i);
            }
            
            bool condFlag = true;
            // check fields of the query objects are matched or not
      
            typedef StringMap::iterator it_type;
            for(it_type iterator = queryObjMap.begin(); iterator != queryObjMap.end(); iterator++) {
                // iterator->first = key
                // iterator->second = value
                std::string key = iterator->first;
                std::string value = iterator->second;
                
                // check if the value is not null
                if (value != ""){
                    // check the key in the map from db
                    if (mapFromDB[key] != value){
                        condFlag = false;
                        break;
                    }
                }
            }
            
            if(condFlag){
                std::cout << "There is a match!" << std::endl;
                // entityList add here
//                CEmployee* emp = new CEmployee();
//                emp = (CEmployee*) emp->getEntityObjFromMapping1(mapFromDB);
//                std::cout << emp->toString() << std::endl;
                  CEmployee* emp = new CEmployee();
                  emp = (CEmployee*) emp->getEntityObjFromMapping(mapFromDB);
//                  std::cout << emp->toString() << std::endl;
                  entityList.push_back(emp);
            }
            else{
                std::cout << "No match!" << std::endl;
            }
        }
        
    } else {
        std::cout << "error!" << std::endl;
    }

    m_dbReader.close();
    
    return entityList;
}

std::vector<std::string> CDatabaseObjectHandler::getElementsFromString(std::string str) {
    std::vector<std::string> items;
    std::string delimiter = "|";

    size_t pos = 0;
    std::string token;
    while ((pos = str.find(delimiter)) != std::string::npos) {
        token = str.substr(0, pos);
        //        std::cout << token << std::endl;
        items.push_back(token);
        str.erase(0, pos + delimiter.length());
    }
    return items;
}
