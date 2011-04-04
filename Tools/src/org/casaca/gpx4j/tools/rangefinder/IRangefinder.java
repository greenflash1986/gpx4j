package org.casaca.gpx4j.tools.rangefinder;

import java.math.BigDecimal;

import org.casaca.gpx4j.core.data.CoordinatesObject;
import org.casaca.gpx4j.core.data.PointsSequence;
import org.casaca.gpx4j.core.data.Route;
import org.casaca.gpx4j.core.data.Track;
import org.casaca.gpx4j.core.data.TrackSegment;

public interface IRangefinder {
	
	public String getUnit();
	
	public BigDecimal getDistance(BigDecimal lat1, BigDecimal lon1, BigDecimal lat2, BigDecimal lon2);
	
	public BigDecimal getDistance(CoordinatesObject c1, CoordinatesObject c2);
	
	public BigDecimal getDistance(PointsSequence ps);
	
	public BigDecimal getDistance(Track t);
	
	public BigDecimal getDistance(TrackSegment ts);
	
	public BigDecimal getDistance(Route r);
	
	public BigDecimal[] getAscentDescent(PointsSequence ps);
	
	public BigDecimal[] getAscentDescent(Track t);
	
	public BigDecimal[] getAscentDescent(TrackSegment ts);
	
	public BigDecimal[] getAscentDescent(Route r);
	
	public BigDecimal getMaxElevation(Track t);
	
	public BigDecimal getMaxElevation(TrackSegment ts);
	
	public BigDecimal getMaxElevation(Route r);
	
	public BigDecimal getMaxElevation(PointsSequence ps);
	
	public BigDecimal getMinElevation(Track t);
	
	public BigDecimal getMinElevation(TrackSegment ts);
	
	public BigDecimal getMinElevation(Route r);
	
	public BigDecimal getMinElevation(PointsSequence ps);
}
