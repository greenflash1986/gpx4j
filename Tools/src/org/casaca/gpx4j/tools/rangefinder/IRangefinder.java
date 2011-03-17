package org.casaca.gpx4j.tools.rangefinder;

import java.math.BigDecimal;

import org.casaca.gpx4j.core.data.Point;
import org.casaca.gpx4j.core.data.PointsSequence;
import org.casaca.gpx4j.core.data.Route;
import org.casaca.gpx4j.core.data.Track;
import org.casaca.gpx4j.core.data.TrackSegment;
import org.casaca.gpx4j.core.data.Waypoint;

public interface IRangefinder {
	
	public BigDecimal getDistance(BigDecimal lat1, BigDecimal lon1, BigDecimal lat2, BigDecimal lon2);
	
	public BigDecimal getDistance(Waypoint w1, Waypoint w2);
	
	public BigDecimal getDistance(Point p1, Point p2);
	
	public BigDecimal getDistance(PointsSequence ps);
	
	public BigDecimal getDistance(Track t);
	
	public BigDecimal getDistance(TrackSegment ts);
	
	public BigDecimal getDistance(Route r);
}
