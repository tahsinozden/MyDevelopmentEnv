/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
#include "Common.h"
#include <exception>

class ExceptionFileNotFound : public std::exception
{
  const char * what () const throw ()
  {
    return "File Not Found!";
  }
};
 
