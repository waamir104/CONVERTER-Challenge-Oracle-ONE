package waamir104.converter.controller;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import waamir104.converter.dto.CurrencyNameOptionDTO;

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
	
}
