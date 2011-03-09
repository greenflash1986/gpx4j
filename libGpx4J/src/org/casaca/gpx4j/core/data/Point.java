package org.casaca.gpx4j.core.data;

import java.math.BigDecimal;
import java.util.Calendar;

public class Point extends BaseObject {
	private BigDecimal elevation;
	private Calendar date;
	private BigDecimal latitude;
	private BigDecimal longitude;
	
	public Point(BigDecimal elevation, Calendar date, BigDecimal latitude, BigDecimal longitude) throws IllegalArgumentException{
		if(latitude == null || longitude == null) throw new IllegalArgumentException("Latitude and longitude must not be null");
		
		this.elevation = elevation;
		this.date = date;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Point(BigDecimal latitude, BigDecimal longitude){
		if(latitude == null || longitude == null) throw new IllegalArgumentException("Latitude and longitude must not be null");
		
		this.elevation = null;
		this.date = null;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public BigDecimal getElevation() {
		return elevation;
	}

	public void setElevation(BigDecimal elevation) {
		this.elevation = elevation;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}
}
