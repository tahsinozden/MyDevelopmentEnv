package ozden.apps.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ozden.apps.currency.Currency;
import ozden.apps.currency.CurrencyConversion;
import ozden.apps.currency.api.CurrencyHelper;

@RestController(value = "/currency_service")
public class CurrencyService {
	// currency rates from bitpay
	final String CURRENCY_SERVICE_MODE_BITPAY = "BITPAY";
	// currency rates from NBP, National Bnak of Poland
	final String CURRENCY_SERVICE_MODE_NBP = "NBP";
	
	@RequestMapping(value="currency_service/currencies")
	public List<Currency> getCurrencies(@RequestParam String currencyServiceMode) throws Exception{
		CurrencyHelper helper = new CurrencyHelper();
		if(currencyServiceMode.equals(CURRENCY_SERVICE_MODE_BITPAY))
			return helper.getAllRates();
		else if(currencyServiceMode.equals(CURRENCY_SERVICE_MODE_NBP))
			return helper.getAllRatesFromNBP();
		else
			throw new Exception("Unsupported currency service mode " + currencyServiceMode);
		
	}
	
	@RequestMapping(value="/currency_service/currency")
	public @ResponseBody CurrencyConversion getCurrenyConversion(@RequestParam String src, 
											   @RequestParam String dst,
											   @RequestParam Double srcAmt,
											   @RequestParam String currencyServiceMode) throws Exception{
		CurrencyHelper helper = new CurrencyHelper();
		
		if(currencyServiceMode.equals(CURRENCY_SERVICE_MODE_BITPAY))
			return helper.getCurrencyConversion(src, dst, srcAmt.doubleValue());
		else if(currencyServiceMode.equals(CURRENCY_SERVICE_MODE_NBP))
			return helper.getCurrencyConversionFromNBP(src, dst, srcAmt.doubleValue());
		else
			throw new Exception("Unsupported currency service mode " + currencyServiceMode);
	}
}
