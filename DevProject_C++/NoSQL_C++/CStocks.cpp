#include "CStocks.h"

std::string CStocks::getDBObjectName() {
	return "Stocks";
}

PairList CStocks::getDBObjectFields() {
	PairList list;
	list.push_back(std::pair<std::string, std::string>("stock_name", "TEXT"));
	list.push_back(std::pair<std::string, std::string>("amount", "INTEGER"));
	list.push_back(std::pair<std::string, std::string>("percentage", "DOUBLE"));
	return list;
}

StringMap CStocks::getMappedObject() {
	StringMap mapping;

	mapping.insert(std::pair<std::string, std::string>("stock_name", m_stockName.getStringValue()));
	mapping.insert(std::pair<std::string, std::string>("amount", m_amount.getStringValue()));
	mapping.insert(std::pair<std::string, std::string>("percentage", m_percentage.getStringValue()));

	return mapping;
}

IDBEntity* CStocks::getEntityObjFromMapping(StringMap objMap) {
	return new CStocks(objMap["stock_name"], Integer::valueOf(objMap["amount"]), Double::valueOf(objMap["percentage"]));
}

IDBEntity* CStocks::clone() const {
	return new CStocks(dynamic_cast<CStocks const&>(*this));
}

std::string CStocks::toString() {
	return m_stockName.getStringValue() + " " + m_amount.getStringValue() + " " + m_percentage.getStringValue();
}
