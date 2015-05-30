package org.casaca.gpx4j.core.data;

import java.math.BigDecimal;

public class Bounds extends BaseObject {
	private BigDecimal minLatitude;
	private BigDecimal minLongitude;
	private BigDecimal maxLatitude;
	private BigDecimal maxLongitude;
	
	public Bounds(BigDecimal minLatitude, BigDecimal minLongitude, BigDecimal maxLatitude,BigDecimal maxLongitude) throws IllegalArgumentException{
		if(minLatitude == null || minLongitude == null || maxLatitude == null || maxLongitude == null)
			throw new IllegalArgumentException("Error creating bounds. Latitudes and longitudes must not be null");
		
		this.minLatitude = minLatitude;
		this.minLongitude = minLongitude;
		this.maxLatitude = maxLatitude;
		this.maxLongitude = maxLongitude;
	}

	public BigDecimal getMinLatitude() {
		return minLatitude;
	}

	public BigDecimal getMinLongitude() {
		return minLongitude;
	}

	public BigDecimal getMaxLatitude() {
		return maxLatitude;
	}

	public BigDecimal getMaxLongitude() {
		return maxLongitude;
	}

	@Override
	public String toString() {
		return "("+minLatitude+", "+minLongitude+") ("+maxLatitude+", "+maxLongitude+")";
	}
}
