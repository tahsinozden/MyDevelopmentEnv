/* 
 * File:   Array.h
 * Author: Tahsin
 *
 * Created on 04 Ekim 2014 Cumartesi, 22:18
 */

#ifndef ARRAY_H
#define	ARRAY_H

const int MAX_LENGTH = 10000;

enum Exceptions
{
     OutOfSize,
     InvalidIndex,     
     NotInitialized,
     InvalidData,
     ExpandArray        
};

class DynamicArray{
private:
    int *elements;
    int *firstPos;
    int *lastPos;
    int TEMP_MAX_SIZE;
    int currentIndex;
    int isFirstElement;
    void Copy(int *temp);
    void ExpandArrayFunc();
    
public:
    DynamicArray();
    ~DynamicArray();
    int* Begin();
    int* End();
    int Size();
    int Length();
    bool pushback(int x);
    bool pushback(int x,int index);
    int pop();
    int pop(int index);
    bool pop(int &data,int index);
    void operator[](int index);
    void Remove();
    void Remove(int index);
    
};


#endif	/* ARRAY_H */

