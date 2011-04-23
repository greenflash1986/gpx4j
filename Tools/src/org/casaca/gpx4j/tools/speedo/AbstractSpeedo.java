package org.casaca.gpx4j.tools.speedo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.casaca.gpx4j.core.data.CoordinatesObject;
import org.casaca.gpx4j.core.data.Extension;
import org.casaca.gpx4j.core.data.IExtensible;
import org.casaca.gpx4j.core.data.PointsSequence;
import org.casaca.gpx4j.core.data.Route;
import org.casaca.gpx4j.core.data.Track;
import org.casaca.gpx4j.core.data.TrackSegment;
import org.casaca.gpx4j.core.exception.GpxPropertiesException;
import org.casaca.gpx4j.tools.GpxTools;
import org.casaca.gpx4j.tools.Tool;
import org.casaca.gpx4j.tools.chronometer.IChronometer;
import org.casaca.gpx4j.tools.chronometer.MillisChronometer;
import org.casaca.gpx4j.tools.data.MeasurementUnit;
import org.casaca.gpx4j.tools.data.Speed;
import org.casaca.gpx4j.tools.exception.GpxChronometerException;
import org.casaca.gpx4j.tools.exception.GpxRangefinderException;
import org.casaca.gpx4j.tools.exception.GpxSpeedoException;
import org.casaca.gpx4j.tools.rangefinder.IRangefinder;
import org.casaca.gpx4j.tools.util.Constants;
import org.casaca.gpx4j.tools.util.Converter;

public abstract class AbstractSpeedo extends Tool implements ISpeedo {
	
	private GpxTools tools;
	private IRangefinder rf;
	private IChronometer ch;
	private Converter cv;
	
	private boolean storeSpeedFlag;
	
	public AbstractSpeedo(Properties props) throws GpxSpeedoException{
		super(props);
		
		this.tools = GpxTools.getTools();
		try {
			this.rf = this.tools.getRangefinder();
			this.cv = this.tools.getConverter();
			this.ch = this.tools.createChronometer(MillisChronometer.class);
			
			storeSpeedFlag = Boolean.parseBoolean(props.getProperty(Constants.TOOLS_SPEEDO_STORE_SPEED, String.valueOf(Constants.APPLICATION_DEFAULT_SPEEDO_STORE_SPEED)));
		} catch (GpxRangefinderException e) {
			throw new GpxSpeedoException(e);
		} catch (GpxPropertiesException e) {
			throw new GpxSpeedoException(e);
		} catch (GpxChronometerException e) {
			throw new GpxSpeedoException(e);
		}
	}
	
	//PROTECTED METHODS
	protected IRangefinder getRangefinder(){
		return this.rf;
	}
	
	protected Converter getConverter(){
		return this.cv;
	}
	
	protected GpxTools getTools(){
		return this.tools;
	}
	
	protected boolean getStoreSpeedFlag(){
		return this.storeSpeedFlag;
	}
	
	protected Speed getSpeed(BigDecimal distance, long time){
		//Distance in meters
		//Time in milliseconds
		if(time==0) return Speed.SPEED_ZERO_METERS_PER_SECOND;
		
		BigDecimal seconds = BigDecimal.valueOf(time).divide(BigDecimal.valueOf(1000.0), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT);
		return new Speed(distance.divide(seconds, Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT), MeasurementUnit.MT_SEG);
	}
	//ENDS PROTECTED METHODS
	
	//PRIVATE METHODS
//    private Speed meanSpeed(Iterator<? extends CoordinatesObject> i){
//            BigDecimal speed = BigDecimal.ZERO;
//            CoordinatesObject c;
//            if(i.hasNext()){
//                    c = i.next();
//                    int count = 0;
//                    while(i.hasNext()){
//                            speed = speed.add(this.getSpeed(c, (c=i.next())).getSpeed());
//                            count++;
//                    }
//                    speed = speed.divide(BigDecimal.valueOf(count), Constants.APPLICATION_PRECISION_OPERATIONS, Constants.APPLICATION_ROUNDING_MODE);
//            }
//            
//            return new Speed(speed, MeasurementUnit.MT_SEG);
//    }
	
	private Speed meanSpeed(List<? extends CoordinatesObject> list){
		if(list.size()<2) return Speed.SPEED_ZERO_METERS_PER_SECOND;
		
		BigDecimal distance = this.rf.getDistance(list);
		System.out.println(list.get(0).getTime().getTimeInMillis());
		System.out.println(list.get(list.size()-1).getTime().getTimeInMillis());
		BigDecimal time = this.ch.getDuration(list.get(0), list.get(list.size()-1));
		return this.getSpeed(distance, time.longValue());
	}
    
