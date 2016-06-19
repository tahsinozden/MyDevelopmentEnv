#pragma once
#include "IDBEntity.h"
#include "String.h"
#include "Integer.h"
#include "Double.h"

class CStocks : public IDBEntity {
private:
	String m_stockName;
	Integer m_amount;
	Double m_percentage;

public:
	CStocks() {}
	CStocks(String name, Integer amt, Double percent) {
		m_stockName = name;
		m_amount = amt;
		m_percentage = percent;
	}

	virtual std::string getDBObjectName() override;

	virtual PairList getDBObjectFields() override;

	StringMap getMappedObject() override;

	IDBEntity* getEntityObjFromMapping(StringMap) override;

	IDBEntity * clone() const;

	std::string toString();

	// getters and setters
	String getStockName() {
		return m_stockName;
	}

};
