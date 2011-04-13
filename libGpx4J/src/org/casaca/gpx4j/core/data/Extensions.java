package org.casaca.gpx4j.core.data;

import java.util.HashMap;
import java.util.Map;

public class Extensions extends BaseObject {
	private Map<String, Extension> extensions;
	
	public Extensions(){
		this.extensions=new HashMap<String, Extension>();
	}
	
	public Map<String, Extension> getExtensions(){
		return this.extensions;
	}
	
	public void addExtension(Extension extension){
		this.extensions.put(extension.getKey(), extension);
	}
}
