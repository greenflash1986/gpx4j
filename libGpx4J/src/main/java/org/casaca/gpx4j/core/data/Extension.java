package org.casaca.gpx4j.core.data;

public class Extension<T extends IExtensible> extends BaseObject {
	
	private String key;
	private T value;
	
	public Extension(String key, T value) throws IllegalArgumentException{
		if(key == null || value == null) throw new IllegalArgumentException("Key and value must not be null");
		
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public T getValue() {
		return value;
	}

	@Override
	public String toString() {
		return this.key+": "+this.value;
	}
}
