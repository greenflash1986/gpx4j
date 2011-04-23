package org.casaca.gpx4j.core.data;

import java.util.Map;

public class StringExtensible extends AbstractExtensible {
	
	public String name;
	public String value;
	
	//Coded for reflection
	@SuppressWarnings("unused")
	private StringExtensible(){
		super();
		this.name=value=null;
	}
	
	public StringExtensible(String name, String value){
		this.name = name;
		this.value = value;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	public String getValue(){
		return this.value;
	}

	@Override
	public Map<String, Object> getFields() {
		this.map.clear();
		this.map.put(name, value);
		
		return this.map;
	}
}