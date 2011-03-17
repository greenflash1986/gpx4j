package org.casaca.gpx4j.tools.speedo;

import java.math.BigDecimal;

import org.casaca.gpx4j.core.data.Point;
import org.casaca.gpx4j.core.data.PointsSequence;
import org.casaca.gpx4j.core.data.Route;
import org.casaca.gpx4j.core.data.Track;
import org.casaca.gpx4j.core.data.TrackSegment;
import org.casaca.gpx4j.core.data.Waypoint;

public interface ISpeedo {

	public BigDecimal meanSpeed(Track t);
	
	public BigDecimal medianSpeed(Track t);
	
	public BigDecimal minSpeed(Track t);
	
	public BigDecimal minSpeedNotZero(Track t);
	
	public BigDecimal maxSpeed(Track t);
	
	public BigDecimal[] getSpeeds(Track t);
	
	public BigDecimal[] getSpeeds(Track t, int secondsInterval);
	
	public BigDecimal meanSpeed(TrackSegment ts);
	
	public BigDecimal medianSpeed(TrackSegment ts);
	
	public BigDecimal minSpeed(TrackSegment ts);
	
	public BigDecimal minSpeedNotZero(TrackSegment ts);
	
	public BigDecimal maxSpeed(TrackSegment ts);
	
	public BigDecimal[] getSpeeds(TrackSegment ts);
	
	public BigDecimal[] getSpeeds(TrackSegment ts, int secondsInterval);
	
	public BigDecimal meanSpeed(Route r);
	
	public BigDecimal medianSpeed(Route r);
	
	public BigDecimal minSpeed(Route r);
	
	public BigDecimal minSpeedNotZero(Route r);
	
	public BigDecimal maxSpeed(Route r);
	
	public BigDecimal[] getSpeeds(Route r);
	
	public BigDecimal[] getSpeeds(Route r, int secondsInterval);
	
	public BigDecimal meanSpeed(PointsSequence ps);
	
	public BigDecimal medianSpeed(PointsSequence ps);
	
	public BigDecimal minSpeed(PointsSequence ps);
	
	public BigDecimal minSpeedNotZero(PointsSequence ps);
	
	public BigDecimal maxSpeed(PointsSequence ps);
	
	public BigDecimal[] getSpeeds(PointsSequence ps);
	
	public BigDecimal[] getSpeeds(PointsSequence ps, int secondsInterval);
	
	public BigDecimal getSpeed(Waypoint w1, Waypoint w2);
	
	public BigDecimal getSpeed(Point p1, Point p2);
}
