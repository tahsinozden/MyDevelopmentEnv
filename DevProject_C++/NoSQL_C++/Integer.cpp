#include "Integer.h"
#include "String.h"
#include "Double.h"
#include <exception>
#include <stdexcept>

LongInt Integer::getIntValue() {
	// TODO: Find sth for null handling for integer
	// if Integer doesn't have a value and if we retrun it as 0 then, our case will not be satisfied for null "" string

	//// if it has a value
	//if (m_intValue) {
	//	return *m_intValue;
	//}
	//else {
	//	return 0;
	//}
	// if it has a value
	if (m_intValue) {
		return *m_intValue;
	}
	else {
		throw std::runtime_error("Not initialized!");
	}
}

std::string Integer::getStringValue() {
	// if it has a value
	if (m_intValue) {
		return std::to_string(*m_intValue);
	}
	else {
		// return NULL string
		return "";
	}
}

bool Integer::operator>(Integer i) {
	return this->getIntValue() > i.getIntValue();
}

bool Integer::operator>=(Integer i) {
	return this->getIntValue() >= i.getIntValue();
}

bool Integer::operator<(Integer i) {
	return this->getIntValue() < i.getIntValue();
}

bool Integer::operator<=(Integer i) {
	return this->getIntValue() <= i.getIntValue();
}

//void Integer::operator=(Integer i) {
//	if (m_intValue) {
//		*m_intValue = i.getIntValue();
//	}
//	else {
//		m_intValue = new LongInt(i.getIntValue());
//	}
//
//	// it doesn't work beacuse it doesn't set the new value
//	//if (m_intValue) {
//	//	*m_intValue = i.getIntValue();
//	//}
//}

bool Integer::operator==(Integer i) {
	return this->getIntValue() == i.getIntValue();
}

Integer Integer::operator+(Integer i) {
	return Integer(this->getIntValue() + i.getIntValue());
}

Integer Integer::operator-(Integer i) {
	return Integer(this->getIntValue() - i.getIntValue());
}

Integer Integer::operator/(Integer i) {
	if (i.getIntValue() != 0) {
		return Integer(this->getIntValue() / i.getIntValue());
	}
	else {
		throw std::runtime_error("Divided By Zero!");
	}
}

Integer Integer::operator*(Integer i) {
	return Integer(this->getIntValue() * i.getIntValue());
}


Integer Integer::valueOf(String s) {
	LongInt val;
	try {
		// if it is not a number, an exception will be thrown
		// actually atoll returns 0, it it is not a number
		val = std::atoll(s.getStringValue().c_str());
	}
	catch (const std::exception& e) {
		throw std::runtime_error(("Not a Number! " + std::string(e.what())).c_str());
	}
	return Integer(val);
}

Integer Integer::valueOf(Double d) {
	return Integer((LongInt)d.getDoubleValue());
}
