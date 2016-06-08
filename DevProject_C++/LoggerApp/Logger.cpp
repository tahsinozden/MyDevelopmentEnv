/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

#include "Logger.h"
#include <iostream>

void Logger::setLogLevel(LOG_LEVEL LogLevel) {
    m_LogLevel = LogLevel;
}

LOG_LEVEL Logger::getLogLevel() const {
    return m_LogLevel;
}

void Logger::initialize(std::string logFileName, LOG_LEVEL logLevel, std::string format){
    try{
        if (fexists(logFileName)){
             _logger.open(logFileName, std::ios::app);
        }
        else {
            _logger.open(logFileName, std::ios::out);
        }

        if (! _logger.is_open()){
            std::cout << "Logger can't be initiated with the file name !" << logFileName << std::endl;
            return;
        }
    }
    // catch everything
    catch(...){
        std::cout << "Error occurred while initialization!" << std::endl;
        return;
    }
    
    // set log level
    m_LogLevel = logLevel;
    
}

void Logger::info(std::string msg){
    if (this->getLogLevel() == INFO || this->getLogLevel() == ALL){
        _logger << getTimeStamp() +  " - INFO - " << msg << std::endl;
    }
}

void Logger::warn(std::string msg){
    if (this->getLogLevel() == WARN || this->getLogLevel() == ALL){
        _logger << getTimeStamp() +  " - WARN - " << msg << std::endl;
    }
}

void Logger::error(std::string msg){
    if (this->getLogLevel() == ERROR || this->getLogLevel() == ALL){
        _logger << getTimeStamp() +  " - ERROR - " << msg << std::endl;
    }
}

void Logger::debug(std::string msg){
    if (this->getLogLevel() == DEBUG || this->getLogLevel() == ALL){
        _logger << getTimeStamp() +  " - DEBUG - " << msg << std::endl;
    }
}

std::string Logger::getTimeStamp() {
  time_t rawtime;
  struct tm * timeinfo;
  char buffer[80];

  time (&rawtime);
  timeinfo = localtime(&rawtime);

  strftime(buffer,80,"%Y-%m-%d %H:%M:%S",timeinfo);
  std::string str(buffer);
  this->m_TimeStamp = str;
  
  return str;
}

bool Logger::fexists(std::string filename) {
  std::ifstream ifile;
  ifile.open(filename, std::ios::in);
  // check we can open the file or not
  if (ifile.is_open()){
      // if exists, close the file and return true
      ifile.close();
      return true;
  }
  else {
      return false;
  }
}