package org.casaca.gpx4j.tools.chronometer;

import java.math.BigDecimal;
import java.util.Calendar;

import org.casaca.gpx4j.core.data.CoordinatesObject;
import org.casaca.gpx4j.core.data.PointsSequence;
import org.casaca.gpx4j.core.data.Route;
import org.casaca.gpx4j.core.data.Track;
import org.casaca.gpx4j.core.data.TrackSegment;
import org.casaca.gpx4j.tools.data.IMeasurementUnit;

public interface IChronometer {
	
	public IMeasurementUnit getUnit();
	
	public BigDecimal getDuration(CoordinatesObject c1, CoordinatesObject c2);
	
	public BigDecimal getDuration(Track t);
	
	public BigDecimal getDuration(TrackSegment ts);
	
	public BigDecimal getDuration(Route r);
	
	public BigDecimal getDuration(PointsSequence ps);
	
	public String getDuration(BigDecimal millis);
	
	public Calendar starts(Track t);
	
	public Calendar starts(TrackSegment ts);
	
	public Calendar starts(Route r);
	
	public Calendar starts(PointsSequence ps);
	
	public Calendar ends(Track t);
	
	public Calendar ends(TrackSegment ts);
	
	public Calendar ends(Route r);
	
	public Calendar ends(PointsSequence ps);
}
