package org.casaca.gpx4j.tools.speedo;

import java.math.BigDecimal;
import java.util.Properties;

import org.casaca.gpx4j.core.exception.GpxPropertiesException;
import org.casaca.gpx4j.tools.data.MeasurementUnit;
import org.casaca.gpx4j.tools.data.Speed;
import org.casaca.gpx4j.tools.exception.GpxSpeedoException;
import org.casaca.gpx4j.tools.util.Constants;

public class MtSegSpeedo extends AbstractSpeedo {

	private Properties toolsProp;
	private BigDecimal mile;
	private BigDecimal km;
	
	public MtSegSpeedo(Properties props) throws GpxSpeedoException, GpxPropertiesException {
		super(props);
		this.toolsProp = this.getTools().getToolsProperties();
		this.mile = BigDecimal.valueOf(Double.parseDouble(toolsProp.getProperty(Constants.TOOLS_CONVERSION_KM_TO_MILE, Constants.APPLICATION_DEFAULT_CONVERSION_KM_TO_MILE)));
		this.km = BigDecimal.valueOf(Double.parseDouble(toolsProp.getProperty(Constants.TOOLS_CONVERSION_MILE_TO_KM, Constants.APPLICATION_DEFAULT_CONVERSION_MILE_TO_KM)));
	}

	@Override
	public MeasurementUnit getUnit() {
		return MeasurementUnit.MT_SEG;
	}
	//CONVERTING SPEED
	@Override
	public Speed toMtSeg(Speed speed) {
		return speed;
	}

	@Override
	public Speed toMph(Speed speed) {		
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_METERS_PER_SECOND)==0) return Speed.SPEED_ZERO_MPH;
		
		Speed newSpeed = new Speed(speed.getSpeed().multiply(BigDecimal.valueOf(3.6)).multiply(this.mile), MeasurementUnit.MPH);
		newSpeed.setCoordinates(speed.getCoordinates()[0], speed.getCoordinates()[1]);
		
		return newSpeed;
	}

	@Override
	public Speed toMinKm(Speed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_METERS_PER_SECOND)==0) return Speed.SPEED_ZERO_MINUTES_PER_KILOMETER;
		
		Speed newSpeed = new Speed(BigDecimal.valueOf(1000).divide((speed.getSpeed().multiply(BigDecimal.valueOf(60))), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT), MeasurementUnit.MIN_KM);
		newSpeed.setCoordinates(speed.getCoordinates()[0], speed.getCoordinates()[1]);
		
		return newSpeed;
	}

	@Override
	public Speed toMinMile(Speed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_METERS_PER_SECOND)==0) return Speed.SPEED_ZERO_MINUTES_PER_MILE;
		
		Speed newSpeed = new Speed((BigDecimal.valueOf(1000).multiply(km).divide(speed.getSpeed(), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT)).divide(BigDecimal.valueOf(60), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT), MeasurementUnit.MIN_MI);
		newSpeed.setCoordinates(speed.getCoordinates()[0], speed.getCoordinates()[1]);
		
		return newSpeed;
	}

	@Override
	public Speed toKmh(Speed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_METERS_PER_SECOND)==0) return Speed.SPEED_ZERO_KMH;
		
		Speed newSpeed = new Speed(speed.getSpeed().multiply(BigDecimal.valueOf(3.6)), MeasurementUnit.KMH);
		newSpeed.setCoordinates(speed.getCoordinates()[0], speed.getCoordinates()[1]);
		
		return newSpeed;
	}
	//END CONVERTING SPEED
}
