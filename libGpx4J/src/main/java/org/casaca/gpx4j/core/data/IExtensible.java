package org.casaca.gpx4j.core.data;

import java.util.Map;

public interface IExtensible {
	
	public void setName(String name);
	
	public String getName();
	
	public String getCanonicalClassName();
	
	public Map<String, Object> getFields();
}
