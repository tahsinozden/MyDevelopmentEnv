/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   main.cpp
 * Author: tahsin
 *
 * Created on 09 Haziran 2016 Perşembe, 18:05
 */

#include <cstdlib>
#include <memory>
#include <fstream>

#include "CEmployee.h"
#include "CDatabaseObjectHandler.h"

using namespace std;

void testInsert();
void testQuery();
void testQueryAll();
void testUpdate();
void testDelete();

/*
 * This is a No-SQL C++ library. It has its newly designed database structure.
 * C++11 features are used that's -std=c++11 needs to be passed to the compiler.
 * 
 * 
 * TODO: Handle null integer initialization, if the integer number not initialized, convert it to "" string
 * TODO: Implement multiple records saving at once
 * TODO: Integrate Logger API to the application
 */
int main(int argc, char** argv) {

	testQueryAll();
	//testUpdate();
	//testQuery();
	//testDelete();
	//testInsert();
    return 0;
}

void testInsert() {
	CEmployee* emp = new CEmployee("new dev", "doe", "software developer", "male", 17);
	CDatabaseObjectHandler* dbHandler = new CDatabaseObjectHandler();
	dbHandler->saveEntityObject(emp);
}

void testQuery() {
	CDatabaseObjectHandler* dbHandler = new CDatabaseObjectHandler();
	CEmployee* queryEmp = new CEmployee("new emp", "", "", "", 37);
	DBEntityList res = dbHandler->queryWithEntityObject(queryEmp);
	std::cout << "Query Result Matches : " << res.size() << std::endl;
	for (int i = 0; i < res.size(); i++) {
		std::cout << ((CEmployee*)(res.at(i)))->toString() << std::endl;
	}
}

void testQueryAll() {
	CDatabaseObjectHandler* dbHandler = new CDatabaseObjectHandler();
	CEmployee* queryEmp = new CEmployee();
	DBEntityList res = dbHandler->queryAll(queryEmp);
	std::cout << "Query Result Matches : " << res.size() << std::endl;
	for (int i = 0; i < res.size(); i++) {
		std::cout << ((CEmployee*)(res.at(i)))->toString() << std::endl;
	}
}

void testUpdate() {
	CDatabaseObjectHandler* dbHandler = new CDatabaseObjectHandler();
	CEmployee* queryEmp = new CEmployee("master dev", "", "", "", 17);

	CEmployee* updateObj = new CEmployee("soft dev1", "dev", "", "", 37);
	dbHandler->updateWithEntityObject(queryEmp, updateObj);
}
void testDelete() {
	CDatabaseObjectHandler* dbHandler = new CDatabaseObjectHandler();
	CEmployee* queryEmp = new CEmployee("news dev", "", "", "", 17);
	dbHandler->deleteEntityObject(queryEmp);
}