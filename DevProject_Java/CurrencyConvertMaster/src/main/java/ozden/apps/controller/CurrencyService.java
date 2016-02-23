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
	@RequestMapping(value="currency_service/currencies")
	public List<Currency> getCurrencies(){
		CurrencyHelper helper = new CurrencyHelper();
		return helper.getAllRates();
	}
	
	@RequestMapping(value="/currency_service/currency")
	public @ResponseBody CurrencyConversion getCurrenyConversion(@RequestParam String src, 
											   @RequestParam String dst,
											   @RequestParam Double srcAmt) throws Exception{
		CurrencyHelper helper = new CurrencyHelper();
		return helper.getCurrencyConversion(src, dst, srcAmt.doubleValue());
		
	}
}
