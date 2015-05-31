package org.casaca.gpx4j.tools.rangefinder;

import java.math.BigDecimal;
import java.util.Properties;

import org.casaca.gpx4j.tools.data.MeasurementUnit;

public class SphericalRangefinder extends AbstractRangefinder {

	public SphericalRangefinder(Properties props) {
		super(props, EARTH_MEAN_RADIUS);
	}
	
	public SphericalRangefinder(Properties props, BigDecimal planetRadius){
		super(props, planetRadius);
	}

	@Override
	public MeasurementUnit getUnit() {
		return MeasurementUnit.METER;
	}

	@Override
	public BigDecimal getDistance(BigDecimal lat1, BigDecimal lon1, BigDecimal lat2, BigDecimal lon2) {
		if(lat1==null || lon1==null || lat2==null || lon2==null) return new BigDecimal(0.0);
		
		double rLat1 = Math.toRadians(lat1.doubleValue());
		double rLon1 = Math.toRadians(lon1.doubleValue());
		double rLat2 = Math.toRadians(lat2.doubleValue());
		double rLon2 = Math.toRadians(lon2.doubleValue());
		
		return new BigDecimal(
				Math.acos(
						Math.sin(rLat1)
						*Math.sin(rLat2)
						+
						Math.cos(rLat1)
						*Math.cos(rLat2)
						*Math.cos(rLon1-rLon2)
				)*this.getPlanetRadius().doubleValue()*1000);
	}
}