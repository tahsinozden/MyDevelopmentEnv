package ozden.apps.currency.api;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ozden.apps.currency.Currency;

/**
 * @author tahsin
 *
 */
public class CurrencyXMLParser {
	private static final Logger log = LoggerFactory.getLogger(CurrencyXMLParser.class);
	private final String NATIONAL_BANK_OF_POLAND_CURRENCY_RATES_URL = "http://www.nbp.pl/kursy/xml/a087z160506.xml";
	
	/*
	 * It returns currency rates over PLN (Polish Zloty) currency
	 * These values are obtained from National Bank of Poland
	 * @return : Returns Currency List
	 */
	public List<Currency> getRatesFromRemoteFile() {
		ArrayList<Currency> allCurs = new ArrayList<Currency>();
		ArrayList<String> allData = new ArrayList<String>();
		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				boolean bCurrencyName = false;
				boolean bCurrencyAmt = false;
				boolean bCurrencyCode = false;
				boolean bCurrencyRate = false;

				public void startElement(String uri, String localName, String qName, Attributes attributes)
						throws SAXException {

					 log.debug("Start Element :" + qName);
					 
					// fetch currency name
					if (qName.equalsIgnoreCase("nazwa_waluty")) {
						bCurrencyName = true;
					}

					// fetch currency amount
					if (qName.equalsIgnoreCase("przelicznik")) {
						bCurrencyAmt = true;
					}

					// fetch currency code
					if (qName.equalsIgnoreCase("kod_waluty")) {
						bCurrencyCode = true;
					}

					// fetch currency rate over PLN
					if (qName.equalsIgnoreCase("kurs_sredni")) {
						bCurrencyRate = true;
					}

				}

				public void endElement(String uri, String localName, String qName) throws SAXException {

					log.debug("End Element :" + qName);

				}

				public void characters(char ch[], int start, int length) throws SAXException {

					log.debug(new String(ch, start, length));

					if (bCurrencyName) {
						log.debug("Currency Name : " + new String(ch, start, length));
						allData.add(new String(ch, start, length));
						bCurrencyName = false;
					}

					if (bCurrencyAmt) {
						log.debug("Currency Amount : " + new String(ch, start, length));
						allData.add(new String(ch, start, length));
						bCurrencyAmt = false;
					}

					if (bCurrencyCode) {
						log.debug("Currency Code : " + new String(ch, start, length));
						allData.add(new String(ch, start, length));
						bCurrencyCode = false;
					}

					if (bCurrencyRate) {
						log.debug("Currency Rate over PLN : " + new String(ch, start, length));
						String rate = new String(ch, start, length);
						rate = rate.replace(',', '.');
						allData.add(rate);
						bCurrencyRate = false;
					}

				}

			};

			// open remote file
			URL url = new URL(NATIONAL_BANK_OF_POLAND_CURRENCY_RATES_URL);
			InputStream inputStream = url.openStream();
			
//			File file = new File("currencies.xml");
//			InputStream inputStream = new FileInputStream(file);
			Reader reader = new InputStreamReader(inputStream, "UTF-8");
			
			// read file as utf-8 format to cover all special characters
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
			
			// parse currency data
			saxParser.parse(is, handler);

		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 1; i < allData.size(); i++) {
			// for each currency record
			// 4 items make 1 currency record
			if (i % 4 == 0) {
				// add currency records to the list
				allCurs.add(
						new Currency(allData.get(i - 2), allData.get(i - 4), Double.parseDouble(allData.get(i - 1))));
			}
		}
		
		log.info("Currency records from remote xml " + allCurs.toString());
		return allCurs;
	}
}
