/* 
 * File:   main.cpp
 * Author: Tahsin
 *
 * Created on 04 Ekim 2014 Cumartesi, 22:17
 */

#include <cstdlib>

#include "Array.h"
#include <iostream>
using namespace std;

/*
 * 
 */
int main(int argc, char** argv) {

    DynamicArray ar;
    //ar.pushback(2453);
    
    for(int i=0; i<1002; i++){
        //cout << ar.pushback(i*i) << endl;
        if(!ar.pushback(i)) break;
    }
    /*
    cout << "pop : " << ar.pop() << endl;
    cout << ar.pop(55) << endl;
    ar.Remove();
    ar.Remove();
    ar.Remove();
    
    cout << "pop : " << ar.pop() << endl;
    
    cout << "23. : " << ar.pop(23) << endl;
    cout << "pop : " << ar.pop() << endl;
    ar.Remove(23);
    cout << "23. : " << ar.pop(23) << endl;
    cout << "pop : " << ar.pop() << endl;
    */
    cout << "- 23. : " << ar.pop(23) << endl;
    cout << "- pop : " << ar.pop() << endl;
    ar.pushback(12,23);
    cout << "- 23. : " << ar.pop(23) << endl;
    cout << "- pop : " << ar.pop() << endl;
    
    cout << "size = " << ar.Size() << endl;
    cout << "length = " << ar.Length() << endl;
    return 0;
}

