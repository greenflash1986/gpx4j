package org.casaca.gpx4j.tools.chronometer;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.casaca.gpx4j.core.data.CoordinatesObject;
import org.casaca.gpx4j.core.data.PointsSequence;
import org.casaca.gpx4j.core.data.Route;
import org.casaca.gpx4j.core.data.Track;
import org.casaca.gpx4j.core.data.TrackSegment;
import org.casaca.gpx4j.tools.util.Constants;

public abstract class AbstractChronometer implements IChronometer {
	
	//PRIVATE METHODS
	public BigDecimal getDuration(List<? extends CoordinatesObject> list){
		return (list!=null && list.size()>1 && list.get(list.size()-1)!=null && list.get(0)!=null)?
				this.getDuration(list.get(list.size()-1), list.get(0))
				:
				BigDecimal.ZERO;
	}
	//END PRIVATE METHODS

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
		return this.getDuration(ts.getWaypoints());
	}

	@Override
	public BigDecimal getDuration(Route r) {
		return this.getDuration(r.getWaypoints());
	}

	@Override
	public BigDecimal getDuration(PointsSequence ps) {
		return this.getDuration(ps.getPoints());
	}

	@Override
	public String getDuration(BigDecimal millis) {
		if(millis.compareTo(BigDecimal.ZERO)==0) return "0 s";
		
		StringBuffer sb = new StringBuffer();
	 	MathContext mc = new MathContext(Constants.APPLICATION_PRECISION_OPERATIONS, RoundingMode.DOWN);
		boolean flag = false;
		BigDecimal millisInDay = BigDecimal.valueOf(86400000);
		BigDecimal millisInHour = BigDecimal.valueOf(3600000);
		BigDecimal millisInMiunte = BigDecimal.valueOf(60000);
		BigDecimal millisInSecond = BigDecimal.valueOf(1000);
		int tmp = millis.divide(millisInDay, mc).shortValue();
		if(tmp>0){ sb.append(tmp).append("d "); flag = true;}
		millis = millis.remainder(millisInDay, mc);
		tmp = millis.divide(millisInHour, mc).shortValue();
		if(flag || tmp>0){ sb.append(tmp).append("h "); flag = true;}
		millis = millis.remainder(millisInHour, mc);
		tmp = millis.divide(millisInMiunte, mc).shortValue();
		if(flag || tmp>0){ sb.append(tmp).append("m "); flag = true;}
		millis = millis.remainder(millisInMiunte, mc);
		tmp = millis.divide(millisInSecond, mc).shortValue();
		if(flag || tmp>0){ sb.append(tmp).append("s "); flag = true;}
		millis = millis.remainder(millisInSecond, mc);
		if(millis.compareTo(BigDecimal.ZERO)==1) sb.append(millis).append(" millis");
		
		return sb.toString();
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