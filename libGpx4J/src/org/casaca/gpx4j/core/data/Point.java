package org.casaca.gpx4j.core.data;

import java.math.BigDecimal;
import java.util.Calendar;

public class Point extends CoordinatesObject {

	public Point(BigDecimal latitude, BigDecimal longitude, BigDecimal elevation, Calendar time) {
		super(latitude, longitude, elevation, time);
	}
	
	public Point(BigDecimal latitude, BigDecimal longitude, BigDecimal elevation) {
		super(latitude, longitude, elevation);
	}
	
	public Point(BigDecimal latitude, BigDecimal longitude) {
		super(latitude, longitude);
	}
}
