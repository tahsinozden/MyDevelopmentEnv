#include <iostream>
#include "Timer.h"
#include <fstream>

int iGlobalFileSize = 0;
std::string sFileName = "newFile.txt";

int getFileSize(std::string);
void checkValue();

int main() {
	::iGlobalFileSize = getFileSize(::sFileName);
	std::cout << "Listening the file... " << ::sFileName << std::endl;
	Timer* tmr = new Timer(1);
	tmr->onTickEvent = &checkValue;
	tmr->start();
	return 0;
}

int getFileSize(std::string fileName){
	int iSize = -1;
	
	try{
		std::ifstream file(fileName,std::ios::ate/*, std::ios::binary*/);
		iSize = file.tellg();
		if (!file){
			std::ofstream oFile(fileName,std::ofstream::out);
			oFile.close();
		}
		file.close();
	}
	catch(...){
		std::cout << "File Error!.." << std::endl;
	}
	
	return iSize;
}

void checkValue(){
	int iTmpSize = getFileSize(::sFileName);
	if (iTmpSize < 0){
		std::cout << "Cannot acccess file!" << std::endl;
	}
	else if (iTmpSize != ::iGlobalFileSize ){
		std::cout << "File has been changed. New value is " << iTmpSize << " bytes." << std::endl;
		::iGlobalFileSize = iTmpSize;
	}
}