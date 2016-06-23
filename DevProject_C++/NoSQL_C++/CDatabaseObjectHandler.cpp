/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

#include "CDatabaseObjectHandler.h"
#include "CEmployee.h"
#include <algorithm>

CDatabaseObjectHandler::CDatabaseObjectHandler() {
	currentEntityObjectFile = "";
}

void CDatabaseObjectHandler::saveEntityObject(IDBEntity* dbEntity) {
    PairList lst = dbEntity->getDBObjectFields();
    std::string header = "";
    std::string record = "";

    bool addHeaderFlag = false;
    StringMap recordMapping = dbEntity->getMappedObject();

    // create record
    for (int i = 0; i < lst.size(); i++) {
        record += recordMapping[lst.at(i).first] + "|";
    }

	// add new line to the end of the record
	record += "\n";

	// write new record to the file
	writeObjectToDB(dbEntity, APPEND, record);

}

void CDatabaseObjectHandler::saveEntityObjects(DBEntityList objList) {
	if (objList.empty()) {
		std::cout << "The list is empty!" << std::endl;
		return;
	}

	// iterate over list
	for (size_t i = 0; i < objList.size(); i++) {
		IDBEntity* dbEntity = objList.at(i)->clone();

		PairList lst = dbEntity->getDBObjectFields();
		std::string header = "";
		std::string record = "";
		StringMap recordMapping = dbEntity->getMappedObject();

		// create record
		for (int i = 0; i < lst.size(); i++) {
			record += recordMapping[lst.at(i).first] + "|";
		}

		// add new line to the end of the record
		record += "\n";

		// write new record to the file
		writeObjectToDB(dbEntity, APPEND, record);
	}

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

DBEntityList CDatabaseObjectHandler::queryWithEntityObject(IDBEntity* queryObj) {

	m_matchedIndexes = new std::vector<int>();
	int currentIndex = 0;
    StringMap queryObjMap = queryObj->getMappedObject();
    
    std::string header = "";
    std::string record = "";
    DBEntityList entityList;
    PairList pairsFromDB;
    StringMap mapFromDB;
    
	// get all entities from DB
	DBEntityList allEntities = queryAll(queryObj);
	bool matchFlag = true;

	// since the first line is header line, increment counter by 1
	++currentIndex;

	// iterate over all records
	for (int i = 0; i < allEntities.size(); i++) {
		matchFlag = true;
		// get object in mapping type for each record
		mapFromDB = allEntities.at(i)->getMappedObject();
		// check matching fields of the query object
		typedef StringMap::iterator it_type;
		for (it_type iterator = queryObjMap.begin(); iterator != queryObjMap.end(); iterator++) {
			// iterator->first = key
			// iterator->second = value
			std::string key = iterator->first;
			std::string value = iterator->second;

			// check if the value is not null
			if (value != "") {
				// check the key in the map from db
				if (mapFromDB[key] != value) {
					matchFlag = false;
					break;
				}
			}
		}

		if (matchFlag) {

			// get reference object
			IDBEntity* entity = queryObj->clone();
			// fill the object with new data
			entity = entity->getEntityObjFromMapping(mapFromDB);
			entityList.push_back(entity);

			// this part is added for update action to detect the indexes to be updated
			m_matchedIndexes->push_back(currentIndex);
		}
		else {
			//std::cout << "No match!" << std::endl;
		}
		++currentIndex;

	}
    
    return entityList;
}

DBEntityList CDatabaseObjectHandler::queryAll(IDBEntity * queryObj) {
	// get all data and return it
	DBEntityList lst = getAllEntitiesFromDB(queryObj);
	return lst;
}

void CDatabaseObjectHandler::updateWithEntityObject(IDBEntity * queryObj, IDBEntity * newObj) {
	// check both entities have the same type
	if (queryObj->getDBObjectName() != newObj->getDBObjectName() ) {
		std::cerr << "ERROR - Query object is different than update object!" << std::endl;
		return;
	}

	std::vector<int>* indexes = getMatchIndexes(queryObj);
	std::vector<int>::iterator iter;
	for (iter = indexes->begin(); iter != indexes->end(); ++iter) {
		std::cout << "Index : " << *iter << ", ";
	}
	std::cout << std::endl;

	// if there is no match, terminate the function
	if (indexes->size() == 0) {
		std::cout << "Nothing to update!" << std::endl;
		return;
	}

	std::string line = "";
	std::string linesForFile = "";

	std::ifstream* reader = getEntityObjectReader(queryObj);
	PairList lst = newObj->getDBObjectFields();
	StringMap recordMapping = newObj->getMappedObject();

	int tmpCounter = 0;
	while (std::getline(*reader, line)) {
		if (std::find(indexes->begin(), indexes->end(), tmpCounter) != indexes->end()) {
			// we have an index match
			std::string record = "";
			// create record
			for (int i = 0; i < lst.size(); i++) {
				record += recordMapping[lst.at(i).first] + "|";
			}
			// insert the new record to the file
			linesForFile += record + "\n";
		}
		else {
			linesForFile += line + "\n";
		}
		++tmpCounter;
	}

	// close the reader
	closeObjectReader();

	// get writer in create mode since we need to write all new data
	//std::ofstream* writer = getEntityObjectWriter(queryObj, CREATE);
	//*writer << linesForFile;
	//closeObjectWriter();
	writeObjectToDB(queryObj, CREATE, linesForFile);

}

void CDatabaseObjectHandler::deleteEntityObject(IDBEntity * objToBeDeleted) {

	// get all entities
	DBEntityList objList = queryAll(objToBeDeleted);
	std::string records = "";
	// add header to the records
	records += createHeaderFromEntityObject(objToBeDeleted) + "\n";

	// this vector is filled after calling queryWithEntityObject function.
	std::vector<int>* indexes = getMatchIndexes(objToBeDeleted);

	// if there is no match, terminate the function
	if (indexes->size() == 0) {
		std::cout << "No matches. Nothing to delete!" << std::endl;
		return;
	}

	// iterate over all objects
	for (int i = 0; i < objList.size(); i++) {
		// if indexes are matched then continue with the next record to delete the object
		// i+1 index is checked because matched indexes return line numbers including header line
		if (std::find(indexes->begin(), indexes->end(), i+1) != indexes->end()) {
			//std::cout << "Index : " << i+1 << std::endl;
			continue;
		}
		IDBEntity* entity = objList.at(i);
		// create record line
		records += createRecordFromEntityObject(entity);
		// add new line after each record
		records += "\n";
	}

	// write records to the database file
	writeObjectToDB(objToBeDeleted, CREATE, records);
}


std::vector<std::string> CDatabaseObjectHandler::getElementsFromString(std::string str) {
	std::vector<std::string> items;
	std::string delimiter = "|";

	size_t pos = 0;
	std::string token;
	while ((pos = str.find(delimiter)) != std::string::npos) {
		token = str.substr(0, pos);
		items.push_back(token);
		str.erase(0, pos + delimiter.length());
	}
	return items;
}

std::vector<int>* CDatabaseObjectHandler::getMatchIndexes(IDBEntity * queryObj) {
	m_matchedIndexes = new std::vector<int>();
	queryWithEntityObject(queryObj);
	return m_matchedIndexes;
}

std::ofstream* CDatabaseObjectHandler::getEntityObjectWriter(IDBEntity * obj, WRITER_MODE mode) {
	std::string objName = obj->getDBObjectName() + ".tbl";
	std::string tmpObjName = objName + ".tmp";

	// set current entity object file for the file to be processed in close file action
	currentEntityObjectFile = objName;

	PairList lst = obj->getDBObjectFields();
	StringMap queryObjMap = obj->getMappedObject();

	std::string header = "";
	std::string record = "";
	DBEntityList entityList;
	PairList pairsFromDB;
	StringMap mapFromDB;

	// copy original file to a temp one
	copyFile(objName, tmpObjName);

	// create header to check
	for (int i = 0; i < lst.size(); i++) {
		header += lst.at(i).first + "|";
		// initialize the pair
		pairsFromDB.push_back(StringPair(lst.at(i).first, ""));
		mapFromDB.insert(StringPair(lst.at(i).first, ""));
	}

	if (!checkObjectHeaderExist(tmpObjName, header)) {
		// if the file is malformed or not exist,
		// create and format the file
		if (!m_dbWriter.is_open()) {
			m_dbWriter.open(tmpObjName.c_str());
			m_dbWriter << header << std::endl;
			m_dbWriter.close();
		}
		else {
			m_dbWriter.close();
			m_dbWriter.open(tmpObjName.c_str());
			m_dbWriter << header << std::endl;
			m_dbWriter.close();
		}

	}
	// if the format is ok, then simply open the file
	else {
		if (mode == APPEND) {
			// open in append mode
			m_dbWriter.open(tmpObjName.c_str(), std::ios::app);
		}
		else {
			// open in create mode, will delete all data inside file 
			m_dbWriter.open(tmpObjName.c_str(), std::ios::out);
		}

	}

	if (!m_dbWriter.is_open()) {
		if (mode == APPEND) {
			// open in append mode
			m_dbWriter.open(tmpObjName.c_str(), std::ios::app);
		}
		else {
			// open in create mode, will delete all data inside file 
			m_dbWriter.open(tmpObjName.c_str(), std::ios::out);
		}
	}
	
	return &m_dbWriter;
}

std::ifstream* CDatabaseObjectHandler::getEntityObjectReader(IDBEntity * obj) {
	std::string objName = obj->getDBObjectName() + ".tbl";
	PairList lst = obj->getDBObjectFields();
	StringMap queryObjMap = obj->getMappedObject();

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
	}

	if (!checkObjectHeaderExist(objName, header)) {
		// if the file is malformed or not exist,
		// create and format the file
		if (!m_dbReader.is_open()) {
			m_dbWriter.open(objName.c_str());
			m_dbWriter << header << std::endl;
			m_dbWriter.close();
		}
		else {
			m_dbWriter.open(objName.c_str());
			m_dbWriter << header << std::endl;
			m_dbWriter.close();
		}

	}
	// if the format is ok, then simply open the file
	else {
		m_dbReader.open(objName.c_str());
	}

	if (!m_dbReader.is_open()) {
		// open the file for reading
		m_dbReader.open(objName.c_str());
	}

	return &m_dbReader;
}

