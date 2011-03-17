package org.casaca.gpx4j.tools.speedo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.casaca.gpx4j.core.data.Point;
import org.casaca.gpx4j.core.data.PointsSequence;
import org.casaca.gpx4j.core.data.Route;
import org.casaca.gpx4j.core.data.Track;
import org.casaca.gpx4j.core.data.TrackSegment;
import org.casaca.gpx4j.core.data.Waypoint;
import org.casaca.gpx4j.core.exception.GpxPropertiesException;
import org.casaca.gpx4j.tools.GpxTools;
import org.casaca.gpx4j.tools.exception.GpxRangefinderException;
import org.casaca.gpx4j.tools.exception.GpxSpeedoException;
import org.casaca.gpx4j.tools.rangefinder.IRangefinder;

public class AbstractSpeedo implements ISpeedo {
	
	private GpxTools tools;
	private IRangefinder rf;
	
	public AbstractSpeedo() throws GpxSpeedoException{
		this.tools = GpxTools.getTools();
		try {
			this.rf = this.tools.getRangefinder();
		} catch (GpxRangefinderException e) {
			throw new GpxSpeedoException(e);
		} catch (GpxPropertiesException e) {
			throw new GpxSpeedoException(e);
		}
	}
	
	private BigDecimal medianSpeed(BigDecimal[] speeds){
		Arrays.sort(speeds);
		if(speeds.length%2==0){
			return speeds[speeds.length/2].add(speeds[(speeds.length/2)-1]).divide(BigDecimal.valueOf(2.0));
		}
		else
			return speeds[speeds.length/2];
	}
	
	private BigDecimal getSpeed(BigDecimal distance, long time){
		BigDecimal hours = BigDecimal.valueOf(time).divide(BigDecimal.valueOf(3600000));
		return distance.divide(hours);
	}

	@Override
	public BigDecimal meanSpeed(Track t) {
		BigDecimal speed = new BigDecimal(0.0);
		Iterator<TrackSegment> ts = t.getTrackSegments().iterator();
		while(ts.hasNext())
			speed = speed.add(this.meanSpeed(ts.next()));
		
		return speed.divide(BigDecimal.valueOf(t.getTrackSegments().size()));
	}

	@Override
	public BigDecimal medianSpeed(Track t) {
		return this.medianSpeed(this.getSpeeds(t));
	}

	@Override
	public BigDecimal minSpeed(Track t) {
		BigDecimal minSpeed = null;;
		BigDecimal speed;
		Iterator<TrackSegment> ts = t.getTrackSegments().iterator();
		if(ts.hasNext()){
			minSpeed = this.minSpeed(ts.next());
			while(ts.hasNext()){
				speed = this.minSpeed(ts.next());
				if(minSpeed.compareTo(speed)==1) minSpeed = speed;
			}
		}
		
		return minSpeed;
	}

	@Override
	public BigDecimal minSpeedNotZero(Track t) {
		BigDecimal minSpeed = null;;
		BigDecimal speed;
		Iterator<TrackSegment> ts = t.getTrackSegments().iterator();
		if(ts.hasNext()){
			minSpeed = this.minSpeed(ts.next());
			while(ts.hasNext()){
				speed = this.minSpeed(ts.next());
				if(minSpeed.compareTo(speed)==1) minSpeed = speed;
			}
		}
		
		return (minSpeed.compareTo(BigDecimal.valueOf(0.0))==0)?null:minSpeed;
	}

	@Override
	public BigDecimal maxSpeed(Track t) {
		BigDecimal maxSpeed = null;;
		BigDecimal speed;
		Iterator<TrackSegment> ts = t.getTrackSegments().iterator();
		if(ts.hasNext()){
			maxSpeed = this.maxSpeed(ts.next());
			while(ts.hasNext()){
				speed = this.maxSpeed(ts.next());
				if(maxSpeed.compareTo(speed)==-1) maxSpeed = speed;
			}
		}
		
		return maxSpeed;
	}

