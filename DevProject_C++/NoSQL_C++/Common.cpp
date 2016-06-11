/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
#include "Common.h"
#include <exception>
#include <fstream>
#include <iostream>
class ExceptionFileNotFound : public std::exception
{
  const char * what () const throw ()
  {
    return "File Not Found!";
  }
};


bool copyFile(std::string srcFile, std::string dstFile){
	char ch;

	std::ifstream f1(srcFile);
	std::ofstream f2(dstFile);

	if (!f1) {
		std::cerr << "Can't open IN file\n";
		return false;
	}

	if (!f2) {
		std::cerr << "Can't open OUT file\n";
		return false;
	}

	// get each character from source and put it to dst file.
	while (f1 && f1.get(ch))
		f2.put(ch);

	return true;
}
 