void CDatabaseObjectHandler::closeObjectWriter() {
	if (m_dbWriter.is_open()) {
		m_dbWriter.close();
		if (currentEntityObjectFile != "") {
			std::string tmpFile = currentEntityObjectFile + ".tmp";
			std::string bakFile = currentEntityObjectFile + ".bak";

			// create a backup file
			copyFile(currentEntityObjectFile, bakFile);

			// if the removing the original file is successful
			if (std::remove(currentEntityObjectFile.c_str()) == 0) {
				std::cout << "renaming the file... " << tmpFile << std::endl;
				// then rename the temp file
				int res = std::rename(tmpFile.c_str(), currentEntityObjectFile.c_str());
				if (res == 0) {
					std::cout << "Success!" << std::endl;
					// remove backup file
					std::remove(bakFile.c_str());
				}
				else {
					std::cerr << "ERROR - " << tmpFile << " couldn't be renamed!" << std::endl;
					// load backup file
					copyFile(bakFile, currentEntityObjectFile);
					// remove temp file
					std::remove(tmpFile.c_str());
				}
			}
			else {
				std::cerr << currentEntityObjectFile << " couldn't be removed!" << std::endl;
				// remove backup file
				std::remove(bakFile.c_str());
			}

			// set to the default value
			currentEntityObjectFile = "";
		}

	}
}

