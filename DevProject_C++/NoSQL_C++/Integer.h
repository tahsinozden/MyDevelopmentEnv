#pragma once
#include <string>
#include <exception>

typedef long long int LongInt;
// forward decleration for String
class String;
// forward decleration for String
class Double;

class Integer {
private:
	LongInt* m_intValue;

public:
	Integer() {
		m_intValue = nullptr;
	};
	Integer(LongInt val) {
		m_intValue = new LongInt(val);
	}
	~Integer() {};

	static Integer valueOf(String s);
	static Integer valueOf(Double d);
	LongInt getIntValue();
	std::string getStringValue();
	bool operator>(Integer i);
	bool operator>=(Integer i);
	bool operator<(Integer i);
	bool operator<=(Integer i);
	//void operator=(Integer i);
	bool operator==(Integer i);
	Integer operator+(Integer i);
	Integer operator-(Integer i);
	Integer operator/(Integer i);
	Integer operator*(Integer i);
};