//============================================================================
// Name        : ScientificCalculator.h
// Author      : tahsin
// Version     :
// Copyright   : 
// Description : ScientificCalculator in C++
//============================================================================

// TODO: Trigonometric functions will be added.
// TODO: User input check function will be made better
 
#ifndef SCIENTIFICCALCULATOR_H
#define	SCIENTIFICCALCULATOR_H

class CalcultationException {
    private:
        std::string m_sMsg;
    public:
        CalcultationException(std::string msg) : m_sMsg(msg) {};
        std::string getMessage(){
            return m_sMsg;
        }
};

class Calculator {
    private:
        std::string m_sExpStr;
        char m_cDefDel;
        std::vector<std::string>* getExpVector(); // returns the expression as a vector
        bool checkExp(); // checks the input expression validity
        void normalize(); // normalizes the input expression
        void expression_formatter(); // formats the user input expression
    public:
        Calculator(std::string str) : m_sExpStr(str), m_cDefDel(' ') {};
        double calculate(); // returns the calculation result, throws CalcultationException

};


#endif	/* SCIENTIFICCALCULATOR_H */