void CDatabaseObjectHandler::closeObjectReader() {
	if (m_dbReader.is_open()) {
		m_dbReader.close();
	}
}

void CDatabaseObjectHandler::writeObjectToDB(IDBEntity * refEntityObject, WRITER_MODE openMode, std::string toBeWritten) {
	// lock the writer to make it thread-safe
	std::lock_guard<std::mutex> fileLocker(m_fileLockMutex);

	std::string objName = refEntityObject->getDBObjectName() + ".tbl";
	std::string tmpObjName = objName + ".tmp";

	// set current entity object file for the file to be processed in close file action
	currentEntityObjectFile = objName;

	PairList lst = refEntityObject->getDBObjectFields();
	StringMap queryObjMap = refEntityObject->getMappedObject();

	std::string header = "";
	std::string record = "";
	DBEntityList entityList;
	PairList pairsFromDB;
	StringMap mapFromDB;

	// check db file, if not exist create an empty one
	if (!fexists(currentEntityObjectFile)) {
		std::ofstream of;
		of.open(currentEntityObjectFile, std::ios::out);
		of.close();
	}

	// copy original file to a temp one
	copyFile(objName, tmpObjName);

	// create header to check
	for (int i = 0; i < lst.size(); i++) {
		header += lst.at(i).first + "|";
		// initialize the pair
		pairsFromDB.push_back(StringPair(lst.at(i).first, ""));
		mapFromDB.insert(StringPair(lst.at(i).first, ""));
	}

	if (!checkObjectHeaderExist(tmpObjName, header)) {
		// if the file is malformed or not exist,
		// create and format the file
		if (!m_dbWriter.is_open()) {
			m_dbWriter.open(tmpObjName.c_str());
			m_dbWriter << header << std::endl;
			m_dbWriter.close();
		}
		else {
			m_dbWriter.close();
			m_dbWriter.open(tmpObjName.c_str());
			m_dbWriter << header << std::endl;
			m_dbWriter.close();
		}

	}
	// if the format is ok, then simply open the file
	else {
		if (openMode == APPEND) {
			// open in append mode
			m_dbWriter.open(tmpObjName.c_str(), std::ios::app);
		}
		else {
			// open in create mode, will delete all data inside file 
			m_dbWriter.open(tmpObjName.c_str(), std::ios::out);
		}

	}

	if (!m_dbWriter.is_open()) {
		if (openMode == APPEND) {
			// open in append mode
			m_dbWriter.open(tmpObjName.c_str(), std::ios::app);
		}
		else {
			// open in create mode, will delete all data inside file 
			m_dbWriter.open(tmpObjName.c_str(), std::ios::out);
		}
	}

	// after all checks, ready to write
	// here is where data is written to the file
	m_dbWriter << toBeWritten;

	// close the writer
	closeObjectWriter();
}

