package org.casaca.gpx4j.tools.speedo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.casaca.gpx4j.core.data.CoordinatesObject;
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
import org.casaca.gpx4j.tools.util.Constants;

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
	
	//PRIVATE METHODS
	private BigDecimal medianSpeed(BigDecimal[] speeds){
		Arrays.sort(speeds);
		if(speeds.length%2==0){
			return speeds[speeds.length/2].add(speeds[(speeds.length/2)-1]).divide(BigDecimal.valueOf(2.0), Constants.APPLICATION_PRECISION_OPERATIONS, Constants.APPLICATION_ROUNDING_MODE);
		}
		else
			return speeds[speeds.length/2];
	}
	
	private BigDecimal getSpeed(BigDecimal distance, long time){
		//Distance in Km
		//Time in milliseconds
		BigDecimal hours = BigDecimal.valueOf(time).divide(BigDecimal.valueOf(3600000.0), Constants.APPLICATION_PRECISION_OPERATIONS, Constants.APPLICATION_ROUNDING_MODE);
		return distance.divide(hours, Constants.APPLICATION_PRECISION_OPERATIONS, Constants.APPLICATION_ROUNDING_MODE);
	}
	
	@SuppressWarnings("unused")
	private BigDecimal meanSpeed(Iterator<CoordinatesObject> i){
		BigDecimal speed = new BigDecimal(0.0);
		CoordinatesObject c;
		if(i.hasNext()){
			c = i.next();
			int count = 0;
			while(i.hasNext()){
				speed = speed.add(this.getSpeed(c, (c=i.next())));
				count++;
			}
			speed = speed.divide(BigDecimal.valueOf(count), Constants.APPLICATION_PRECISION_OPERATIONS, Constants.APPLICATION_ROUNDING_MODE);
		}
		
		return speed;
	}
	
	private BigDecimal meanSpeed(CoordinatesObject[] array){
		BigDecimal speed = new BigDecimal(0.0);
		if(array.length>1){
			for (int i = 0; i < array.length-1;) {
				speed = speed.add(this.getSpeed(array[i], array[++i]));
			}
			speed = speed.divide(BigDecimal.valueOf(array.length-1), Constants.APPLICATION_PRECISION_OPERATIONS, Constants.APPLICATION_ROUNDING_MODE);
		}
		
		return speed;
	}
	
	private BigDecimal minSpeed(BigDecimal[] speeds){
		Arrays.sort(speeds);
		return (speeds.length>0)?speeds[0]:null;
	}
	
	private BigDecimal minSpeedNotZero(BigDecimal[] speeds){
		Arrays.sort(speeds);
		for (int i = 0; i < speeds.length; i++)
			if(speeds[i].compareTo(BigDecimal.ZERO)==1)
				return speeds[i];
		
		return BigDecimal.ZERO;
	}
	
	private BigDecimal maxSpeed(BigDecimal[] speeds){
		Arrays.sort(speeds);
		return (speeds.length>0)?speeds[speeds.length-1]:null;
	}
	
	@SuppressWarnings("unused")
	private BigDecimal[] getSpeeds(Iterator<Waypoint> ws){
		List<BigDecimal> speeds = new ArrayList<BigDecimal>();
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
	
	private BigDecimal[] getSpeeds(CoordinatesObject[] array){
		BigDecimal[] speeds = new BigDecimal[(array.length>1)?array.length-1:0];
		if(array.length>1){
			for (int i = 0; i < array.length-1;) {
				speeds[i]=this.getSpeed(array[i], array[++i]);
			}
		}
		
		return speeds;
	}
	
	private BigDecimal[] getSpeedPerSecond(CoordinatesObject[] array){
		List<BigDecimal> speeds = new ArrayList<BigDecimal>();
		if(array.length>1){
			BigDecimal speed1 = BigDecimal.valueOf(0.0);
			BigDecimal speed2;
			BigDecimal increment=speed1;//0
			long seconds;
			speeds.add(speed1);//second 0 == speed 0
			CoordinatesObject w1, w2;
			for (int i = 0; i < array.length-1; i++) {
				w1 = array[i];
				w2 = array[i+1];
				seconds = Math.abs(w2.getTime().getTimeInMillis()-w1.getTime().getTimeInMillis())/1000;
				speed2 = this.getSpeed(w1, w2);
				increment = speed2.subtract(speed1).divide(BigDecimal.valueOf(seconds), Constants.APPLICATION_PRECISION_OPERATIONS, Constants.APPLICATION_ROUNDING_MODE);
				for(int j=1;j<seconds;j++){
					speeds.add(speed1.add(increment.multiply(BigDecimal.valueOf(j))));
				}
				speed1=speed2;
			}
		}
		
		return speeds.toArray(new BigDecimal[speeds.size()]);
	}
	//END PRIVATE METHODS
	
	//MEAN SPEED
	@Override
	public BigDecimal meanSpeed(Track t) {
		BigDecimal speed = new BigDecimal(0.0);
		Iterator<TrackSegment> ts = t.getTrackSegments().iterator();
		while(ts.hasNext())
			speed = speed.add(this.meanSpeed(ts.next()));
		
		return speed.divide(BigDecimal.valueOf(t.getTrackSegments().size()), Constants.APPLICATION_PRECISION_OPERATIONS, Constants.APPLICATION_ROUNDING_MODE);
	}

	@Override
	public BigDecimal meanSpeed(TrackSegment ts) {
		return this.meanSpeed(ts.getWaypoints().toArray(new CoordinatesObject[ts.getWaypoints().size()]));
	}
	
	@Override
	public BigDecimal meanSpeed(Route r) {
		return this.meanSpeed(r.getWaypoints().toArray(new CoordinatesObject[r.getWaypoints().size()]));
	}
	
	@Override
	public BigDecimal meanSpeed(PointsSequence ps) {
		return this.meanSpeed(ps.getPoints().toArray(new CoordinatesObject[ps.getPoints().size()]));
	}
	//END MEAN SPEED
	
	//MEDIAN SPEED
	@Override
	public BigDecimal medianSpeed(Track t) {
		return this.medianSpeed(this.getSpeeds(t));
	}

	@Override
	public BigDecimal medianSpeed(TrackSegment ts) {
		return this.medianSpeed(this.getSpeeds(ts));
	}
	
	@Override
	public BigDecimal medianSpeed(Route r) {
		return this.medianSpeed(this.getSpeeds(r));
	}
	
	@Override
	public BigDecimal medianSpeed(PointsSequence ps) {
		return this.medianSpeed(this.getSpeeds(ps));
	}
	//END MEDIAN SPEED
	
	//MIN SPEED
	@Override
	public BigDecimal minSpeed(Track t) {
		BigDecimal minSpeed = null;
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
	public BigDecimal minSpeed(TrackSegment ts) {
		return this.minSpeed(this.getSpeeds(ts));
	}

	@Override
	public BigDecimal minSpeed(Route r) {
		return this.minSpeed(this.getSpeeds(r));
	}
	
	@Override
	public BigDecimal minSpeed(PointsSequence ps) {
		return this.minSpeed(this.getSpeeds(ps));
	}
	//END MIN SPEED
	
	//MIN SPEED NOT ZERO
	@Override
	public BigDecimal minSpeedNotZero(Track t) {
		BigDecimal minSpeed = BigDecimal.ZERO;
		BigDecimal speed = BigDecimal.ONE;
		Iterator<TrackSegment> ts = t.getTrackSegments().iterator();
		if(ts.hasNext()){
			minSpeed = this.minSpeedNotZero(this.getSpeeds(ts.next()));
			while(ts.hasNext()){
				speed = this.minSpeedNotZero(this.getSpeeds(ts.next()));
				if(minSpeed.compareTo(speed)==1)
					minSpeed = speed;
			}
		}
		
		return minSpeed;
	}
	
	@Override
	public BigDecimal minSpeedNotZero(TrackSegment ts) {
		return this.minSpeedNotZero(this.getSpeeds(ts));
	}
	
	@Override
	public BigDecimal minSpeedNotZero(Route r) {
		return this.minSpeedNotZero(this.getSpeeds(r));
	}
	
	@Override
	public BigDecimal minSpeedNotZero(PointsSequence ps) {
		return this.minSpeedNotZero(this.getSpeeds(ps));
	}
	//END MIN SPEED NOT ZERO
	
	//MAX SPEED
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
	public BigDecimal maxSpeed(Route r) {
		return this.maxSpeed(this.getSpeeds(r));
	}
	
	@Override
	public BigDecimal maxSpeed(PointsSequence ps) {
		return this.maxSpeed(this.getSpeeds(ps));
	}
	
	@Override
	public BigDecimal maxSpeed(TrackSegment ts) {
		return this.maxSpeed(this.getSpeeds(ts));
	}
	//END MAX SPEED
	
	//GET SPEEDS
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
	public BigDecimal[] getSpeeds(TrackSegment ts) {
		return this.getSpeeds(ts.getWaypoints().toArray(new CoordinatesObject[ts.getWaypoints().size()]));
	}
	
	@Override
	public BigDecimal[] getSpeeds(Route r) {
		return this.getSpeeds(r.getWaypoints().toArray(new CoordinatesObject[r.getWaypoints().size()]));
	}
	
	@Override
	public BigDecimal[] getSpeeds(PointsSequence ps) {
		return this.getSpeeds(ps.getPoints().toArray(new CoordinatesObject[ps.getPoints().size()]));
	}
	//END GET SPEEDS
	
	//GET SPEED PER SECOND
	@Override
	public BigDecimal[] getSpeedPerSecond(TrackSegment ts) {
		return this.getSpeedPerSecond(ts.getWaypoints().toArray(new CoordinatesObject[ts.getWaypoints().size()]));
	}

	@Override
	public BigDecimal[] getSpeedPerSecond(Route r) {
		return this.getSpeedPerSecond(r.getWaypoints().toArray(new CoordinatesObject[r.getWaypoints().size()]));
	}

	@Override
	public BigDecimal[] getSpeedPerSecond(PointsSequence ps) {
		return this.getSpeedPerSecond(ps.getPoints().toArray(new CoordinatesObject[ps.getPoints().size()]));
	}
	//END GET SPEED PER SECOND
	
	//GET SPEED
	@Override
	public BigDecimal getSpeed(CoordinatesObject c1, CoordinatesObject c2) {
		return this.getSpeed(this.rf.getDistance(c1, c2), Math.abs(c2.getTime().getTimeInMillis()-c1.getTime().getTimeInMillis()));
	}
	//END GET SPEED
}