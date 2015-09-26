//============================================================================
// Name        : main.cpp
// Author      : tahsin
// Version     :
// Copyright   : 
// Description : Implementation of a calculator in C++
//============================================================================

#include <iostream>
#include <vector>
#include <stdlib.h>
#include "ScientificCalculator.h"

int main() {
    std::cout << std::endl << "\t\t\t\t\t\t\tWelcome to ScientificCalculator v1.0" << std::endl; 
    std::cout<<"\t!!! Usage Instructions !!!" << std::endl;
    std::cout << "\tEach math expression (such as (6+7) ) must be enclosed by braces () i.e. ( 3 + (6 * ( 2 - 1 )) ), ( 5+ (8*2) )" << std::endl;
    std::cout << "\tWhitespaces are not important. Your input can be like (3+(6*(2-1)))" << std::endl;
    std::cout << "\tSample Expression : ( 6 + (( 5 * 4 )/2) )" << std::endl;
    std::cout << "\tFloating numbers are supported i.e. ( 12.5 - ( 1 / 2 ) )" << std::endl<< std::endl;
    std::string exps = "";

    // get  user input
    std::cout << "Input math expression : ";
    std::getline(std::cin, exps);
    try {
        Calculator calc(exps);
        std::cout << "Result = " << calc.calculate() << std::endl;    
    }
    catch(CalcultationException *ex){
        std::cout << "Exception : " << ex->getMessage() << std::endl;
    }

    return 0;
}
