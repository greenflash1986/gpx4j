package org.casaca.gpx4j.tools.rangefinder;

import java.math.BigDecimal;

import org.casaca.gpx4j.core.data.Point;
import org.casaca.gpx4j.core.data.Waypoint;

public class HaversineWithElevationRangefinder extends HaversineRangefinder {

	public HaversineWithElevationRangefinder(BigDecimal planetRadius) {
		super(planetRadius);
	}

	@Override
	public BigDecimal getDistance(Waypoint w1, Waypoint w2) {
		BigDecimal d = super.getDistance(w1, w2);
		if(Math.abs(w1.getElevation().doubleValue()-w2.getElevation().doubleValue())==0) return d;
		
		return new BigDecimal(Math.hypot(d.doubleValue(), Math.abs(w1.getElevation().doubleValue()-w2.getElevation().doubleValue())));
	}

	@Override
	public BigDecimal getDistance(Point p1, Point p2) {
		BigDecimal d = super.getDistance(p1, p2);
		if(Math.abs(p1.getElevation().doubleValue()-p2.getElevation().doubleValue())==0) return d;
		
		return new BigDecimal(Math.hypot(d.doubleValue(), Math.abs(p1.getElevation().doubleValue()-p2.getElevation().doubleValue())));
	}
}