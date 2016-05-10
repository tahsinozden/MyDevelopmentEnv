package ozden.apps.currency.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import ozden.apps.Application;
import ozden.apps.currency.Currency;
import ozden.apps.currency.CurrencyConversion;
import ozden.apps.entities.Currencies;
import ozden.apps.repos.CurrenciesRepository;

@Component
public class CurrencyHelper {
	private static final Logger log = LoggerFactory.getLogger(CurrencyHelper.class);
	// URL to get the all up-to-date currency rates
	final String CURRENCY_API_URL = "https://bitpay.com/api/rates";
	private RestTemplate restTemplate;
	
	@Autowired
	private CurrenciesRepository currenciesRepository;
	
	/*
	 * Return index number of the currency, returns -1 if there is no match
	 */
	private int getCurrencyIndexFromList(List<Currency> lst, String curName){
		for (int i = 0; i < lst.size(); i++) {
			if (lst.get(i).getCode().equals(curName)){
				return i;
			}
		}
//		for(Currency cur : lst){
//			log.debug("Checking " + cur.getCode());
//			if (cur.getCode().equals(curName)){
//				log.debug(cur.getCode() + " MATCHED!");
//				return true;
//			}
//		}
		log.debug("NOT MATCHED!");
		return -1;
	}
	public CurrencyHelper(){
		restTemplate = new RestTemplate();
	}
	
	public List<Currency> getAllRates() throws RestClientException{
		// get all currencies with their rates 
		Currency[] currencies = restTemplate.getForObject(this.CURRENCY_API_URL, Currency[].class);
		List<Currency> all = new ArrayList<Currency>(Arrays.asList(currencies));
		return all;

	}
	
	public List<Currency> getAllRatesFromNBP() throws RestClientException{
		// get all currencies with their rates from NBP
		CurrencyXMLParser parser = new CurrencyXMLParser();
		List<Currency> allRates = parser.getRatesFromRemoteFile();
		Currency PolishCurrency = new Currency("PLN", "Polish Zloty", 1.0);
		// since all rates are calculated in PLN, PLN does not exist in the list
		// then add it to the list
		allRates.add(PolishCurrency);
		return allRates;

	}
	
	public CurrencyConversion getCurrencyConversion(String srcCurrency, String dstCurrency, double srcAmt) throws Exception{
		// throw exception if currencies are empty
		if (srcCurrency.equals("") || dstCurrency.equals("")){
			throw new Exception("Empty Currency!");
		}
		if(srcAmt <= 0){
			throw new Exception("Invalid value of source amount!");
		}
		double srcRate = 0.0;
		double dstRate = 0.0;
		List<Currency> allRates = this.getAllRates();
		if( srcCurrency.equals(dstCurrency)){
			throw new Exception("Same currency type is selected!");
		}
		for (Currency cur : allRates){
			// map currencies as source and destination
			if(cur.getCode().equals(srcCurrency)) srcRate = cur.getRate().doubleValue();
			else if(cur.getCode().equals(dstCurrency)) dstRate = cur.getRate().doubleValue();
		}
		// throw exception if any of the currencies are not found
		if(srcRate == 0.0 || dstRate == 0.0){
			throw new Exception("Currency Not Found!");
		}
		
		CurrencyConversion conv = new CurrencyConversion(srcCurrency, dstCurrency, srcRate, dstRate, srcAmt);
		// perform currency calculation
//		double rate = conv.getRate();
//		conv.getConvResult();
		conv.doCurrencyConversion();
		return conv;
	}
	
	// TODO: implement a service which enable this calculation for front end
	// TODO: implement a scheduled task reqularly save data to database for hostory
	public CurrencyConversion getCurrencyConversionFromNBP(String srcCurrency, String dstCurrency, double srcAmt) throws Exception{
		
		// throw exception if currencies are empty
		if (srcCurrency.equals("") || dstCurrency.equals("")){
			throw new Exception("Empty Currency!");
		}
		if(srcAmt <= 0){
			throw new Exception("Invalid value of source amount!");
		}
		double srcRate = 0.0;
		double dstRate = 0.0;
		CurrencyXMLParser parser = new CurrencyXMLParser();
		List<Currency> allRates = parser.getRatesFromRemoteFile();
		
		if( srcCurrency.equals(dstCurrency)){
			throw new Exception("Same currency type is selected!");
		}
		CurrencyConversion conv = null;
		Currency srcCurInPLN = null;
		Currency dstCurInPLN = null;
		
		
		// if destination currency is in PLN,
		// return src currency rate, because all rates calculated over PLN
		if (srcCurrency.equals("PLN")){
			if(getCurrencyIndexFromList(allRates, dstCurrency) < 0){
				throw new Exception("Dest. currency is not supposted! " + dstCurrency );
			}
			srcCurInPLN = allRates.get(getCurrencyIndexFromList(allRates, dstCurrency));
			// since it is in PLN, destination currency rate is 1
			conv = new CurrencyConversion(srcCurrency, dstCurrency, srcCurInPLN.getRate(), 1 , srcAmt);
			conv.doCurrencyConversion();
			return conv;
		}
		
		// if destination currency is in PLN,
		// return src currency rate, because all rates calculated over PLN
		if (dstCurrency.equals("PLN")){
			if(getCurrencyIndexFromList(allRates, srcCurrency) < 0){
				throw new Exception("Source currency is not supposted! " + srcCurrency );
			}
			srcCurInPLN = allRates.get(getCurrencyIndexFromList(allRates, srcCurrency));
			// since it is in PLN, destination currency rate is 1
			conv = new CurrencyConversion(srcCurrency, dstCurrency, 1, srcCurInPLN.getRate(), srcAmt);
			conv.doCurrencyConversion();
			return conv;
		}

		if(getCurrencyIndexFromList(allRates, srcCurrency) < 0){
			throw new Exception("Source currency is not supposted! " + srcCurrency );
		}
		
		if(getCurrencyIndexFromList(allRates, dstCurrency) < 0){
			throw new Exception("Dest. currency is not supposted! " + dstCurrency );
		}
		
		// get currencies in Polish Zloty ratio
		srcCurInPLN = allRates.get(getCurrencyIndexFromList(allRates, srcCurrency));
		dstCurInPLN = allRates.get(getCurrencyIndexFromList(allRates, dstCurrency));
		
		conv = new CurrencyConversion(srcCurrency, dstCurrency, dstCurInPLN.getRate(), srcCurInPLN.getRate(), srcAmt);
		conv.doCurrencyConversion();
		return conv;
	}
	
