package org.casaca.gpx4j.tools.chronometer;

import java.math.BigDecimal;
import java.util.Properties;

import org.casaca.gpx4j.core.data.CoordinatesObject;

public class MillisChronometer extends AbstractChronometer {

	public MillisChronometer(Properties props) {
		super(props);
	}

	@Override
	public BigDecimal getDuration(CoordinatesObject c1, CoordinatesObject c2) {
		if(c1==null || c1.getTime()==null || c2==null || c2.getTime()==null) return BigDecimal.ZERO;
		
		return BigDecimal.valueOf(Math.abs(c1.getTime().getTimeInMillis()-c2.getTime().getTimeInMillis()));
	}
}
