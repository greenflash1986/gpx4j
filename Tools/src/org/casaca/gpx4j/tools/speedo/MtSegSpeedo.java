package org.casaca.gpx4j.tools.speedo;

import java.math.BigDecimal;
import java.util.Properties;

import org.casaca.gpx4j.core.exception.GpxPropertiesException;
import org.casaca.gpx4j.tools.data.IMeasurementUnit;
import org.casaca.gpx4j.tools.data.ISpeed;
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
	public IMeasurementUnit getUnit() {
		return MeasurementUnit.MT_SEG;
	}
	//CONVERTING SPEED
	@Override
	public ISpeed toMtSeg(ISpeed speed) {
		return speed;
	}

	@Override
	public ISpeed toMph(ISpeed speed) {		
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_METERS_PER_SECOND)==0) return Speed.SPEED_ZERO_MPH;
		
		ISpeed newSpeed = new Speed(speed.getSpeed().multiply(BigDecimal.valueOf(3.6)).multiply(this.mile), MeasurementUnit.MPH);
		newSpeed.setCoordinates(speed.getCoordinates()[0], speed.getCoordinates()[1]);
		
		return newSpeed;
	}

	@Override
	public ISpeed toMinKm(ISpeed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_METERS_PER_SECOND)==0) return Speed.SPEED_ZERO_MINUTES_PER_KILOMETER;
		
		ISpeed newSpeed = new Speed(BigDecimal.valueOf(1000).divide((speed.getSpeed().multiply(BigDecimal.valueOf(60))), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT), MeasurementUnit.MIN_KM);
		newSpeed.setCoordinates(speed.getCoordinates()[0], speed.getCoordinates()[1]);
		
		return newSpeed;
	}

	@Override
	public ISpeed toMinMile(ISpeed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_METERS_PER_SECOND)==0) return Speed.SPEED_ZERO_MINUTES_PER_MILE;
		
		ISpeed newSpeed = new Speed((BigDecimal.valueOf(1000).multiply(km).divide(speed.getSpeed(), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT)).divide(BigDecimal.valueOf(60), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT), MeasurementUnit.MIN_MI);
		newSpeed.setCoordinates(speed.getCoordinates()[0], speed.getCoordinates()[1]);
		
		return newSpeed;
	}

	@Override
	public ISpeed toKmh(ISpeed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_METERS_PER_SECOND)==0) return Speed.SPEED_ZERO_KMH;
		
		ISpeed newSpeed = new Speed(speed.getSpeed().multiply(BigDecimal.valueOf(3.6)), MeasurementUnit.KMH);
		newSpeed.setCoordinates(speed.getCoordinates()[0], speed.getCoordinates()[1]);
		
		return newSpeed;
	}
	//END CONVERTING SPEED
}
