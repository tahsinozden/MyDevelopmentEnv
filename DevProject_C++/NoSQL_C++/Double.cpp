#include "Double.h"
#include "Integer.h"
#include "String.h"
#include <exception>
#include <stdexcept>

LongDouble Double::getDoubleValue() {
	// TODO: Find sth for null handling for double

	//// if it has a value
	//if (m_doubleValue) {
	//	return *m_doubleValue;
	//}
	//else {
	//	return 0.0;
	//}

	// if it has a value
	if (m_doubleValue) {
		return *m_doubleValue;
	}
	else {
		throw std::runtime_error("Not initialized!");
	}
}

std::string Double::getStringValue() {
	// if it has a value
	if (m_doubleValue) {
		return std::to_string(*m_doubleValue);
	}
	else {
		// return NULL string
		return "";
	}
}

bool Double::operator>(Double d) {
	return this->getDoubleValue() > d.getDoubleValue();
}

bool Double::operator>=(Double d) {
	return this->getDoubleValue() >= d.getDoubleValue();
}

bool Double::operator<(Double d) {
	return this->getDoubleValue() < d.getDoubleValue();
}

bool Double::operator<=(Double d) {
	return this->getDoubleValue() <= d.getDoubleValue();
}

//void Double::operator=(Double d) {
//	if (m_doubleValue) {
//		*m_doubleValue = d.getDoubleValue();
//	}
//	else {
//		m_doubleValue = new LongDouble(d.getDoubleValue());
//	}
//	//if (m_doubleValue) {
//	//	*m_doubleValue = d.getDoubleValue();
//	//}
//}

bool Double::operator==(Double d) {
	return this->getDoubleValue() == d.getDoubleValue();
}

Double Double::operator+(Double d) {
	return Double(this->getDoubleValue() + d.getDoubleValue());
}

Double Double::operator-(Double d) {
	return Double(this->getDoubleValue() - d.getDoubleValue());
}

Double Double::operator/(Double d) {
	if (d.getDoubleValue() != 0) {
		return Double(this->getDoubleValue() / d.getDoubleValue());
	}
	else {
		throw std::runtime_error("Divided By Zero!");
	}
}

Double Double::operator*(Double d) {
	return Double(this->getDoubleValue() * d.getDoubleValue());
}

// convert to Integer
// Double d(3.87);
// Integer i;
// i = d;
Double::operator Integer() {
	return Integer((LongInt)this->getDoubleValue());
}

Double Double::valueOf(String s) {
	LongDouble val;
	try {
		// if it is not a number, an exception will be thrown
		// actually atoll returns 0, it it is not a number
		val = std::atof(s.getStringValue().c_str());
	}
	catch (const std::exception& e) {
		throw std::runtime_error(("Not a Number! " + std::string(e.what())).c_str());
	}
	return Double(val);
}