	private Speed medianSpeed(List<Speed> speeds){
		Collections.sort(speeds);
		int size = speeds.size();
		if(size%2==0){
			return new Speed(speeds.get(size/2).getSpeed().add(speeds.get((size/2)-1).getSpeed()).divide(BigDecimal.valueOf(2.0), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT), MeasurementUnit.MT_SEG);
		}
		else
			return speeds.get(size/2);
	}
	
	private Speed minSpeed(List<Speed> speeds){
		return Collections.min(speeds);
	}
	
	private Speed minSpeedNotZero(List<Speed> speeds){
		Collections.sort(speeds);
		Iterator<Speed> i = speeds.iterator();
		Speed speed;
		
		while(i.hasNext()){
			speed = i.next();
			if(speed.compareTo(Speed.SPEED_ZERO_METERS_PER_SECOND)==1)
				return speed;
		}
		
		return Speed.SPEED_ZERO_METERS_PER_SECOND;
	}
	
	private Speed maxSpeed(List<Speed> speeds){
		return Collections.max(speeds);
	}
	
	private List<Speed> getSpeeds(Iterator<? extends CoordinatesObject> ws, boolean storeSpeed){
		List<Speed> speeds = new ArrayList<Speed>();
		CoordinatesObject w1, w2;
		
		if(ws.hasNext()){
			w1=ws.next();
			while(ws.hasNext()){
				w2=ws.next();
				speeds.add(this.getSpeed(w1, w2, storeSpeed));
				w1=w2;
			}
		}
		
		return speeds;
	}
	
	private List<Speed> getSpeedPerSecond(List<? extends CoordinatesObject> array, boolean storeSpeed){
		List<Speed> speeds = new ArrayList<Speed>();
		Speed speed1 = Speed.SPEED_ZERO_METERS_PER_SECOND;
		Speed speed2 = Speed.SPEED_ZERO_METERS_PER_SECOND;
		BigDecimal increment = BigDecimal.ZERO;
		long seconds;
		CoordinatesObject c1, c2;
		Iterator<? extends CoordinatesObject> i = array.iterator();
		
		if(i.hasNext()){
			speeds.add(speed1);
			c1 = i.next();
			
			while(i.hasNext()){
				seconds = Math.abs((c2=i.next()).getTime().getTimeInMillis()-c1.getTime().getTimeInMillis())/1000;
				speed2 = this.getSpeed(c1, c2, storeSpeed);
				increment = speed2.getSpeed().subtract(speed1.getSpeed()).divide(BigDecimal.valueOf(seconds), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT);
				for(int j=1;j<seconds;j++){
					speed1.setSpeed(speed1.getSpeed().add(increment.multiply(BigDecimal.valueOf(j))));
					speeds.add(speed1);
				}
				speed1 = speed2;
			}
		}
		
		return speeds;
	}
	//END PRIVATE METHODS
	
	//GET SPEED
	@Override
	public Speed getSpeed(CoordinatesObject c1, CoordinatesObject c2, boolean storeSpeed) {
		Speed speed = this.getSpeed(this.getRangefinder().getDistance(c1, c2), Math.abs(c2.getTime().getTimeInMillis()-c1.getTime().getTimeInMillis()));
		speed.setCoordinates(c1, c2);

		c2.getExtensions().addExtension(new Extension<Speed>(Constants.APPLICATION_TAG_POINT_SPEED, speed));
		return speed;
	}
	//END GET SPEED
	
	//MEAN SPEED
	@Override
	public Speed meanSpeed(Track t) {
		BigDecimal speed = BigDecimal.ZERO;
		Iterator<TrackSegment> ts = t.getTrackSegments().iterator();
		while(ts.hasNext())
			speed = speed.add(this.meanSpeed(ts.next()).getSpeed());
		
		return new Speed(speed.divide(BigDecimal.valueOf(t.getTrackSegments().size()), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT), MeasurementUnit.MT_SEG);
	}

	@Override
	public Speed meanSpeed(TrackSegment ts) {
		if(ts==null) return Speed.SPEED_ZERO_METERS_PER_SECOND;
		
		return this.meanSpeed(ts.getWaypoints());
	}
	
	@Override
	public Speed meanSpeed(Route r) {
		if(r==null) return Speed.SPEED_ZERO_METERS_PER_SECOND;
		
		return this.meanSpeed(r.getWaypoints());
	}
	
	@Override
	public Speed meanSpeed(PointsSequence ps) {
		if(ps==null) return Speed.SPEED_ZERO_METERS_PER_SECOND;
		
		return this.meanSpeed(ps.getPoints());
	}
	//END MEAN SPEED
	
	//MEDIAN SPEED
	@Override
	public Speed medianSpeed(Track t) {
		return this.medianSpeed(this.getSpeeds(t, this.storeSpeedFlag));
	}

	@Override
	public Speed medianSpeed(TrackSegment ts) {
		return this.medianSpeed(this.getSpeeds(ts, this.storeSpeedFlag));
	}
	