	@Override
	public BigDecimal[] getSpeeds(Track t) {
		List<BigDecimal> speeds = new ArrayList<BigDecimal>();
		Iterator<TrackSegment> ts = t.getTrackSegments().iterator();
		while(ts.hasNext()){
			speeds.addAll(Arrays.asList(this.getSpeeds(ts.next())));
		}
		
		return speeds.toArray(new BigDecimal[speeds.size()]);
	}

	@Override
	public BigDecimal[] getSpeeds(Track t, int secondsInterval) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal meanSpeed(TrackSegment ts) {
		BigDecimal speed = new BigDecimal(0.0);
		Iterator<Waypoint> i = ts.getWaypoints().iterator();
		Waypoint w;
		if(i.hasNext()){
			w = i.next();
			while(i.hasNext()){
				speed = speed.add(this.getSpeed(w, (w=i.next())));
			}
			speed = speed.divide(BigDecimal.valueOf(ts.getWaypoints().size()));
		}
		
		return speed;
	}

	@Override
	public BigDecimal medianSpeed(TrackSegment ts) {
		return this.medianSpeed(this.getSpeeds(ts));
	}

	@Override
	public BigDecimal minSpeed(TrackSegment ts) {
		BigDecimal[] speeds = this.getSpeeds(ts);
		Arrays.sort(speeds);
		return (speeds.length>0)?speeds[0]:null;
	}

	@Override
	public BigDecimal minSpeedNotZero(TrackSegment ts) {
		BigDecimal[] speeds = this.getSpeeds(ts);
		Arrays.sort(speeds);
		for (int i = 0; i < speeds.length; i++) {
			if(speeds[i].compareTo(BigDecimal.valueOf(0.0))==1) return speeds[i];
		}
		
		return null;
	}

	@Override
	public BigDecimal maxSpeed(TrackSegment ts) {
		BigDecimal[] speeds = this.getSpeeds(ts);
		Arrays.sort(speeds);
		return (speeds.length>0)?speeds[speeds.length-1]:null;
	}

	@Override
	public BigDecimal[] getSpeeds(TrackSegment ts) {
		List<BigDecimal> speeds = new ArrayList<BigDecimal>();
		Iterator<Waypoint> ws = ts.getWaypoints().iterator();
		Waypoint w1, w2;
		
		if(ws.hasNext()){
			w1=ws.next();
			while(ws.hasNext()){
				w2=ws.next();
				speeds.add(this.getSpeed(w1, w2));
				w1=w2;
			}
		}
		
		return speeds.toArray(new BigDecimal[speeds.size()]);
	}

	@Override
	public BigDecimal[] getSpeeds(TrackSegment ts, int secondsInterval) {
		Iterator<Waypoint> ws = ts.getWaypoints().iterator();
		List<BigDecimal> speeds = new ArrayList<BigDecimal>();
		if(ws.hasNext()){
			BigDecimal speed1 = BigDecimal.valueOf(0.0);
			BigDecimal speed2;
			BigDecimal increment=speed1;//0
			long seconds;
			speeds.add(speed1);//second 0 == speed 0
			Waypoint w1 = ws.next(), w2;
			while(ws.hasNext()){
				w2=ws.next();
				seconds = Math.abs(w2.getTime().getTimeInMillis()-w1.getTime().getTimeInMillis())/1000;
				speed2 = this.getSpeed(w1, w2);
				for(int i=1;i<seconds;i++){
					increment = increment.add(speed2.subtract(speed1).divide(BigDecimal.valueOf(seconds)).multiply(BigDecimal.valueOf(i)));
					speeds.add(increment);
				}
				speed1=increment=speed2;
			}
		}
		
		return speeds.toArray(new BigDecimal[speeds.size()]);
	}

	@Override
	public BigDecimal meanSpeed(Route r) {
		BigDecimal speed = new BigDecimal(0.0);
		Iterator<Waypoint> i = r.getWaypoints().iterator();
		Waypoint w;
		if(i.hasNext()){
			w = i.next();
			while(i.hasNext()){
				speed = speed.add(this.getSpeed(w, (w=i.next())));
			}
			speed = speed.divide(BigDecimal.valueOf(r.getWaypoints().size()));
		}
		
		return speed;
	}

	@Override
	public BigDecimal medianSpeed(Route r) {
		return this.medianSpeed(this.getSpeeds(r));
	}

