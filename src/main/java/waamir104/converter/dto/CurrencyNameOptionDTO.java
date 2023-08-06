package waamir104.converter.dto;

public class CurrencyNameOptionDTO {
	private int id;
	private String shortName;
	private String longName;
	
	public CurrencyNameOptionDTO() {}
	
	public CurrencyNameOptionDTO(int id, String shortName, String longName) {
		this.id = id;
		this.shortName = shortName;
		this.longName = longName;
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
	
	@Override
	public String toString() {
		return this.longName;
	}
}
