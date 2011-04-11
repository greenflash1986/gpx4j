package org.casaca.gpx4j.tools.rangefinder;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Properties;

import org.casaca.gpx4j.core.data.CoordinatesObject;
import org.casaca.gpx4j.core.data.Point;
import org.casaca.gpx4j.core.data.PointsSequence;
import org.casaca.gpx4j.core.data.Route;
import org.casaca.gpx4j.core.data.Track;
import org.casaca.gpx4j.core.data.TrackSegment;
import org.casaca.gpx4j.core.data.Waypoint;
import org.casaca.gpx4j.tools.Tool;

public abstract class AbstractRangefinder extends Tool implements IRangefinder {
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
	
	public AbstractRangefinder(Properties props, BigDecimal planetRadius){
		super(props);
		
		this.planetRadius = planetRadius;
	}

	public BigDecimal getPlanetRadius() {
		return planetRadius;
	}

	public void setPlanetRadius(BigDecimal planetRadius) {
		this.planetRadius = planetRadius;
	}
	
	//PRIVATE METHODS
	private BigDecimal[] getAscentDescent(Iterator<? extends CoordinatesObject> i){
		BigDecimal[] result = new BigDecimal[2];
		result[0]=result[1]=BigDecimal.ZERO;
		BigDecimal diff;
		CoordinatesObject c1, c2;
		
		if(i.hasNext()){
			c1 = i.next();
			while(i.hasNext()){
				c2 = i.next();
				diff = c2.getElevation().subtract(c1.getElevation());
				if(diff.signum()==-1)
					result[0] = result[0].add(diff.abs());
				else
					result[1] = result[1].add(diff);
				c1=c2;
			}
		}
		
		return result;
	}
	
	private BigDecimal getMaxElevation(Iterator<? extends CoordinatesObject> i){
		BigDecimal maxElevation = BigDecimal.ZERO;
		CoordinatesObject co = null;
		while(i.hasNext()){
			co = i.next();
			if(co.getElevation().compareTo(maxElevation)==1) maxElevation = co.getElevation();
		}
		if(co==null)
			return null;
		else
			return maxElevation;
	}
	
	private BigDecimal getMinElevation(Iterator<? extends CoordinatesObject> i){
		BigDecimal minElevation = null;
		CoordinatesObject co = null;
		if(i.hasNext()){
			minElevation = i.next().getElevation();
			while(i.hasNext()){
				co = i.next();
				if(co.getElevation().compareTo(minElevation)==-1)
					minElevation = co.getElevation();
			}
		}
		
		return minElevation;
	}
	//END PRIVATE METHODS
	
	//IRangefinder methods
	@Override
	public BigDecimal getDistance(CoordinatesObject c1, CoordinatesObject c2) {
		if(c1==null || c2==null) return new BigDecimal(0.0);
		return this.getDistance(c1.getLatitude(), c1.getLongitude(), c2.getLatitude(), c2.getLongitude());
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

	@Override
	public BigDecimal[] getAscentDescent(PointsSequence ps) {
		return this.getAscentDescent(ps.getPoints().iterator());
	}

	@Override
	public BigDecimal[] getAscentDescent(Track t) {
		BigDecimal[] result = new BigDecimal[2];
		result[0] = result[1] = BigDecimal.ZERO;
		BigDecimal[] temp;
		TrackSegment ts;
		Iterator<TrackSegment> i = t.getTrackSegments().iterator();
		while(i.hasNext()){
			ts = i.next();
			temp = this.getAscentDescent(ts);
			result[0] = result[0].add(temp[0]);
			result[1] = result[1].add(temp[1]);
		}
		
		return result;
	}

	@Override
	public BigDecimal[] getAscentDescent(TrackSegment ts) {
		return this.getAscentDescent(ts.getWaypoints().iterator());
	}

	@Override
	public BigDecimal[] getAscentDescent(Route r) {
		return this.getAscentDescent(r.getWaypoints().iterator());
	}

	@Override
	public BigDecimal getMaxElevation(Track t) {
		BigDecimal maxElevation = BigDecimal.ZERO;
		BigDecimal elevation;
		Iterator<TrackSegment> i = t.getTrackSegments().iterator();
		while(i.hasNext()){
			elevation = this.getMaxElevation(i.next());
			if(elevation.compareTo(maxElevation)==1) maxElevation = elevation;
		}
		
		return maxElevation;
	}

	@Override
	public BigDecimal getMaxElevation(TrackSegment ts) {
		return this.getMaxElevation(ts.getWaypoints().iterator());
	}

	@Override
	public BigDecimal getMaxElevation(Route r) {
		return this.getMaxElevation(r.getWaypoints().iterator());
	}

	@Override
	public BigDecimal getMaxElevation(PointsSequence ps) {
		return this.getMaxElevation(ps.getPoints().iterator());
	}

	@Override
	public BigDecimal getMinElevation(Track t) {
		BigDecimal minElevation = null;
		BigDecimal elevation = null;
		Iterator<TrackSegment> i = t.getTrackSegments().iterator();
		if(i.hasNext()){
			minElevation = this.getMinElevation(i.next());
			while(i.hasNext()){
				elevation = this.getMinElevation(i.next());
				if(elevation.compareTo(minElevation)==-1)
					minElevation = elevation;
			}
		}
		
		return minElevation;
	}

	@Override
	public BigDecimal getMinElevation(TrackSegment ts) {
		return this.getMinElevation(ts.getWaypoints().iterator());
	}

	@Override
	public BigDecimal getMinElevation(Route r) {
		return this.getMinElevation(r.getWaypoints().iterator());
	}

	@Override
	public BigDecimal getMinElevation(PointsSequence ps) {
		return this.getMinElevation(ps.getPoints().iterator());
	}
	//End IRangefinder methods
}
