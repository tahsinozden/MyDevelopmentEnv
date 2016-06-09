/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   CEmployee.h
 * Author: tahsin
 *
 * Created on 09 Haziran 2016 Perşembe, 18:17
 */

#ifndef CEMPLOYEE_H
#define CEMPLOYEE_H
#include "IDBEntity.h"
#include "Common.h"
#include <string>

class CEmployee : public IDBEntitry {
    private:
        std::string m_name;
        std::string m_surname;
        std::string m_occupation;
        std::string m_gender;
        int m_age;
    
    public:
        CEmployee(){};
        CEmployee(       
                std::string name,
                std::string surname,
                std::string occupation,
                std::string gender,
                int age){
                     
                    m_name = name;
                    m_surname = surname;
                    m_occupation = occupation;
                    m_gender = gender;
                    m_age = age;
        }
        
        virtual std::string getDBObjectName() override;

        virtual PairList getDBObjectFields() override;
        
        StringMap getMappedObject() override;
        
        IDBEntitry* getEntityObjFromMapping(StringMap) override;

//        CEmployee* getEntityObjFromMapping1(StringMap);
        
        void setAge(int age);
        int getAge() const;
        void setGender(std::string gender);
        std::string getGender() const;
        void setOccupation(std::string occupation);
        std::string getOccupation() const;
        void setSurname(std::string surname);
        std::string getSurname() const;
        void setName(std::string name);
        std::string getName() const;
        
        std::string toString();
        
            
}; 

#endif /* CEMPLOYEE_H */

