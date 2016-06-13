/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   Logger.h
 * Author: tahsin
 *
 */

#ifndef LOGGER_H
#define LOGGER_H
#include <thread>
#include <fstream>
#include <chrono>
#include <ctime>
#include <mutex>

enum LOG_LEVEL {
    INFO,
    WARN,
    ERROR,
    DEBUG,
    ALL
};

class Logger {
private:
    std::ofstream _logger;
    std::mutex _mu;
    LOG_LEVEL m_LogLevel;
    std::string  m_TimeStamp;
    bool fexists(std::string filename);
public:
    Logger(){}
    
    ~Logger(){
        _logger.close();
    }
    
    void initialize(std::string logFileName, LOG_LEVEL logLevel, std::string format);
    void info(std::string);
    void warn(std::string);
    void error(std::string);
    void debug(std::string);    
    std::string getTimeStamp();
    void setLogLevel(LOG_LEVEL LogLevel);
    LOG_LEVEL getLogLevel() const;
    std::ofstream getLogger() const;
};

#endif /* LOGGER_H */

