package ozden.apps.currency.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ozden.apps.currency.Currency;
import ozden.apps.currency.CurrencyConversion;

public class CurrencyHelper {
	// URL to get the all up-to-date currency rates
	final String CURRENCY_API_URL = "https://bitpay.com/api/rates";
	private RestTemplate restTemplate;
	
	public CurrencyHelper(){
		restTemplate = new RestTemplate();
	}
	
	public List<Currency> getAllRates() throws RestClientException{
		// get all currencies with their rates 
		Currency[] currencies = restTemplate.getForObject(this.CURRENCY_API_URL, Currency[].class);
		List<Currency> all = new ArrayList<Currency>(Arrays.asList(currencies));
		return all;

	}
	
	public CurrencyConversion getCurrencyConversion(String srcCurrency, String dstCurrency) throws Exception{
		// throe exception if currencies are empty
		if (srcCurrency.equals("") || dstCurrency.equals("")){
			throw new Exception("Empty Currency!");
		}
		double srcRate = 0.0;
		double dstRate = 0.0;
		List<Currency> allRates = this.getAllRates();
		for (Currency cur : allRates){
			// map currencies as source and destination
			if(cur.getCode().equals(srcCurrency)) srcRate = cur.getRate().doubleValue();
			else if(cur.getCode().equals(dstCurrency)) dstRate = cur.getRate().doubleValue();
		}
		// throw exception if any of the currencies are not found
		if(srcRate == 0.0 || dstRate == 0.0){
			throw new Exception("Currency Not Found!");
		}
		
		CurrencyConversion conv = new CurrencyConversion(srcCurrency, dstCurrency, srcRate, dstRate);
		// perform currency calculation
		conv.getRate();
		return conv;
	}
	
}
