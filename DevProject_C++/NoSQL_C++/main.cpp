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
#include "Integer.h"
#include "String.h"
#include "Double.h"
#include "CStocks.h"

using namespace std;

void testInsert();
void testQuery();
void testQueryAll();
void testUpdate();
void testDelete();
void testTypes();
void testDouble();
void testStocks();
void testInsertMultipleObjects();
/*
 * This is a No-SQL C++ library. It has its newly designed database structure.
 * C++11 features are used that's -std=c++11 needs to be passed to the compiler.
 * 
 * 
 * TODO: Integrate Logger API to the application
 */
int main(int argc, char** argv) {

	//testQueryAll();
	//testUpdate();
	//testQuery();
	//testDelete();
	//testInsert();
	//testTypes();
	//testDouble();
	//testStocks();
	testInsertMultipleObjects();
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
void testTypes() {
	int(*func)();
	func = std::numeric_limits<int>::quiet_NaN;
	std::string a = "";
	//int b = std::stoi(a);
	Integer i;
	std::cout << i.getStringValue() << std::endl;
	std::cout << i.getIntValue() << std::endl;

	Integer x(34);
	Integer y(0);
	Integer z;
	String str("sfsdfs");
	try {
		std::cout << (x + y).getIntValue() << std::endl;
		//z = x / y;
		Integer v = Integer::valueOf(str);
		std::cout << v.getIntValue() << std::endl;
	}
	catch (const std::exception& e) {

		//std::cout << z.getIntValue() << std::endl;
		std::cout << "Exception : " << e.what() << std::endl;
	}

}

void testDouble() {
	Double d(3.67);
	Integer i(3);
	i = d;
	std::cout << d.getDoubleValue() / 2 << std::endl;
	std::cout << i.getIntValue() / 2 << std::endl;
}

void testStocks() {
	CDatabaseObjectHandler* dbHandler = new CDatabaseObjectHandler();
	Integer amt(23);
	Double per(23.4);
	CStocks* st = new CStocks(String("phone"), amt, per);
	dbHandler->saveEntityObject(st);


	Integer amt1;
	Double per1;
	CStocks* st1 = new CStocks(String("phone"), amt1, per1);
	DBEntityList res = dbHandler->queryWithEntityObject(st1);
	std::cout << "Query Result Matches : " << res.size() << std::endl;
	for (int i = 0; i < res.size(); i++) {
		std::cout << ((CStocks*)(res.at(i)))->toString() << std::endl;
	}

	CStocks* queryPc = new CStocks(String("pc"), amt1, per1);
	res = dbHandler->queryWithEntityObject(queryPc);
	std::cout << "Query Result Matches : " << res.size() << std::endl;
	for (int i = 0; i < res.size(); i++) {
		std::cout << ((CStocks*)(res.at(i)))->toString() << std::endl;
	}
	amt1 = Integer(45);
	per1 = Double(67.9);
	CStocks* updatePc = new CStocks(String("pc"), amt, per);
	dbHandler->updateWithEntityObject(queryPc, updatePc);
}

void testInsertMultipleObjects() {
	DBEntityList lst;
	lst.push_back(new CStocks(String("pc"), Integer(10), Double(12.5)));
	lst.push_back(new CStocks(String("tablet"), Integer(2), Double(21.5)));
	lst.push_back(new CStocks(String("phone"), Integer(100), Double(54.37)));
	lst.push_back(new CEmployee("john","doe", "developer", "male", 34));

	CDatabaseObjectHandler* dbHandler = new CDatabaseObjectHandler();
	dbHandler->saveEntityObjects(lst);
}