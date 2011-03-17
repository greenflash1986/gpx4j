package org.casaca.gpx4j.tools.rangefinder;

import java.math.BigDecimal;

public class SphericalRangefinder extends AbstractRangefinder {

	public SphericalRangefinder() {
		super(EARTH_MEAN_RADIUS);
	}
	
	public SphericalRangefinder(BigDecimal planetRadius){
		super(planetRadius);
	}

	@Override
	public BigDecimal getDistance(BigDecimal lat1, BigDecimal lon1, BigDecimal lat2, BigDecimal lon2) {
		if(lat1==null || lon1==null || lat2==null || lon2==null) return new BigDecimal(0.0);
		
		return new BigDecimal(
				Math.acos(
						Math.sin(Math.toRadians(lat1.doubleValue()))
						*Math.sin(Math.toRadians(lat2.doubleValue()))
						+
						Math.cos(Math.toRadians(lat1.doubleValue()))
						*Math.cos(Math.toRadians(lat2.doubleValue()))
						*Math.cos(
								Math.toRadians(lon2.doubleValue())
								-Math.toRadians(lon1.doubleValue()))
				)*this.getPlanetRadius().doubleValue());
	}
}