package org.casaca.gpx4j.core.data;

import java.math.BigDecimal;

public class Degrees extends BaseObject {
	private BigDecimal degrees;
	
	public Degrees(BigDecimal degrees) throws IllegalArgumentException{
		if(degrees.compareTo(BigDecimal.valueOf(0.0))==-1 || degrees.compareTo(BigDecimal.valueOf(360.0))==1) throw new IllegalArgumentException("Degrees must be greater than zero and less than 360");
		
		this.degrees=degrees;
	}
	
	public BigDecimal getDegrees(){
		return this.degrees;
	}
}