void CDatabaseObjectHandler::readObjectFromDB() {
}

/*
	Retrieves all entity records from related DB.(file)
*/
DBEntityList CDatabaseObjectHandler::getAllEntitiesFromDB(IDBEntity * refObj) {
	// lock the file untill the scope is finished
	std::lock_guard<std::mutex> locker(m_fileLockMutex);

	std::string objName = refObj->getDBObjectName() + ".tbl";
	PairList lst = refObj->getDBObjectFields();
	StringMap queryObjMap = refObj->getMappedObject();

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

			IDBEntity* entity = refObj->clone();
			// fill the object with new data
			entity = (entity->getEntityObjFromMapping(mapFromDB))->clone();
			// add it to the stack
			entityList.push_back(entity);
		}

	}
	else {
		std::cout << "error while reading the file " << objName << std::endl;
	}

	m_dbReader.close();

	return entityList;

}

std::string CDatabaseObjectHandler::createRecordFromEntityObject(IDBEntity* obj) {

	std::string record = "";
	StringMap recordMapping = obj->getMappedObject();
	PairList lst = obj->getDBObjectFields();
	for (int i = 0; i < lst.size(); i++) {
		// get ordered pairs from getDBObjectFields, then find them in the map
		// lst.at(i).first returns field name
		record += recordMapping[lst.at(i).first] + "|";
	}

	return record;
}

std::string CDatabaseObjectHandler::createHeaderFromEntityObject(IDBEntity * obj) {
	PairList objPairs = obj->getDBObjectFields();

	PairList::iterator it = objPairs.begin();
	std::string header = "";
	for (; it != objPairs.end(); ++it) {
		// get the value of the map
		header += it->first + "|";
	}

	return header;
}
