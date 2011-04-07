package org.casaca.gpx4j.tools.chronometer;

import java.math.BigDecimal;

import org.casaca.gpx4j.core.data.CoordinatesObject;

public class MillisChronometer extends AbstractChronometer {

	@Override
	public BigDecimal getDuration(CoordinatesObject c1, CoordinatesObject c2) {
		if(c1==null || c1.getTime()==null || c2==null || c2.getTime()==null) return BigDecimal.ZERO;
		
		return BigDecimal.valueOf(Math.abs(c1.getTime().getTimeInMillis()-c2.getTime().getTimeInMillis()));
	}
}
