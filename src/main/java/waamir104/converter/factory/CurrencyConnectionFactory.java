package waamir104.converter.factory;

import java.net.HttpURLConnection;
import java.net.URL;

import waamir104.converter.model.CurrencyAPI;

public class CurrencyConnectionFactory {
	private HttpURLConnection conn;
	private URL url;
	
	public CurrencyConnectionFactory() {}
	
	public CurrencyConnectionFactory(CurrencyAPI api, CurrencyConnectionFactoryValues value) {
		switch (value) {
			case RATES:
				this.url = api.getURLCurrencyRates();
				this.configureConnection();
				break;
			case NAMES:
				this.url = api.getURLCurrencyNames();
				this.configureConnection();
		}
	}
	
	private void configureConnection() {
		try {
			this.conn = (HttpURLConnection) this.url.openConnection();
			this.conn.setRequestMethod("GET");
			this.conn.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public HttpURLConnection getConnection() {
		return this.conn;
	}
}
