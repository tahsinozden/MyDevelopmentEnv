/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

#include "CEmployee.h"

std::string CEmployee::getDBObjectName(){
    return "Employee";
}

PairList CEmployee::getDBObjectFields(){
    PairList list;
    list.push_back(std::pair<std::string,std::string>("name", "TEXT"));
    list.push_back(std::pair<std::string,std::string>("surname", "TEXT"));
    list.push_back(std::pair<std::string,std::string>("occupation", "TEXT"));
    list.push_back(std::pair<std::string,std::string>("gender", "TEXT"));    
    list.push_back(std::pair<std::string,std::string>("age", "INT"));   
    return list;
}

StringMap CEmployee::getMappedObject(){
    StringMap mapping;
    
    mapping.insert(std::pair<std::string, std::string>("name",m_name));
    mapping.insert(std::pair<std::string, std::string>("surname",m_surname));
    mapping.insert(std::pair<std::string, std::string>("occupation",m_occupation));
    mapping.insert(std::pair<std::string, std::string>("gender",m_gender));
    mapping.insert(std::pair<std::string, std::string>("age",std::to_string(m_age)));
    
    return mapping;
}

IDBEntitry* CEmployee::getEntityObjFromMapping(StringMap objMap){
    return new CEmployee(objMap["name"], objMap["surname"], objMap["occupation"], objMap["gender"], std::stoi(objMap["age"]));
}

//CEmployee* CEmployee::getEntityObjFromMapping1(StringMap objMap){
//    return new CEmployee(objMap["name"], objMap["surname"], objMap["occupation"], objMap["gender"], std::stoi(objMap["age"]));
//}

std::string CEmployee::toString() {
    return m_name + " " + m_surname + " " + m_occupation + " " + m_gender + " " + std::to_string( m_age); 
}


void CEmployee::setAge(int age) {
    m_age = age;
}

int CEmployee::getAge() const {
    return m_age;
}

void CEmployee::setGender(std::string gender) {
    m_gender = gender;
}

std::string CEmployee::getGender() const {
    return m_gender;
}

void CEmployee::setOccupation(std::string occupation) {
    m_occupation = occupation;
}

std::string CEmployee::getOccupation() const {
    return m_occupation;
}

void CEmployee::setSurname(std::string surname) {
    m_surname = surname;
}

std::string CEmployee::getSurname() const {
    return m_surname;
}

void CEmployee::setName(std::string name) {
    m_name = name;
}

std::string CEmployee::getName() const {
    return m_name;
}