package org.casaca.gpx4j.tools.rangefinder;

import java.math.BigDecimal;
import java.util.Properties;

import org.casaca.gpx4j.core.data.CoordinatesObject;

public class HaversineWithElevationRangefinder extends HaversineRangefinder {

	public HaversineWithElevationRangefinder(Properties props, BigDecimal planetRadius) {
		super(props, planetRadius);
	}

	@Override
	public BigDecimal getDistance(CoordinatesObject c1, CoordinatesObject c2) {
		BigDecimal d = super.getDistance(c1, c2);
		if(Math.abs(c1.getElevation().doubleValue()-c2.getElevation().doubleValue())==0) return d;
		
		return new BigDecimal(Math.hypot(d.doubleValue(), Math.abs(c1.getElevation().doubleValue()-c2.getElevation().doubleValue()))*1000);
	}
}