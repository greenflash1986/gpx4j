package org.casaca.gpx4j.tools.rangefinder;

import java.math.BigDecimal;
import java.util.Iterator;

import org.casaca.gpx4j.core.data.Point;
import org.casaca.gpx4j.core.data.PointsSequence;
import org.casaca.gpx4j.core.data.Route;
import org.casaca.gpx4j.core.data.Track;
import org.casaca.gpx4j.core.data.TrackSegment;
import org.casaca.gpx4j.core.data.Waypoint;

public abstract class AbstractRangefinder implements IRangefinder {
	public static final BigDecimal SUN_EQUATORIAL_RADIUS = new BigDecimal(695500);
	
	public static final BigDecimal MERCURY_MEAN_RADIUS = new BigDecimal(2439.7);
	
	public static final BigDecimal VENUS_MEAN_RADIUS = new BigDecimal(6051.8);
	
	public static final BigDecimal EARTH_MEAN_RADIUS = new BigDecimal(6371);
	public static final BigDecimal EARTH_EQUATORIAL_RADIUS = new BigDecimal(6378.1);
	public static final BigDecimal EARTH_POLAR_RADIUS = new BigDecimal(6356.8);
	
	public static final BigDecimal MARS_EQUATORIAL_RADIUS = new BigDecimal(3396.2);
	public static final BigDecimal MARS_POLAR_RADIUS = new BigDecimal(3376.2);
	
	public static final BigDecimal JUPITER_EQUATORIAL_RADIUS = new BigDecimal(71492);
	public static final BigDecimal JUPITER_POLAR_RADIUS = new BigDecimal(66854);
	
	public static final BigDecimal SATURN_EQUATORIAL_RADIUS = new BigDecimal(60268);
	public static final BigDecimal SATURN_POLAR_RADIUS = new BigDecimal(54364);
	
	public static final BigDecimal URANUS_EQUATORIAL_RADIUS = new BigDecimal(2559);
	public static final BigDecimal URANUS_POLAR_RADIUS = new BigDecimal(24973);
	
	public static final BigDecimal NEPTUNE_EQUATORIAL_RADIUS = new BigDecimal(24764);
	public static final BigDecimal NEPTUNE_POLAR_RADIUS = new BigDecimal(24341);
	
	public static final BigDecimal PLUTO_MEAN_RADIUS = new BigDecimal(1153);
	
	private BigDecimal planetRadius;
	
	public AbstractRangefinder(BigDecimal planetRadius){
		this.planetRadius = planetRadius;
	}

	public BigDecimal getPlanetRadius() {
		return planetRadius;
	}

	public void setPlanetRadius(BigDecimal planetRadius) {
		this.planetRadius = planetRadius;
	}
	
	@Override
	public BigDecimal getDistance(Waypoint w1, Waypoint w2) {
		if(w1==null || w2==null) return new BigDecimal(0.0);
		return this.getDistance(w1.getLatitude(), w1.getLongitude(), w2.getLatitude(), w2.getLongitude());
	}

	@Override
	public BigDecimal getDistance(Point p1, Point p2) {
		if(p1==null || p2==null) return new BigDecimal(0.0);
		return this.getDistance(p1.getLatitude(), p1.getLongitude(), p2.getLatitude(), p2.getLongitude());
	}

	@Override
	public BigDecimal getDistance(PointsSequence ps) {
		if(ps==null) return new BigDecimal(0.0);
		Iterator<Point> i = ps.getPoints().iterator();
		BigDecimal d = new BigDecimal(0.0);
		if(i.hasNext()){
			Point p = i.next();
			while(i.hasNext()){
				d = d.add(this.getDistance(p, (p=i.next())));
			}
		}
		
		return d;
	}

	@Override
	public BigDecimal getDistance(Track t) {
		if(t==null) return new BigDecimal(0.0);
		Iterator<TrackSegment> i = t.getTrackSegments().iterator();
		BigDecimal d = new BigDecimal(0.0);
		while(i.hasNext()){
			d = d.add(this.getDistance(i.next()));
		}
		
		return d;
	}

	@Override
	public BigDecimal getDistance(TrackSegment ts) {
		if(ts==null) return new BigDecimal(0.0);
		Iterator<Waypoint> i = ts.getWaypoints().iterator();
		BigDecimal d = new BigDecimal(0.0);
		if(i.hasNext()){
			Waypoint w = i.next();
			while(i.hasNext()){
				d = d.add(this.getDistance(w, (w=i.next())));
			}
		}
		
		return d;
	}

	@Override
	public BigDecimal getDistance(Route r) {
		if(r==null) return new BigDecimal(0.0);
		Iterator<Waypoint> i = r.getWaypoints().iterator();
		BigDecimal d = new BigDecimal(0.0);
		if(i.hasNext()){
			Waypoint w = i.next();
			while(i.hasNext()){
				d = d.add(this.getDistance(w, (w=i.next())));
			}
		}
		
		return d;
	}
}
