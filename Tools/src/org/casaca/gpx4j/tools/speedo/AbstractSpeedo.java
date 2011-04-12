package org.casaca.gpx4j.tools.speedo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.casaca.gpx4j.core.data.CoordinatesObject;
import org.casaca.gpx4j.core.data.PointsSequence;
import org.casaca.gpx4j.core.data.Route;
import org.casaca.gpx4j.core.data.Track;
import org.casaca.gpx4j.core.data.TrackSegment;
import org.casaca.gpx4j.core.exception.GpxPropertiesException;
import org.casaca.gpx4j.tools.GpxTools;
import org.casaca.gpx4j.tools.Tool;
import org.casaca.gpx4j.tools.chronometer.IChronometer;
import org.casaca.gpx4j.tools.chronometer.MillisChronometer;
import org.casaca.gpx4j.tools.converter.Converter;
import org.casaca.gpx4j.tools.data.ISpeed;
import org.casaca.gpx4j.tools.data.MeasurementUnit;
import org.casaca.gpx4j.tools.data.Speed;
import org.casaca.gpx4j.tools.exception.GpxChronometerException;
import org.casaca.gpx4j.tools.exception.GpxRangefinderException;
import org.casaca.gpx4j.tools.exception.GpxSpeedoException;
import org.casaca.gpx4j.tools.rangefinder.IRangefinder;
import org.casaca.gpx4j.tools.util.Constants;

public abstract class AbstractSpeedo extends Tool implements ISpeedo {
	
	private GpxTools tools;
	private IRangefinder rf;
	private IChronometer ch;
	private Converter cv;
	
