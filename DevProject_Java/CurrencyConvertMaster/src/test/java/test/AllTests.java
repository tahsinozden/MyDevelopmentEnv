package test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ozden.apps.currency.api.CurrencyHelper;
import ozden.apps.currency.api.CurrencyXMLParser;
import ozden.apps.currency.Currency;

@RunWith(Suite.class)
@SuiteClasses({})
public class AllTests {
	public static void main(String[] args){
		System.out.println("First test!");
		testCurrencyConv();
	}
	
	public static void testRemoteFileParsing(){ 
		CurrencyXMLParser parser = new CurrencyXMLParser();
		List<Currency> dat = parser.getRatesFromRemoteFile();
		System.out.println(dat);
	}
	
	public static void testCurrencyConv(){
		CurrencyHelper helper = new CurrencyHelper();
		try {
			System.out.println(helper.getCurrencyConversionFromNBP("TRY", "PLN", 1));
			System.out.println(helper.getCurrencyConversionFromNBP("PLN", "TRY", 1));
			System.out.println(helper.getCurrencyConversionFromNBP("USD", "TRY", 1));
			System.out.println(helper.getCurrencyConversionFromNBP("EUR", "TRY", 1));
			System.out.println(helper.getCurrencyConversionFromNBP("EUR", "PLN", 1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
