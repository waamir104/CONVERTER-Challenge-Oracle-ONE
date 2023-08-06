package waamir104.converter.model;

public class ConverterSelectionOption {
	private int id;
	private String name;
	
	public ConverterSelectionOption() {}
	
	public ConverterSelectionOption(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
