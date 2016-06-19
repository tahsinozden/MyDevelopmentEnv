#pragma once
#include <string>

// forward decleration for Integer
class Integer;

class String {
public:
	String();
	String(std::string);
	~String();
	std::string getStringValue();
	bool equals(String s);
	bool operator>(String s);
	bool operator>=(String s);
	bool operator<(String s);
	bool operator<=(String s);
	void operator=(String s);
	bool operator==(String s);
	String operator+(String s);
	void operator+=(String s);

private:
	std::string m_strValue;
};


