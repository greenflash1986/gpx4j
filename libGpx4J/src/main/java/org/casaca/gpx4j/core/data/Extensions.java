package org.casaca.gpx4j.core.data;

import java.util.HashMap;
import java.util.Map;

public class Extensions extends BaseObject {
	private Map<String, Extension<? extends IExtensible>> extensions;
	
	public Extensions(){
		this.extensions=new HashMap<String, Extension<? extends IExtensible>>();
	}
	
	public Map<String, Extension<? extends IExtensible>> getExtensions(){
		return this.extensions;
	}
	
	public void addExtension(Extension<? extends IExtensible> extension){
		this.extensions.put(extension.getKey(), extension);
	}
}
