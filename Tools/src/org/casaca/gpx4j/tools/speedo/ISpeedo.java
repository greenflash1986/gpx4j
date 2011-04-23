package org.casaca.gpx4j.tools.speedo;

import java.util.List;

import org.casaca.gpx4j.core.data.CoordinatesObject;
import org.casaca.gpx4j.core.data.PointsSequence;
import org.casaca.gpx4j.core.data.Route;
import org.casaca.gpx4j.core.data.Track;
import org.casaca.gpx4j.core.data.TrackSegment;
import org.casaca.gpx4j.tools.data.MeasurementUnit;
import org.casaca.gpx4j.tools.data.Speed;

public interface ISpeedo {
	
	public MeasurementUnit getUnit();
	
	public Speed toMtSeg(Speed speed);
	
	public Speed toMph(Speed speed);
	
	public Speed toMinKm(Speed speed);
	
	public Speed toMinMile(Speed speed);
	
	public Speed toKmh(Speed speed);

	public Speed meanSpeed(Track t);
	
	public Speed medianSpeed(Track t);
	
	public Speed minSpeed(Track t);
	
	public Speed minSpeedNotZero(Track t);
	
	public Speed maxSpeed(Track t);
	
	public List<Speed> getSpeeds(Track t, boolean storeSpeed);
	
	public Speed meanSpeed(TrackSegment ts);
	
	public Speed medianSpeed(TrackSegment ts);
	
	public Speed minSpeed(TrackSegment ts);
	
	public Speed minSpeedNotZero(TrackSegment ts);
	
	public Speed maxSpeed(TrackSegment ts);
	
	public List<Speed> getSpeeds(TrackSegment ts, boolean storeSpeed);
	
	public List<Speed> getSpeedPerSecond(TrackSegment ts, boolean storeSpeed);
	
	public Speed meanSpeed(Route r);
	
	public Speed medianSpeed(Route r);
	
	public Speed minSpeed(Route r);
	
	public Speed minSpeedNotZero(Route r);
	
	public Speed maxSpeed(Route r);
	
	public List<Speed> getSpeeds(Route r, boolean storeSpeed);
	
	public List<Speed> getSpeedPerSecond(Route r, boolean storeSpeed);
	
	public Speed meanSpeed(PointsSequence ps);
	
	public Speed medianSpeed(PointsSequence ps);
	
	public Speed minSpeed(PointsSequence ps);
	
	public Speed minSpeedNotZero(PointsSequence ps);
	
	public Speed maxSpeed(PointsSequence ps);
	
	public List<Speed> getSpeeds(PointsSequence ps, boolean storeSpeed);
	
	public List<Speed> getSpeedPerSecond(PointsSequence ps, boolean storeSpeed);
	
	public Speed getSpeed(CoordinatesObject c1, CoordinatesObject c2, boolean storeSpeed);
}
