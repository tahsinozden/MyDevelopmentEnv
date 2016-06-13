/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   main.cpp
 * Author: tahsin
 *
 */

#include <cstdlib>
#include "Logger.h"
#include <thread>

void test(){
    Logger *log = new Logger();
    log->initialize("newfile.log", ALL, "");
    log->info("First message for the log.");
    log->error("This is an error!");
}

void testLogger1(Logger& log){
    log.info("Log from " + std::string(__FUNCTION__));
}

void testLogger2(Logger& log){
    log.info("Log from " + std::string(__FUNCTION__));
}

void testLogs(){
    Logger log;
    log.initialize("newfile.log", ALL, "");
    std::thread t1(testLogger1, std::ref(log));
    std::thread t2(testLogger2, std::ref(log));
    t1.join();
    t2.join();
}
/*
 * 
 */
int main(int argc, char** argv) {
    test();
    testLogs();
    return 0;
}

