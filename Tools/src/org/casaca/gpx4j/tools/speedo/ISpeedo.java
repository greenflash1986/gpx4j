package org.casaca.gpx4j.tools.speedo;

import java.math.BigDecimal;

import org.casaca.gpx4j.core.data.CoordinatesObject;
import org.casaca.gpx4j.core.data.PointsSequence;
import org.casaca.gpx4j.core.data.Route;
import org.casaca.gpx4j.core.data.Track;
import org.casaca.gpx4j.core.data.TrackSegment;

public interface ISpeedo {

	public BigDecimal meanSpeed(Track t);
	
	public BigDecimal medianSpeed(Track t);
	
	public BigDecimal minSpeed(Track t);
	
	public BigDecimal minSpeedNotZero(Track t);
	
	public BigDecimal maxSpeed(Track t);
	
	public BigDecimal[] getSpeeds(Track t);
	
	public BigDecimal meanSpeed(TrackSegment ts);
	
	public BigDecimal medianSpeed(TrackSegment ts);
	
	public BigDecimal minSpeed(TrackSegment ts);
	
	public BigDecimal minSpeedNotZero(TrackSegment ts);
	
	public BigDecimal maxSpeed(TrackSegment ts);
	
	public BigDecimal[] getSpeeds(TrackSegment ts);
	
	public BigDecimal[] getSpeedPerSecond(TrackSegment ts);
	
	public BigDecimal meanSpeed(Route r);
	
	public BigDecimal medianSpeed(Route r);
	
	public BigDecimal minSpeed(Route r);
	
	public BigDecimal minSpeedNotZero(Route r);
	
	public BigDecimal maxSpeed(Route r);
	
	public BigDecimal[] getSpeeds(Route r);
	
	public BigDecimal[] getSpeedPerSecond(Route r);
	
	public BigDecimal meanSpeed(PointsSequence ps);
	
	public BigDecimal medianSpeed(PointsSequence ps);
	
	public BigDecimal minSpeed(PointsSequence ps);
	
	public BigDecimal minSpeedNotZero(PointsSequence ps);
	
	public BigDecimal maxSpeed(PointsSequence ps);
	
	public BigDecimal[] getSpeeds(PointsSequence ps);
	
	public BigDecimal[] getSpeedPerSecond(PointsSequence ps);
	
	public BigDecimal getSpeed(CoordinatesObject c1, CoordinatesObject c2);
}
