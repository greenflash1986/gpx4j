package org.casaca.gpx4j.core.data;

public class Degrees extends BaseObject {
	private Double degrees;
	
	public Degrees(Double degrees) throws IllegalArgumentException{
		if(degrees<0 || degrees>360) throw new IllegalArgumentException("Degrees must be greater than zero and less than 360");
		
		this.degrees=degrees;
	}
	
	public Double getDegrees(){
		return this.degrees;
	}
}
