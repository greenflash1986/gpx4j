package org.casaca.gpx4j.tools.speedo;

import java.math.BigDecimal;
import java.util.Properties;

import org.casaca.gpx4j.core.data.CoordinatesObject;
import org.casaca.gpx4j.core.exception.GpxPropertiesException;
import org.casaca.gpx4j.tools.GpxTools;
import org.casaca.gpx4j.tools.data.ISpeed;
import org.casaca.gpx4j.tools.data.MeasurementUnit;
import org.casaca.gpx4j.tools.data.Speed;
import org.casaca.gpx4j.tools.exception.GpxSpeedoException;
import org.casaca.gpx4j.tools.util.Constants;

public class MphSpeedo extends AbstractSpeedo {
	
	private BigDecimal km;
	private BigDecimal mile;

	public MphSpeedo(Properties props) throws GpxSpeedoException, GpxPropertiesException {
		super(props);
		this.km = BigDecimal.valueOf(Double.parseDouble(GpxTools.getTools().getToolsProperties().getProperty(Constants.TOOLS_CONVERSION_MILE_TO_KM, Constants.APPLICATION_DEFAULT_CONVERSION_MILE_TO_KM)));
		this.mile = BigDecimal.valueOf(Double.parseDouble(GpxTools.getTools().getToolsProperties().getProperty(Constants.TOOLS_CONVERSION_KM_TO_MILE, Constants.APPLICATION_DEFAULT_CONVERSION_KM_TO_MILE)));
	}
	
	@Override
	public String getUnit() {
		return "Mph";
	}

	//CONVERTING METHODS
	@Override
	public ISpeed toMtSeg(ISpeed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_MPH)==0) return Speed.SPEED_ZERO_METERS_PER_SECOND;
		
		speed.setSpeed(speed.getSpeed().multiply(km).divide(BigDecimal.valueOf(3.6), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
		speed.setUnit(MeasurementUnit.MT_SEG);
		
		return speed;
	}

	@Override
	public ISpeed toMph(ISpeed speed) {
		return speed;
	}

	@Override
	public ISpeed toMinKm(ISpeed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_MPH)==0) return Speed.SPEED_ZERO_MINUTES_PER_KILOMETER;
		
		speed.setSpeed(BigDecimal.valueOf(60).divide((speed.getSpeed().multiply(km)), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
		speed.setUnit(MeasurementUnit.MIN_KM);
		
		return speed;
	}

	@Override
	public ISpeed toMinMile(ISpeed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_MPH)==0) return Speed.SPEED_ZERO_MINUTES_PER_MILE;
		
		speed.setSpeed(BigDecimal.valueOf(60).divide(speed.getSpeed(), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
		speed.setUnit(MeasurementUnit.MIN_MI);
		
		return speed;
	}

	@Override
	public ISpeed toKmh(ISpeed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_MPH)==0) return Speed.SPEED_ZERO_KMH;
		
		speed.setSpeed(speed.getSpeed().multiply(km));
		speed.setUnit(MeasurementUnit.MPH);
		
		return speed;
	}
	//END CONVERTING METHODS

	@Override
	public ISpeed getSpeed(CoordinatesObject c1, CoordinatesObject c2) {
		if(c1==null || c2==null) return Speed.SPEED_ZERO_MPH;
		
		ISpeed speed = super.getSpeed(super.getRangefinder().getDistance(c1, c2), Math.abs(c2.getTime().getTimeInMillis()-c1.getTime().getTimeInMillis()));
		speed.setSpeed((speed==null || speed.compareTo(Speed.SPEED_ZERO_MPH)==0)?Speed.SPEED_ZERO_MPH.getSpeed():speed.getSpeed().multiply(BigDecimal.valueOf(3.6)).multiply(this.mile));
		speed.setUnit(MeasurementUnit.MPH);
		
		return speed;
	}
}
