#include "String.h"
#include "Integer.h"

String::String() {
	// assign null string value
	m_strValue = "";
}

String::String(std::string s) {
	m_strValue = s;
}

String::~String() {
}

std::string String::getStringValue() {
	return m_strValue;
}

bool String::equals(String s) {
	return this->getStringValue() == s.getStringValue();
}

bool String::operator>(String s) {
	return this->getStringValue() > s.getStringValue();
}

bool String::operator>=(String s) {
	return this->getStringValue() >= s.getStringValue();
}

bool String::operator<(String s) {
	return this->getStringValue() < s.getStringValue();
}

bool String::operator<=(String s) {
	return this->getStringValue() <= s.getStringValue();
}

void String::operator=(String s) {
	m_strValue = s.getStringValue();
}


bool String::operator==(String s) {
	return this->getStringValue() == s.getStringValue();
}

String String::operator+(String s) {
	return this->getStringValue() + s.getStringValue();
}

void String::operator+=(String s) {
	m_strValue += s.getStringValue();
}
