package org.casaca.gpx4j.tools.speedo;

import java.math.BigDecimal;

import org.casaca.gpx4j.core.data.CoordinatesObject;
import org.casaca.gpx4j.core.exception.GpxPropertiesException;
import org.casaca.gpx4j.tools.GpxTools;
import org.casaca.gpx4j.tools.data.ISpeed;
import org.casaca.gpx4j.tools.data.MeasurementUnit;
import org.casaca.gpx4j.tools.data.Speed;
import org.casaca.gpx4j.tools.exception.GpxSpeedoException;
import org.casaca.gpx4j.tools.util.Constants;

public class MinMileSpeedo extends AbstractSpeedo {
	
	private BigDecimal km;
	private BigDecimal mile;
	
	public MinMileSpeedo() throws GpxSpeedoException, GpxPropertiesException {
		super();
		this.km = BigDecimal.valueOf(Double.parseDouble(GpxTools.getTools().getToolsProperties().getProperty(Constants.TOOLS_CONVERSION_KM_TO_MILE, Constants.APPLICATION_DEFAULT_CONVERSION_KM_TO_MILE)));
		this.mile = BigDecimal.valueOf(Double.parseDouble(GpxTools.getTools().getToolsProperties().getProperty(Constants.TOOLS_CONVERSION_MILE_TO_KM, Constants.APPLICATION_DEFAULT_CONVERSION_MILE_TO_KM)));
	}

	@Override
	public String getUnit() {
		return "min/Mile";
	}

	@Override
	public ISpeed toMtSeg(ISpeed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_MINUTES_PER_MILE)==0) return Speed.SPEED_ZERO_METERS_PER_SECOND;
		
		speed.setSpeed(mile.multiply(BigDecimal.valueOf(1000)).divide(speed.getSpeed().multiply(BigDecimal.valueOf(60)), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
		speed.setUnit(MeasurementUnit.MIN_MI);
		
		return speed;
	}

	@Override
	public ISpeed toMph(ISpeed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_MINUTES_PER_MILE)==0) return Speed.SPEED_ZERO_MPH;
		
		speed.setSpeed(BigDecimal.valueOf(60).divide(speed.getSpeed(), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
		speed.setUnit(MeasurementUnit.MPH);
		
		return speed;
	}

	@Override
	public ISpeed toMinKm(ISpeed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_MINUTES_PER_MILE)==0) return Speed.SPEED_ZERO_MINUTES_PER_KILOMETER;
		
		speed.setSpeed(speed.getSpeed().divide(mile, Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
		speed.setUnit(MeasurementUnit.MIN_MI);
		
		return speed;
	}

	@Override
	public ISpeed toMinMile(ISpeed speed) {
		return speed;
	}

	@Override
	public ISpeed toKmh(ISpeed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_MINUTES_PER_MILE)==0) return Speed.SPEED_ZERO_KMH;
		
		speed.setSpeed(BigDecimal.valueOf(60).multiply(mile).divide(speed.getSpeed(), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
		speed.setUnit(MeasurementUnit.KMH);
		
		return speed;
	}

	@Override
	public ISpeed getSpeed(CoordinatesObject c1, CoordinatesObject c2) {
		if(c1==null || c2==null) return Speed.SPEED_ZERO_MINUTES_PER_MILE;
		
		ISpeed speed = super.getSpeed(super.getRangefinder().getDistance(c1, c2), Math.abs(c2.getTime().getTimeInMillis()-c1.getTime().getTimeInMillis()));
		speed.setSpeed((speed==null || speed.compareTo(Speed.SPEED_ZERO_METERS_PER_SECOND)==0)?Speed.SPEED_ZERO_MINUTES_PER_MILE.getSpeed():BigDecimal.valueOf(1000).multiply(km).divide((speed.getSpeed().multiply(BigDecimal.valueOf(60))), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
		speed.setUnit(MeasurementUnit.MIN_MI);
		
		return speed;
	}
}