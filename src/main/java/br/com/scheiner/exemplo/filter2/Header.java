package br.com.scheiner.exemplo.filter2;

public class Header {
	
	
	public Header(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Header [key=" + key + ", value=" + value + "]";
	}
	
	

}
