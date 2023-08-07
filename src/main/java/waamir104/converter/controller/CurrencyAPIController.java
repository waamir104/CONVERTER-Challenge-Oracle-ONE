package waamir104.converter.controller;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import waamir104.converter.dto.CurrencyNameOptionDTO;
import waamir104.converter.model.Currency;

public class CurrencyAPIController {
	private HttpURLConnection conn;
	
	public CurrencyAPIController(HttpURLConnection conn) {
		this.conn = conn;
	}
	
	public ArrayList<CurrencyNameOptionDTO> listCurrencies() {
		ArrayList<CurrencyNameOptionDTO> currencyList = new ArrayList<>();
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(this.conn.getInputStream()));
			String response = this.readResponse(br);
			JSONObject jsonResponse = new JSONObject(response);
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(response);
			ArrayList<String> keys = new ArrayList<>();
			jsonNode.fieldNames().forEachRemaining(keys::add);
			int counter = 1;
			
			for (String key : keys) {
				currencyList.add(new CurrencyNameOptionDTO(counter, key, jsonResponse.getString(key)));
				counter++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return currencyList;
	}
	
	private String readResponse(BufferedReader br) {
		StringBuilder responseSB = new StringBuilder();
		String line;
		
		try {
			while ((line = br.readLine()) != null) {
				responseSB.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return responseSB.toString();
	}
	
	public ArrayList<Currency> getExchangeRates(CurrencyNameOptionDTO currencyFrom, CurrencyNameOptionDTO currencyTo) {
		ArrayList<Currency> exchangeRates = new ArrayList<>();
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String response = this.readResponse(br);
			JSONObject responseJSON = new JSONObject(response);
			JSONObject rates = responseJSON.getJSONObject("rates");
			BigDecimal rateFrom = rates.getBigDecimal(currencyFrom.getShortName());
			BigDecimal rateTo = rates.getBigDecimal(currencyTo.getShortName());
			
			exchangeRates.add(new Currency(currencyFrom.getId(), currencyFrom.getShortName(), currencyFrom.getLongName(), rateFrom));
			exchangeRates.add(new Currency(currencyTo.getId(), currencyTo.getShortName(), currencyTo.getLongName(), rateTo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return exchangeRates;
	}
	
}
