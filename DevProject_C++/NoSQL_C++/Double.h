#pragma once
#include <string>
#include <exception>

typedef long double LongDouble;
// forward decleration for String
class String;
class Integer;

class Double {
private:
	LongDouble* m_doubleValue;

public:
	Double() {
		m_doubleValue = nullptr;
	};
	Double(LongDouble val) {
		m_doubleValue = new LongDouble(val);
	}
	~Double() {};

	static Double valueOf(String s);
	LongDouble getDoubleValue();

	std::string getStringValue();
	bool operator>(Double i);
	bool operator>=(Double i);
	bool operator<(Double i);
	bool operator<=(Double i);
	//void operator=(Double i);
	bool operator==(Double i);
	Double operator+(Double i);
	Double operator-(Double i);
	Double operator/(Double i);
	Double operator*(Double i);
	operator Integer();// convert to Integer
};