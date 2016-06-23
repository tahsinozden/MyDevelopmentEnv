#pragma once
#include "IDBEntity.h"
#include "String.h"
#include "Integer.h"
#include "Double.h"

class CStocks : public IDBEntity {
private:
	// customized types are used instead of string, int and double
	// because null integer conversion to null string "" needs to be handled for object query
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
	void setStockName(String s) {
		m_stockName = s;
	}
	Integer getAmount() {
		return m_amount;
	}
	void setAmount(Integer i) {
		m_amount = i;
	}
	Double getPercentage() {
		return m_percentage;
	}
	void setPercentage(Double d) {
		m_percentage = d;
	}
};
