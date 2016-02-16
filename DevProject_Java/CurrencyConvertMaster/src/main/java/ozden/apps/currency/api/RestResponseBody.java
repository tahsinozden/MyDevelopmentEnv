package ozden.apps.currency.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ozden.apps.currency.Currency;

// this is a rest template class used for rest response
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestResponseBody {
	private String type;
	private Currency[] currencies;
	
	public RestResponseBody(){
		
	}
	
	public RestResponseBody(String type, Currency[] currencies) {
		super();
		this.type = type;
		this.currencies = currencies;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Currency[] getCurrencies() {
		return currencies;
	}
	
	public void setCurrencies(Currency[] currencies) {
		this.currencies = currencies;
	}

	@Override
	public String toString() {
		return "RestResponseBody [type=" + type + ", currencies=" + currencies + "]";
	}
	
	
}
