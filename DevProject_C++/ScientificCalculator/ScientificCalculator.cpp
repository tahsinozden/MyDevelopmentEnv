//============================================================================
// Name        : ScientificCalculator.cpp
// Author      : tahsin
// Version     :
// Copyright   : 
// Description : ScientificCalculator in C++
//============================================================================

#include <iostream>
#include <vector>
#include <stdlib.h>
#include "ScientificCalculator.h"


std::vector<std::string>* Calculator::getExpVector(){
    int loc = 0;
    std::vector<std::string> *vals = new std::vector<std::string>();
    for ( int idx=0; idx < m_sExpStr.length(); idx++){
        if ( m_sExpStr[idx] == m_cDefDel ){
            std::string tmp = "";
            int tmpLen = idx - loc;
            tmp = m_sExpStr.substr(loc, tmpLen);
            // push the parsed item to the stack
            vals->push_back(tmp);
            // increment the location varible for next parsing
            loc = idx + 1;
        }
    }
    return vals;
}


double Calculator::calculate(){
    // format the user input
    expression_formatter();
    normalize();
    
    if (checkExp() != true){
        std::cout << "Calculation failed!. Check input expression!" << std::endl;
        // if check fails, throw an exception
        throw new CalcultationException("Error Occured!");
    }
    std::vector<std::string>* exps = getExpVector();
    std::vector<double> *vals = new std::vector<double>();
    std::vector<std::string> *ops = new std::vector<std::string>();

    std::vector<std::string>::iterator item = exps->begin();
    for (; item != exps->end() ; item++){
        if ( *item == "("){}
        else if (*item == "+") ops->push_back(std::string(*item));
        else if (*item == "-") ops->push_back(std::string(*item));
        else if (*item == "*") ops->push_back(std::string(*item));
        else if (*item == "/") ops->push_back(std::string(*item));
        else if (*item == ")") {
            double val = vals->back();
            std::string op = ops->back();
            vals->pop_back();
            ops->pop_back();

            if ( op == "+") {
                val = vals->back() + val;
                vals->pop_back();
            }
            else if ( op == "-"){
                val = vals->back() - val;
                vals->pop_back();
            }
            else if ( op == "*"){
                val = vals->back() * val;
                vals->pop_back();
            }
            else if ( op == "/"){
                val = vals->back() / val;
                vals->pop_back();
            }

            vals->push_back(val);
        }
        else {
            double d_val = atof( std::string(*item).c_str() );
            vals->push_back( d_val );

        }
    }
    if ( !vals->empty() ){
        return vals->back();
    }
    else{
        throw new CalcultationException("Stack Error!");
    }

}


bool Calculator::checkExp(){
    if (m_sExpStr == "") {
        std::cout << "Empty expression!" << std::endl;
        return false;
    }
    // check whitespaces
    // the last character must be default delimiter
    if ( m_sExpStr[m_sExpStr.length()-1] != m_cDefDel ) {
        std::cout << "The last character must end with whitespace!" << std::endl;
        return false;
    }

    bool bSpaceFlag = false;
    for ( int i=0; i < m_sExpStr.length(); i++){
        if (m_sExpStr[i] == m_cDefDel ) bSpaceFlag = false;
        else bSpaceFlag = true;
    }
    // if there is a missing white space between items
    if (bSpaceFlag) return bSpaceFlag;
    
    // check braces
    int iLeftBracesAmt = 0;
    int iRightBracesAmt = 0;
    for ( int i=0; i < m_sExpStr.length(); i++){
        if (m_sExpStr[i] == '(') iLeftBracesAmt++;
        else if (m_sExpStr[i] == ')') iRightBracesAmt++;
    }
    if ((iLeftBracesAmt == 0 || iRightBracesAmt == 0) || iLeftBracesAmt != iRightBracesAmt ){
        std::cout << "Check braces!" << std::endl;
        return false;
    }

    // return check passed
    return true;
}

void Calculator::normalize(){
    if ( m_sExpStr != "" && m_sExpStr[m_sExpStr.length()-1] != m_cDefDel ) {
        m_sExpStr += m_cDefDel;
    }
}

void Calculator::expression_formatter(){
    std::vector<char> *vec = new std::vector<char>();
    bool bNumberFlag = false;
    bool bOperandFlag = false;
    const char del = m_cDefDel;
    std::string exp = m_sExpStr;

    for (int i=0; i<exp.length(); i++){
        char tmpChar = exp[i]; 
        if ( tmpChar == '('){
            vec->push_back(tmpChar);
            vec->push_back(del);
            // since it '(' charachter set flags as false
            bNumberFlag = false;
            bOperandFlag = false;

        }
        else if ( tmpChar == ')'){
            vec->push_back(tmpChar);
            vec->push_back(del);
            // since it ')' charachter set flags as false
            bNumberFlag = false;
            bOperandFlag = false;
        }
        else if (tmpChar == '+' || tmpChar == '-' ||
                 tmpChar == '*' || tmpChar == '/')
        {
            if(bOperandFlag) {
                // if we encounter another operand, this operand can be due to sign of the following number
                vec->push_back(tmpChar);
                vec->push_back(del);
                // set number flag as true
                bOperandFlag = false;
                bNumberFlag = true;
            }
            else {
                // if we encounter an operand, push it to the stack
                // then set number flag as false since it is an operand
                // set operand flag as true to help to check if we encounter another operand
                // in the following character
                vec->push_back(tmpChar);
                vec->push_back(del);
                bOperandFlag = true;
                bNumberFlag = false;
            }
        }
        else if ( tmpChar >= '0' && tmpChar <= '9'){
            // if we encounter another number and the number flag is true,
            // it means that we still need to parse the number
            // but first remove the last added " " character 
            if (bNumberFlag){
                vec->pop_back();
            }

            vec->push_back(tmpChar);
            vec->push_back(del);
            bNumberFlag = true;
            bOperandFlag = false;
        }
        else if (tmpChar == '.'){
            // if we encounter "." value, it means we have double value
            if (bNumberFlag){
                vec->pop_back();
                vec->push_back(tmpChar);
                vec->push_back(del);
            }
        }
    }

    std::string sFormattedStr(vec->begin(), vec->end());
    m_sExpStr = sFormattedStr;
    std::cout << "Formatted User Input : " << m_sExpStr << std::endl;
}
