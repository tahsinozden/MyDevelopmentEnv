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

void test(){
    Logger *log = new Logger();
    log->initialize("newfile.log", ALL, "");
    log->info("First message for the log.");
    log->error("This is an error!");
}

/*
 * 
 */
int main(int argc, char** argv) {
    test();
    return 0;
}

