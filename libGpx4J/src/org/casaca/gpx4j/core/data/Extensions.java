package org.casaca.gpx4j.core.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Extensions extends BaseObject {
	private Map<String, Extension> extensions;
	
	public Extensions(){
		this.extensions=new HashMap<String, Extension>();
	}
	
	public String getValue(String key){
		return this.extensions.get(key).getValue();
	}
	
	public Set<String> getKeys(){
		return this.extensions.keySet();
	}
	
	public int count(){
		return this.extensions.size();
	}
	
	public void addExtension(Extension extension){
		this.extensions.put(extension.getKey(), extension);
	}
}
