package org.casaca.gpx4j.tools.speedo;

import java.util.List;

import org.casaca.gpx4j.core.data.CoordinatesObject;
import org.casaca.gpx4j.core.data.PointsSequence;
import org.casaca.gpx4j.core.data.Route;
import org.casaca.gpx4j.core.data.Track;
import org.casaca.gpx4j.core.data.TrackSegment;
import org.casaca.gpx4j.tools.data.IMeasurementUnit;
import org.casaca.gpx4j.tools.data.ISpeed;

public interface ISpeedo {
	
	public IMeasurementUnit getUnit();
	
	public ISpeed toMtSeg(ISpeed speed);
	
	public ISpeed toMph(ISpeed speed);
	
	public ISpeed toMinKm(ISpeed speed);
	
	public ISpeed toMinMile(ISpeed speed);
	
	public ISpeed toKmh(ISpeed speed);

	public ISpeed meanSpeed(Track t);
	
	public ISpeed medianSpeed(Track t);
	
	public ISpeed minSpeed(Track t);
	
	public ISpeed minSpeedNotZero(Track t);
	
	public ISpeed maxSpeed(Track t);
	
	public List<ISpeed> getSpeeds(Track t);
	
	public ISpeed meanSpeed(TrackSegment ts);
	
	public ISpeed medianSpeed(TrackSegment ts);
	
	public ISpeed minSpeed(TrackSegment ts);
	
	public ISpeed minSpeedNotZero(TrackSegment ts);
	
	public ISpeed maxSpeed(TrackSegment ts);
	
	public List<ISpeed> getSpeeds(TrackSegment ts);
	
	public List<ISpeed> getSpeedPerSecond(TrackSegment ts);
	
	public ISpeed meanSpeed(Route r);
	
	public ISpeed medianSpeed(Route r);
	
	public ISpeed minSpeed(Route r);
	
	public ISpeed minSpeedNotZero(Route r);
	
	public ISpeed maxSpeed(Route r);
	
	public List<ISpeed> getSpeeds(Route r);
	
	public List<ISpeed> getSpeedPerSecond(Route r);
	
	public ISpeed meanSpeed(PointsSequence ps);
	
	public ISpeed medianSpeed(PointsSequence ps);
	
	public ISpeed minSpeed(PointsSequence ps);
	
	public ISpeed minSpeedNotZero(PointsSequence ps);
	
	public ISpeed maxSpeed(PointsSequence ps);
	
	public List<ISpeed> getSpeeds(PointsSequence ps);
	
	public List<ISpeed> getSpeedPerSecond(PointsSequence ps);
	
	public ISpeed getSpeed(CoordinatesObject c1, CoordinatesObject c2);
}