	public AbstractSpeedo(Properties props) throws GpxSpeedoException{
		super(props);
		
		this.tools = GpxTools.getTools();
		try {
			this.rf = this.tools.getRangefinder();
			this.cv = this.tools.getConverter();
			this.ch = this.tools.createChronometer(MillisChronometer.class);
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
	
	protected ISpeed getSpeed(BigDecimal distance, long time){
		//Distance in meters
		//Time in milliseconds
		if(time==0) return Speed.SPEED_ZERO_METERS_PER_SECOND;
		
		BigDecimal seconds = BigDecimal.valueOf(time).divide(BigDecimal.valueOf(1000.0), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT);
		return new Speed(distance.divide(seconds, Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT), MeasurementUnit.MT_SEG);
	}
	//ENDS PROTECTED METHODS
	
	//PRIVATE METHODS
//    private ISpeed meanSpeed(Iterator<? extends CoordinatesObject> i){
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
	
	private ISpeed meanSpeed(List<? extends CoordinatesObject> list){
		if(list.size()<2) return Speed.SPEED_ZERO_METERS_PER_SECOND;
		
		BigDecimal distance = this.rf.getDistance(list);
		System.out.println(list.get(0).getTime().getTimeInMillis());
		System.out.println(list.get(list.size()-1).getTime().getTimeInMillis());
		BigDecimal time = this.ch.getDuration(list.get(0), list.get(list.size()-1));
		return this.getSpeed(distance, time.longValue());
	}
    
	private ISpeed medianSpeed(List<ISpeed> speeds){
		Collections.sort(speeds);
		int size = speeds.size();
		if(size%2==0){
			return new Speed(speeds.get(size/2).getSpeed().add(speeds.get((size/2)-1).getSpeed()).divide(BigDecimal.valueOf(2.0), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT), MeasurementUnit.MT_SEG);
		}
		else
			return speeds.get(size/2);
	}
	
	private ISpeed minSpeed(List<ISpeed> speeds){
		return Collections.min(speeds);
	}
	
	private ISpeed minSpeedNotZero(List<ISpeed> speeds){
		Collections.sort(speeds);
		Iterator<ISpeed> i = speeds.iterator();
		ISpeed speed;
		
		while(i.hasNext()){
			speed = i.next();
			if(speed.compareTo(Speed.SPEED_ZERO_METERS_PER_SECOND)==1)
				return speed;
		}
		
		return Speed.SPEED_ZERO_METERS_PER_SECOND;
	}
	
	private ISpeed maxSpeed(List<ISpeed> speeds){
		return Collections.max(speeds);
	}
	
	private List<ISpeed> getSpeeds(Iterator<? extends CoordinatesObject> ws){
		List<ISpeed> speeds = new ArrayList<ISpeed>();
		CoordinatesObject w1, w2;
		
		if(ws.hasNext()){
			w1=ws.next();
			while(ws.hasNext()){
				w2=ws.next();
				speeds.add(this.getSpeed(w1, w2));
				w1=w2;
			}
		}
		
		return speeds;
	}
	
	private List<ISpeed> getSpeedPerSecond(List<? extends CoordinatesObject> array){
		List<ISpeed> speeds = new ArrayList<ISpeed>();
		ISpeed speed1 = Speed.SPEED_ZERO_METERS_PER_SECOND;
		ISpeed speed2 = Speed.SPEED_ZERO_METERS_PER_SECOND;
		BigDecimal increment = BigDecimal.ZERO;
		long seconds;
		CoordinatesObject c1, c2;
		Iterator<? extends CoordinatesObject> i = array.iterator();
		
		if(i.hasNext()){
			speeds.add(speed1);
			c1 = i.next();
			
			while(i.hasNext()){
				seconds = Math.abs((c2=i.next()).getTime().getTimeInMillis()-c1.getTime().getTimeInMillis())/1000;
				speed2 = this.getSpeed(c1, c2);
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
	public ISpeed getSpeed(CoordinatesObject c1, CoordinatesObject c2) {
		ISpeed speed = this.getSpeed(this.getRangefinder().getDistance(c1, c2), Math.abs(c2.getTime().getTimeInMillis()-c1.getTime().getTimeInMillis()));
		speed.setCoordinates(c1, c2);
		return speed;
	}
	//END GET SPEED
	
	//MEAN SPEED
	@Override
	public ISpeed meanSpeed(Track t) {
		BigDecimal speed = BigDecimal.ZERO;
		Iterator<TrackSegment> ts = t.getTrackSegments().iterator();
		while(ts.hasNext())
			speed = speed.add(this.meanSpeed(ts.next()).getSpeed());
		
		return new Speed(speed.divide(BigDecimal.valueOf(t.getTrackSegments().size()), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT), MeasurementUnit.MT_SEG);
	}

	@Override
	public ISpeed meanSpeed(TrackSegment ts) {
		if(ts==null) return Speed.SPEED_ZERO_METERS_PER_SECOND;
		
		return this.meanSpeed(ts.getWaypoints());
	}
	
	@Override
	public ISpeed meanSpeed(Route r) {
		if(r==null) return Speed.SPEED_ZERO_METERS_PER_SECOND;
		
		return this.meanSpeed(r.getWaypoints());
	}
	
	@Override
	public ISpeed meanSpeed(PointsSequence ps) {
		if(ps==null) return Speed.SPEED_ZERO_METERS_PER_SECOND;
		
		return this.meanSpeed(ps.getPoints());
	}
	//END MEAN SPEED
	
	//MEDIAN SPEED
	@Override
	public ISpeed medianSpeed(Track t) {
		return this.medianSpeed(this.getSpeeds(t));
	}

	@Override
	public ISpeed medianSpeed(TrackSegment ts) {
		return this.medianSpeed(this.getSpeeds(ts));
	}
	
	@Override
	public ISpeed medianSpeed(Route r) {
		return this.medianSpeed(this.getSpeeds(r));
	}
	
	@Override
	public ISpeed medianSpeed(PointsSequence ps) {
		return this.medianSpeed(this.getSpeeds(ps));
	}
	//END MEDIAN SPEED
	
	//MIN SPEED
	@Override
	public ISpeed minSpeed(Track t) {
		ISpeed minSpeed = null;
		ISpeed speed;
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
	public ISpeed minSpeed(TrackSegment ts) {
		return this.minSpeed(this.getSpeeds(ts));
	}

	@Override
	public ISpeed minSpeed(Route r) {
		return this.minSpeed(this.getSpeeds(r));
	}
	
	@Override
	public ISpeed minSpeed(PointsSequence ps) {
		return this.minSpeed(this.getSpeeds(ps));
	}
	//END MIN SPEED
	
	//MIN SPEED NOT ZERO
	@Override
	public ISpeed minSpeedNotZero(Track t) {
		ISpeed minSpeed = Speed.SPEED_ZERO_METERS_PER_SECOND;
		ISpeed speed = Speed.SPEED_ZERO_METERS_PER_SECOND;
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
	public ISpeed minSpeedNotZero(TrackSegment ts) {
		return this.minSpeedNotZero(this.getSpeeds(ts));
	}
	
	@Override
	public ISpeed minSpeedNotZero(Route r) {
		return this.minSpeedNotZero(this.getSpeeds(r));
	}
	
	@Override
	public ISpeed minSpeedNotZero(PointsSequence ps) {
		return this.minSpeedNotZero(this.getSpeeds(ps));
	}
	//END MIN SPEED NOT ZERO
	
	//MAX SPEED
	@Override
	public ISpeed maxSpeed(Track t) {
		ISpeed maxSpeed = null;
		ISpeed speed;
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
	public ISpeed maxSpeed(Route r) {
		return this.maxSpeed(this.getSpeeds(r));
	}
	
	@Override
	public ISpeed maxSpeed(PointsSequence ps) {
		return this.maxSpeed(this.getSpeeds(ps));
	}
	
	@Override
	public ISpeed maxSpeed(TrackSegment ts) {
		return this.maxSpeed(this.getSpeeds(ts));
	}
	//END MAX SPEED
	
	//GET SPEEDS
	@Override
	public List<ISpeed> getSpeeds(Track t) {
		List<ISpeed> speeds = new ArrayList<ISpeed>();
		Iterator<TrackSegment> ts = t.getTrackSegments().iterator();
		while(ts.hasNext()){
			speeds.addAll(this.getSpeeds(ts.next()));
		}
		
		return speeds;
	}

	@Override
	public List<ISpeed> getSpeeds(TrackSegment ts) {
		return this.getSpeeds(ts.getWaypoints().iterator());
	}
	
	@Override
	public List<ISpeed> getSpeeds(Route r) {
		return this.getSpeeds(r.getWaypoints().iterator());
	}
	
	@Override
	public List<ISpeed> getSpeeds(PointsSequence ps) {
		return this.getSpeeds(ps.getPoints().iterator());
	}
	//END GET SPEEDS
	
	//GET SPEED PER SECOND
	@Override
	public List<ISpeed> getSpeedPerSecond(TrackSegment ts) {
		return this.getSpeedPerSecond(ts.getWaypoints());
	}

	@Override
	public List<ISpeed> getSpeedPerSecond(Route r) {
		return this.getSpeedPerSecond(r.getWaypoints());
	}

	@Override
	public List<ISpeed> getSpeedPerSecond(PointsSequence ps) {
		return this.getSpeedPerSecond(ps.getPoints());
	}
	//END GET SPEED PER SECOND
}