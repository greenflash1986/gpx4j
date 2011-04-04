package org.casaca.gpx4j.tools.speedo;

import java.math.BigDecimal;
import java.util.Properties;

import org.casaca.gpx4j.core.data.CoordinatesObject;
import org.casaca.gpx4j.core.exception.GpxPropertiesException;
import org.casaca.gpx4j.tools.data.ISpeed;
import org.casaca.gpx4j.tools.data.MeasurementUnit;
import org.casaca.gpx4j.tools.data.Speed;
import org.casaca.gpx4j.tools.exception.GpxSpeedoException;
import org.casaca.gpx4j.tools.util.Constants;

public class MtSegSpeedo extends AbstractSpeedo {

	private Properties toolsProp;
	private BigDecimal mile;
	private BigDecimal km;
	
	public MtSegSpeedo() throws GpxSpeedoException, GpxPropertiesException {
		super();
		this.toolsProp = this.getTools().getToolsProperties();
		this.mile = BigDecimal.valueOf(Double.parseDouble(toolsProp.getProperty(Constants.TOOLS_CONVERSION_KM_TO_MILE, Constants.APPLICATION_DEFAULT_CONVERSION_KM_TO_MILE)));
		this.km = BigDecimal.valueOf(Double.parseDouble(toolsProp.getProperty(Constants.TOOLS_CONVERSION_MILE_TO_KM, Constants.APPLICATION_DEFAULT_CONVERSION_MILE_TO_KM)));
	}

	@Override
	public String getUnit() {
		return "m/s";
	}
	//CONVERTING SPEED
	@Override
	public ISpeed toMtSeg(ISpeed speed) {
		return speed;
	}

	@Override
	public ISpeed toMph(ISpeed speed) {		
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_METERS_PER_SECOND)==0) return Speed.SPEED_ZERO_MPH;
		
		speed.setSpeed(speed.getSpeed().multiply(BigDecimal.valueOf(3.6)).multiply(this.mile));
		speed.setUnit(MeasurementUnit.MPH);
		
		return speed;
	}

	@Override
	public ISpeed toMinKm(ISpeed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_METERS_PER_SECOND)==0) return Speed.SPEED_ZERO_MINUTES_PER_KILOMETER;
		
		speed.setSpeed(BigDecimal.valueOf(1000).divide((speed.getSpeed().multiply(BigDecimal.valueOf(60))), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
		speed.setUnit(MeasurementUnit.MT_SEG);
		
		return speed;
	}

	@Override
	public ISpeed toMinMile(ISpeed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_METERS_PER_SECOND)==0) return Speed.SPEED_ZERO_MINUTES_PER_MILE;
		
		speed.setSpeed((BigDecimal.valueOf(1000).multiply(km).divide(speed.getSpeed(), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT)).divide(BigDecimal.valueOf(60), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
		speed.setUnit(MeasurementUnit.MT_SEG);
		
		return speed;
	}

	@Override
	public ISpeed toKmh(ISpeed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_METERS_PER_SECOND)==0) return Speed.SPEED_ZERO_KMH;
		
		speed.setSpeed(speed.getSpeed().multiply(BigDecimal.valueOf(3.6)));
		speed.setUnit(MeasurementUnit.KMH);
		
		return speed;
	}
	//END CONVERTING SPEED
	
	//GET SPEED
	@Override
	public ISpeed getSpeed(CoordinatesObject c1, CoordinatesObject c2) {
		return super.getSpeed(super.getRangefinder().getDistance(c1, c2), Math.abs(c2.getTime().getTimeInMillis()-c1.getTime().getTimeInMillis()));
	}
	//END GET SPEED
}
