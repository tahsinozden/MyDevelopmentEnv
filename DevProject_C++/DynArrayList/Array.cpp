#include <iostream>
#include "Array.h"
using namespace std;

const int INT_MIN = -32767; 

DynamicArray::DynamicArray(){
        TEMP_MAX_SIZE = 10;
        elements = new int[TEMP_MAX_SIZE];
        elements[0] = INT_MIN;
        currentIndex = 0;
        isFirstElement = true;
    }
DynamicArray::~DynamicArray(){
      delete elements;
    }
int* DynamicArray::Begin()
    {
         return firstPos;
    }

int* DynamicArray::End()
    {
         return lastPos;
    }

int DynamicArray::Size()
    {
        return currentIndex;
    }

bool DynamicArray::pushback(int x){

        try
        {
           int size = Size();
           cout << "size = " << size << endl;
           
           if(size >= MAX_LENGTH){ // total size of the elements, in case of increasing the limit return fail
            throw Exceptions(OutOfSize);      
           }
           else if(size >= TEMP_MAX_SIZE)  // temporary size of the elements, in case of increasing the limit expand the array
           {
            throw Exceptions(ExpandArray);
            cout << "inside" << endl;
           }   
        }
        catch(Exceptions ex)
        {
            cout << "ex : " << ex <<endl;
            switch(ex)
            {
             case (Exceptions)OutOfSize:
                  cout << "pushback::OutOfSize!" << endl;
                  return false;
                  break; 
             case (Exceptions)InvalidData:
                  cout << "InvalidData!" << endl;
                  break; 
             case (Exceptions)ExpandArray:
                  cout << "ExpandArray!" << endl;
                  ExpandArrayFunc();
                  break;         
            }        
        }
        
        elements[currentIndex] = x;
        currentIndex++;
        if(currentIndex < TEMP_MAX_SIZE)
            elements[currentIndex] = INT_MIN;
        return true;

    }

int DynamicArray::pop(){
        try
        {
           if(currentIndex == 0)
           {
             throw Exceptions(NotInitialized);  
           }
        }
        catch(Exceptions ex)
        {
            switch(ex)
            {
             case (Exceptions)OutOfSize:
                  cout << "pop::OutOfSize!" << endl;
                  return INT_MIN;
                  break; 
             case (Exceptions)NotInitialized:
                  cout << "NotInitialized!" << endl;
                  return INT_MIN;
                  break;           
            }        
        }
        return elements[currentIndex-1];
    }
    
int DynamicArray::pop(int index){
    
     try
        {
           int size = Size();
           if(index >= size || index < 0)     
           {
            throw Exceptions(InvalidIndex);        
           }   
           else if(currentIndex == 0) 
           {
             throw Exceptions(NotInitialized);  
           }
        }
        catch(Exceptions ex)
        {
            switch(ex)
            {
             case (Exceptions)OutOfSize:
                  cout << "pop::OutOfSize!" << endl;
                  return INT_MIN;
                  break; 
             case (Exceptions)InvalidIndex:
                  cout << "InvalidIndex!" << endl;
                  return INT_MIN;
                  break;   
             case (Exceptions)NotInitialized:
                  cout << "NotInitialized!!" << endl;
                  return INT_MIN;
                  break;      
            }
        }
     
     return elements[index];
}

void DynamicArray::Remove(){
        try
        {
           if(currentIndex == 0)
           {
             throw Exceptions(NotInitialized);  
           }
        }
        catch(Exceptions ex)
        {
            switch(ex)
            {
             case (Exceptions)OutOfSize:
                  cout << "Remove::OutOfSize!" << endl;
                  return;
                  break; 
             case (Exceptions)NotInitialized:
                  cout << "NotInitialized!" << endl;
                  return;
                  break;           
            }        
        }
        
        currentIndex -= 1;
        elements[currentIndex] = INT_MIN;
    
}

void DynamicArray::Remove(int index){
        try
        {
           int size = Size();
           if(index >= size || index < 0)     
           {
            throw Exceptions(InvalidIndex);        
           }   
           else if(currentIndex == 0)
           {
             throw Exceptions(NotInitialized);  
           }
        }
        catch(Exceptions ex)
        {
            switch(ex)
            {
             case (Exceptions)InvalidIndex:
                  cout << "pop::InvalidIndex!" << endl;
                  return;
                  break; 
             case (Exceptions)NotInitialized:
                  cout << "NotInitialized!" << endl;
                  return;
                  break;           
            }        
        }
        int count = 0;
        for(int i=0; i < Size(); i++){
            if(i == index) continue;
            else if(i > index){
                elements[count] = elements[i];
            }
            
            ++count;
        }
        
        currentIndex -= 1;
        elements[currentIndex] = INT_MIN;
    
}

void DynamicArray::Copy(int *ptr){
    int size = Size() + 1;
    ptr = new int[size];
    
    for(int i=0;i<size; i++){
        ptr[i] = elements[i];
    }
    ptr[size-1] = INT_MIN;
}

bool DynamicArray::pushback(int x, int index){

        try
        {
           int size = Size();
           cout << "size = " << size << endl;
           
           if(size >= MAX_LENGTH){ // total size of the elements, in case of increasing the limit return fail
            throw Exceptions(OutOfSize);      
           }
           else if(size >= TEMP_MAX_SIZE)  // temporary size of the elements, in case of increasing the limit expand the array
           {
            throw Exceptions(ExpandArray);
            cout << "inside" << endl;
           }   
        }
        catch(Exceptions ex)
        {
            cout << "ex : " << ex <<endl;
            switch(ex)
            {
             case (Exceptions)OutOfSize:
                  cout << "pushback::OutOfSize!" << endl;
                  return false;
                  break; 
             case (Exceptions)InvalidData:
                  cout << "InvalidData!" << endl;
                  break; 
             case (Exceptions)ExpandArray:
                  cout << "ExpandArray!" << endl;
                  ExpandArrayFunc();
                  break;         
            }        
        }
        
        int *tempElemenets;
        //Copy(tempElemenets);
        tempElemenets = new int[Size()];
        for(int i=0; i<Size(); i++)  tempElemenets[i] = elements[i];
        delete elements;
        int count = 0;
        for(int i=0;i<Size(); i++ ){
            
            if(i == index){
                elements[count++] = x;
                elements[count] = tempElemenets[i];
                 ++count;
                continue;
            }
            else if(i > index) elements[count] = tempElemenets[i];
            else elements[count] = tempElemenets[i];
            ++count;
        }
        currentIndex++;
        if(currentIndex < TEMP_MAX_SIZE)
            elements[currentIndex] = INT_MIN; // TOD0 : ExpandArray
        return true;

    }

int DynamicArray::Length(){
    int counter = 0;
    while(elements[counter++] != INT_MIN);
    return --counter;
}

void DynamicArray::ExpandArrayFunc(){
    int *tempElements = new int[TEMP_MAX_SIZE];
    int maxSize = TEMP_MAX_SIZE;
     for(int i=0; i<TEMP_MAX_SIZE; i++){
                            tempElements[i] = elements[i];
                  }
                  delete elements;
                  TEMP_MAX_SIZE += 10; // each time expand the array by 10
                  elements = new int[TEMP_MAX_SIZE];
                  for(int i=0; i < maxSize; i++){
                       elements[i] = tempElements[i];
                  }
    
}