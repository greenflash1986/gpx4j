package org.casaca.gpx4j.tools.rangefinder;

import java.math.BigDecimal;

public class HaversineRangefinder extends AbstractRangefinder {
	
	public HaversineRangefinder(BigDecimal planetRadius) {
		super(planetRadius);
	}

	@Override
	public String getUnit() {
		return "m";
	}

	@Override
	public BigDecimal getDistance(BigDecimal la1, BigDecimal lo1, BigDecimal la2, BigDecimal lo2){
		if(la1==null || lo1==null || la2==null || lo2==null) return new BigDecimal(0.0);

		double dLat = Math.toRadians(la2.doubleValue()-la1.doubleValue());
		double dLon = Math.toRadians(lo2.doubleValue()-lo1.doubleValue());
		double a = Math.pow(Math.sin(dLat/2), 2)+Math.cos(la1.doubleValue())*Math.cos(la2.doubleValue())*Math.pow(Math.sin(dLon/2), 2);
		double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		
		return new BigDecimal(this.getPlanetRadius().doubleValue()*c*1000);
	}
}