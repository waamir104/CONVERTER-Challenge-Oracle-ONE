package waamir104.converter.model;

import java.math.BigDecimal;

public class Currency {
	private int id;
	private String shortName;
	private String longName;
	private BigDecimal oneUSDEquals;
	
	public Currency() {}

	public Currency(int id, String shortName, String longName, BigDecimal oneUSDEquals) {
		this.id = id;
		this.shortName = shortName;
		this.longName = longName;
		this.oneUSDEquals = oneUSDEquals;
	}

	public int getId() {
		return id;
	}

	public String getShortName() {
		return shortName;
	}

	public String getLongName() {
		return longName;
	}

	public BigDecimal getOneUSDEquals() {
		return oneUSDEquals;
	}
	
	@Override
	public String toString() {
		return this.longName;
	}
}
