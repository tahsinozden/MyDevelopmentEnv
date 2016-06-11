/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   Common.h
 * Author: tahsin
 *
 * Created on 09 Haziran 2016 Perşembe, 18:40
 */

#ifndef COMMON_H
#define COMMON_H

#include<vector>
#include "IDBEntity.h"
#include <exception>

typedef std::vector<IDBEntitry*> DBEntityList; 
typedef std::map<std::string, std::string> StringMap;
typedef std::pair<std::string, std::string> StringPair;

bool copyFile(std::string srcFile, std::string dstFile);

#endif /* COMMON_H */