	@Override
	public BigDecimal minSpeed(Route r) {
		BigDecimal[] speeds = this.getSpeeds(r);
		Arrays.sort(speeds);
		
		return (speeds.length>0)?speeds[0]:null;
	}

	@Override
	public BigDecimal minSpeedNotZero(Route r) {
		BigDecimal[] speeds = this.getSpeeds(r);
		Arrays.sort(speeds);
		for (int i = 0; i < speeds.length; i++) {
			if(speeds[i].compareTo(BigDecimal.valueOf(0.0))==1)
				return speeds[i];
		}
		
		return null;
	}

	@Override
	public BigDecimal maxSpeed(Route r) {
		BigDecimal[] speeds = this.getSpeeds(r);
		Arrays.sort(speeds);
		return (speeds.length>0)?speeds[speeds.length-1]:null;
	}

	@Override
	public BigDecimal[] getSpeeds(Route r) {
		List<BigDecimal> speeds = new ArrayList<BigDecimal>();
		Iterator<Waypoint> ws = r.getWaypoints().iterator();
		Waypoint w1, w2;
		
		if(ws.hasNext()){
			w1=ws.next();
			while(ws.hasNext()){
				w2=ws.next();
				speeds.add(this.getSpeed(w1, w2));
				w1=w2;
			}
		}
		
		return speeds.toArray(new BigDecimal[speeds.size()]);
	}

	@Override
	public BigDecimal[] getSpeeds(Route r, int secondsInterval) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal meanSpeed(PointsSequence ps) {
		BigDecimal speed = new BigDecimal(0.0);
		Iterator<Point> i = ps.getPoints().iterator();
		Point p;
		if(i.hasNext()){
			p = i.next();
			while(i.hasNext()){
				speed = speed.add(this.getSpeed(p, (p=i.next())));
			}
			speed = speed.divide(BigDecimal.valueOf(ps.getPoints().size()));
		}
		
		return speed;
	}

	@Override
	public BigDecimal medianSpeed(PointsSequence ps) {
		return this.medianSpeed(this.getSpeeds(ps));
	}

	@Override
	public BigDecimal minSpeed(PointsSequence ps) {
		BigDecimal[] speeds = this.getSpeeds(ps);
		Arrays.sort(speeds);
		return (speeds.length>0)?speeds[0]:null;
	}

	@Override
	public BigDecimal minSpeedNotZero(PointsSequence ps) {
		BigDecimal[] speeds = this.getSpeeds(ps);
		Arrays.sort(speeds);
		for (int i = 0; i < speeds.length; i++) {
			if(speeds[i].compareTo(BigDecimal.valueOf(0.0))==1)
				return speeds[i];
		}
		
		return null;
	}

	@Override
	public BigDecimal maxSpeed(PointsSequence ps) {
		BigDecimal[] speeds = this.getSpeeds(ps);
		Arrays.sort(speeds);
		return (speeds.length>0)?speeds[speeds.length-1]:null;
	}

	@Override
	public BigDecimal[] getSpeeds(PointsSequence ps) {
		List<BigDecimal> speeds = new ArrayList<BigDecimal>();
		Iterator<Point> p = ps.getPoints().iterator();
		Point p1, p2;
		
		if(p.hasNext()){
			p1=p.next();
			while(p.hasNext()){
				p2=p.next();
				speeds.add(this.getSpeed(p1, p2));
				p1=p2;
			}
		}
		
		return speeds.toArray(new BigDecimal[speeds.size()]);
	}

	@Override
	public BigDecimal[] getSpeeds(PointsSequence ps, int secondsInterval) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getSpeed(Waypoint w1, Waypoint w2) {
		return this.getSpeed(this.rf.getDistance(w1, w2), Math.abs(w2.getTime().getTimeInMillis()-w1.getTime().getTimeInMillis()));
	}

	@Override
	public BigDecimal getSpeed(Point p1, Point p2) {
		return this.getSpeed(this.rf.getDistance(p1, p2), Math.abs(p2.getDate().getTimeInMillis()-p1.getDate().getTimeInMillis()));
	}
}