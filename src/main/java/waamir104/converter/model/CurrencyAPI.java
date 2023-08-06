package waamir104.converter.model;

import java.net.URL;

public class CurrencyAPI {
	private String clientId = "";
	
	public CurrencyAPI() {}
	
	public URL getURLCurrencyRates() {
		String urlStr = String.format("https://openexchangerates.org/api/latest.json?app_id=%s&base=USD", this.clientId);
		
		URL url = null;
		
		try {
			url = new URL(urlStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return url;
	}
	
	public URL getURLCurrencyNames() {
		URL url = null;
		
		try {
			url = new URL("https://openexchangerates.org/api/currencies.json");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return url;
	}
}
