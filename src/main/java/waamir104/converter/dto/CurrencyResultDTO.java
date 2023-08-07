package waamir104.converter.dto;

import java.math.BigDecimal;

public class CurrencyResultDTO {
	private String longName;
	private BigDecimal amount;
	
	public CurrencyResultDTO() {
		
	}
	
	public CurrencyResultDTO(String longName, BigDecimal amount) {
		this.longName = longName;
		this.amount = amount;
	}

	public String getLongName() {
		return longName;
	}

	public BigDecimal getAmount() {
		return amount;
	}
	
	@Override
	public String toString() {
		return this.longName;
	}
}
