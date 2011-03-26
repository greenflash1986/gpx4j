package org.casaca.gpx4j.tools.chronometer;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Iterator;

import org.casaca.gpx4j.core.data.CoordinatesObject;
import org.casaca.gpx4j.core.data.PointsSequence;
import org.casaca.gpx4j.core.data.Route;
import org.casaca.gpx4j.core.data.Track;
import org.casaca.gpx4j.core.data.TrackSegment;

public abstract class AbstractChronometer implements IChronometer {
	
	//PRIVATE METHODS
	private BigDecimal getDuration(CoordinatesObject[] array){
		BigDecimal duration = BigDecimal.ZERO;
		if(array.length>1)
			for (int i = 0; i < array.length-1; i++)
				duration = duration.add(this.getDuration(array[i], array[i+1]));
		
		return duration;
	}
	//END PRIVATE METHODS

	@Override
	public BigDecimal getDuration(CoordinatesObject c1, CoordinatesObject c2) {
		if(c1==null || c1.getTime()==null || c2==null || c2.getTime()==null) return BigDecimal.ZERO;
		
		return BigDecimal.valueOf(Math.abs(c1.getTime().getTimeInMillis()-c2.getTime().getTimeInMillis()));
	}

	@Override
	public BigDecimal getDuration(Track t) {
		BigDecimal duration = BigDecimal.ZERO;
		Iterator<TrackSegment> i = t.getTrackSegments().iterator();
		while(i.hasNext()){
			duration = duration.add(this.getDuration(i.next()));
		}
		
		return duration;
	}

	@Override
	public BigDecimal getDuration(TrackSegment ts) {
		return this.getDuration(ts.getWaypoints().toArray(new CoordinatesObject[ts.getWaypoints().size()]));
	}

	@Override
	public BigDecimal getDuration(Route r) {
		return this.getDuration(r.getWaypoints().toArray(new CoordinatesObject[r.getWaypoints().size()]));
	}

	@Override
	public BigDecimal getDuration(PointsSequence ps) {
		return this.getDuration(ps.getPoints().toArray(new CoordinatesObject[ps.getPoints().size()]));
	}

	@Override
	public String getDuration(BigDecimal millis) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Calendar starts(Track t) {
		if(t.getTrackSegments().size()>0)
			return this.starts(t.getTrackSegments().get(0));
		
		return null;
	}

	@Override
	public Calendar starts(TrackSegment ts) {
		if(ts.getWaypoints().size()>0)
			return ts.getWaypoints().get(0).getTime();
		
		return null;
	}

	@Override
	public Calendar starts(Route r) {
		if(r.getWaypoints().size()>0)
			return r.getWaypoints().get(0).getTime();
		
		return null;
	}

	@Override
	public Calendar starts(PointsSequence ps) {
		if(ps.getPoints().size()>0)
			return ps.getPoints().get(0).getTime();
		
		return null;
	}

	@Override
	public Calendar ends(Track t) {
		if(t.getTrackSegments().size()>0){
			TrackSegment ts = t.getTrackSegments().get(t.getTrackSegments().size()-1);
			if(ts.getWaypoints().size()>0)
				return ts.getWaypoints().get(ts.getWaypoints().size()-1).getTime();
		}
		
		return null;
	}

	@Override
	public Calendar ends(TrackSegment ts) {
		if(ts.getWaypoints().size()>0)
			return ts.getWaypoints().get(ts.getWaypoints().size()-1).getTime();
		
		return null;
	}

	@Override
	public Calendar ends(Route r) {
		if(r.getWaypoints().size()>0)
			return r.getWaypoints().get(r.getWaypoints().size()-1).getTime();
		
		return null;
	}

	@Override
	public Calendar ends(PointsSequence ps) {
		if(ps.getPoints().size()>0)
			return ps.getPoints().get(ps.getPoints().size()-1).getTime();
		
		return null;
	}
}