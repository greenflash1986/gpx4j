package org.casaca.gpx4j.tools.data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.casaca.gpx4j.core.data.BaseObject;
import org.casaca.gpx4j.core.data.IExtensible;

public class Bearing extends BaseObject implements IExtensible{

	private BigDecimal value;
	private CardinalDirection cardinalDirection;
	
	//IExtensible attributes
	private String name;
	//End IExtensible attributes
	
	//Coded for reflection
	private Bearing(){
		this.value = null;
		this.cardinalDirection = null;
		
		this.name = this.getClass().getSimpleName().substring(0,1).toLowerCase()+this.getClass().getSimpleName().substring(1);
	}
	
	public Bearing(BigDecimal bearing, CardinalDirection cardinalDirection){
		if(bearing==null || cardinalDirection==null) throw new IllegalArgumentException("Bearing or cardinal direction must not be null");
		
		this.value=bearing;
		this.cardinalDirection=cardinalDirection;
		
		this.name = this.getClass().getSimpleName().substring(0,1).toLowerCase()+this.getClass().getSimpleName().substring(1);
	}
	
	//IExtensible methods
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getCanonicalClassName() {
		return this.getClass().getCanonicalName();
	}

	@Override
	public Map<String, Object> getFields() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("value", this.value);
		this.cardinalDirection.setName("cardinalDirection");
		map.put("cardinalDirection", this.cardinalDirection);
		
		return map;
	}
	//End IExtensible methods
}