	@Override
	public Speed medianSpeed(Route r) {
		return this.medianSpeed(this.getSpeeds(r, this.storeSpeedFlag));
	}
	
	@Override
	public Speed medianSpeed(PointsSequence ps) {
		return this.medianSpeed(this.getSpeeds(ps, this.storeSpeedFlag));
	}
	//END MEDIAN SPEED
	
	//MIN SPEED
	@Override
	public Speed minSpeed(Track t) {
		Speed minSpeed = null;
		Speed speed;
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
	public Speed minSpeed(TrackSegment ts) {
		return this.minSpeed(this.getSpeeds(ts, this.storeSpeedFlag));
	}

	@Override
	public Speed minSpeed(Route r) {
		return this.minSpeed(this.getSpeeds(r, this.storeSpeedFlag));
	}
	
	@Override
	public Speed minSpeed(PointsSequence ps) {
		return this.minSpeed(this.getSpeeds(ps, this.storeSpeedFlag));
	}
	//END MIN SPEED
	
	//MIN SPEED NOT ZERO
	@Override
	public Speed minSpeedNotZero(Track t) {
		Speed minSpeed = Speed.SPEED_ZERO_METERS_PER_SECOND;
		Speed speed = Speed.SPEED_ZERO_METERS_PER_SECOND;
		Iterator<TrackSegment> ts = t.getTrackSegments().iterator();
		if(ts.hasNext()){
			minSpeed = this.minSpeedNotZero(this.getSpeeds(ts.next(), this.storeSpeedFlag));
			while(ts.hasNext()){
				speed = this.minSpeedNotZero(this.getSpeeds(ts.next(), this.storeSpeedFlag));
				if(minSpeed.compareTo(speed)==1)
					minSpeed = speed;
			}
		}
		
		return minSpeed;
	}
	
	@Override
	public Speed minSpeedNotZero(TrackSegment ts) {
		return this.minSpeedNotZero(this.getSpeeds(ts, this.storeSpeedFlag));
	}
	
	@Override
	public Speed minSpeedNotZero(Route r) {
		return this.minSpeedNotZero(this.getSpeeds(r, this.storeSpeedFlag));
	}
	
	@Override
	public Speed minSpeedNotZero(PointsSequence ps) {
		return this.minSpeedNotZero(this.getSpeeds(ps, this.storeSpeedFlag));
	}
	//END MIN SPEED NOT ZERO
	
	//MAX SPEED
	@Override
	public Speed maxSpeed(Track t) {
		Speed maxSpeed = null;
		Speed speed;
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
	public Speed maxSpeed(Route r) {
		return this.maxSpeed(this.getSpeeds(r, this.storeSpeedFlag));
	}
	
	@Override
	public Speed maxSpeed(PointsSequence ps) {
		return this.maxSpeed(this.getSpeeds(ps, this.storeSpeedFlag));
	}
	
	@Override
	public Speed maxSpeed(TrackSegment ts) {
		return this.maxSpeed(this.getSpeeds(ts, this.storeSpeedFlag));
	}
	//END MAX SPEED
	
	//GET SPEEDS
	@Override
	public List<Speed> getSpeeds(Track t, boolean storeSpeed) {
		List<Speed> speeds = new ArrayList<Speed>();
		Iterator<TrackSegment> ts = t.getTrackSegments().iterator();
		while(ts.hasNext()){
			speeds.addAll(this.getSpeeds(ts.next(), storeSpeed));
		}
		
		return speeds;
	}

	@Override
	public List<Speed> getSpeeds(TrackSegment ts, boolean storeSpeed) {
		return this.getSpeeds(ts.getWaypoints().iterator(), storeSpeed);
	}
	
	@Override
	public List<Speed> getSpeeds(Route r, boolean storeSpeed) {
		return this.getSpeeds(r.getWaypoints().iterator(), storeSpeed);
	}
	
	@Override
	public List<Speed> getSpeeds(PointsSequence ps, boolean storeSpeed) {
		return this.getSpeeds(ps.getPoints().iterator(), storeSpeed);
	}
	//END GET SPEEDS
	
	//GET SPEED PER SECOND
	@Override
	public List<Speed> getSpeedPerSecond(TrackSegment ts, boolean storeSpeed) {
		return this.getSpeedPerSecond(ts.getWaypoints(), storeSpeed);
	}

	@Override
	public List<Speed> getSpeedPerSecond(Route r, boolean storeSpeed) {
		return this.getSpeedPerSecond(r.getWaypoints(), storeSpeed);
	}

	@Override
	public List<Speed> getSpeedPerSecond(PointsSequence ps, boolean storeSpeed) {
		return this.getSpeedPerSecond(ps.getPoints(), storeSpeed);
	}
	//END GET SPEED PER SECOND
}