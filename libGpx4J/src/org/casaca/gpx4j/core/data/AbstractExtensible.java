package org.casaca.gpx4j.core.data;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractExtensible implements IExtensible {
	
	protected Map<String, Object> map;
	
	public AbstractExtensible(){
		this.map = new HashMap<String, Object>();
	}

	@Override
	public String getCanonicalClassName() {
		return this.getClass().getCanonicalName();
	}
}