	public void saveCurrencies2Table(){
		// check database for the records
		List<Currencies> allCurrenciesInTable = null;
		try {
			allCurrenciesInTable = currenciesRepository.findAll();
			System.out.println("Records in the table " + allCurrenciesInTable.toString());
		} catch (Exception e) {
			System.out.println("CAUGHT IT!!!");
//			return;
		}
		 
		List<Currency> currenciesFromService = null;
		List<Currencies> currenciesInTable = null;
		Date nowTime = Calendar.getInstance().getTime();
		// 1 hour in milliseconds
		final long TIME_INTERVAL = 1 * 60 * 60 * 1000;
		// check if we have records or not
		if (allCurrenciesInTable == null || allCurrenciesInTable.isEmpty()){
			System.out.println("Currencies table is empty, will be initialized!");
			currenciesFromService = this.getAllRates();
			if (currenciesFromService != null ){
				System.out.println("Currencies are obtained from the remote service.");
				currenciesInTable = new ArrayList<Currencies>();
//				System.out.println("Currencies from remote service " + currenciesFromService.toString());
				for (Currency cur : currenciesFromService){
					currenciesInTable.add(new Currencies(cur.getCode(), cur.getName(), cur.getRate(), nowTime));
				}
			}
			// save it to the table
			if (!currenciesInTable.isEmpty()){
				System.out.println("Now currencies from servce will be added to the table.");
//				System.out.println("Currencies for the Currencies table " + currenciesInTable.toString());
//				currenciesRepository.save(currenciesInTable);
				for(Currencies cur : currenciesInTable){
//					System.out.println(cur.toString());
					try {
						if(cur != null)
							currenciesRepository.save(cur);
					} catch (Exception e) {
						System.err.println("ERROR message -> " + e.getMessage() + " - " + cur.toString());
//						e.printStackTrace();
					}

				}
			}
		}
		else{
//			List<Currencies> allCurrenciesInTable = currenciesRepository.findAll();
			if (allCurrenciesInTable != null && !allCurrenciesInTable.isEmpty()){
				// check the last update time
				Currencies sampleRecord = allCurrenciesInTable.get(0);	
				if((nowTime.getTime() - sampleRecord.getLastUpdateTime().getTime()) >= TIME_INTERVAL){
					System.out.println("The table needs to be updated");
					currenciesFromService = this.getAllRates();
					if (currenciesFromService != null ){
						System.out.println("Currencies are obtained from the remote service.");
						currenciesInTable = new ArrayList<Currencies>();
						for (Currency cur : currenciesFromService){
							currenciesInTable.add(new Currencies(cur.getCode(), cur.getName(), cur.getRate(), nowTime));
						}
					}
					// save it to the table
					if (!currenciesInTable.isEmpty()){
						System.out.println("Now currencies from servce will be added to the table.");
						currenciesRepository.save(currenciesInTable);
					}
				}
			}
			else{
				System.out.println("Table is empty!");
			}
		}
		
	}
	
	public CurrencyConversion getOfflineCurrencyConversion(String srcCurrency, String dstCurrency, double srcAmt) throws Exception{
		// throw exception if currencies are empty
		if (srcCurrency.equals("") || dstCurrency.equals("")){
			throw new Exception("Empty Currency!");
		}
		if(srcAmt <= 0){
			throw new Exception("Invalid value of source amount!");
		}
		double srcRate = 0.0;
		double dstRate = 0.0;
		List<Currency> allRates = this.getAllRates();
		Currencies srcCur = null;
		Currencies dstCur = null;

		if( srcCurrency.equals(dstCurrency)){
			throw new Exception("Same currency type is selected!");
		}
		
		List<Currencies> tmp = currenciesRepository.findByCode(srcCurrency);
		if(tmp == null){
			System.err.println("Source currency not found! exit conversion!");
			return null;
		}
		srcCur = tmp.get(0);
		tmp = currenciesRepository.findByCode(dstCurrency);
		if(tmp == null){
			System.err.println("Destination currency not found! exit conversion!");
			return null;
		}
		dstCur = tmp.get(0);
		
		srcRate = srcCur.getRate().doubleValue();
		dstRate = dstCur.getRate().doubleValue();

		// throw exception if any of the currencies are not found
		if(srcRate == 0.0 || dstRate == 0.0){
			throw new Exception("Currency Not Found!");
		}
		
		CurrencyConversion conv = new CurrencyConversion(srcCurrency, dstCurrency, srcRate, dstRate, srcAmt);
		// perform currency calculation
//		double rate = conv.getRate();
//		conv.getConvResult();
		conv.doCurrencyConversion();
		return conv;
	}
	
}
