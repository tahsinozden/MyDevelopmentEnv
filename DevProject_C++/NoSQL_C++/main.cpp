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

/*
 * This is a No-SQL C++ library. It has its newly designed database structure.
 * C++11 features are used that's -std=c++11 needs to be passed to the compiler.
 * 
 * 
 * TODO: Handle null integer initialization, if the integer number not initialized, convert it to "" string
 * TODO: Implement delete record
 * TODO: Implement multiple records saving at once
 * TODO: Integrate Logger API to the application
 */
int main(int argc, char** argv) {
    CEmployee* emp = new CEmployee("john", "doe", "software developer", "male", 45);
    CDatabaseObjectHandler* dbHandler = new CDatabaseObjectHandler();
    //dbHandler->saveEntityObject(emp);
    
    CEmployee* queryEmp = new CEmployee("john", "", "", "", 45);
    DBEntityList res = dbHandler->queryWithEntityObject(queryEmp);
    
    std::cout << "Query Result Matches : " << res.size() << std::endl;
    for (int i = 0; i < res.size(); i++) {
        std::cout << ((CEmployee*)(res.at(i)))->toString() << std::endl;
    }
	queryEmp = new CEmployee("jack", "", "", "", 45);
	CEmployee* updateObj = new CEmployee("master", "dev", "software engineer", "male", 37);
	dbHandler->updateWithEntityObject(queryEmp, updateObj);

    return 0;
}

