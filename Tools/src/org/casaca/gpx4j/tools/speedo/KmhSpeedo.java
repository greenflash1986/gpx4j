package org.casaca.gpx4j.tools.speedo;

import java.math.BigDecimal;
import java.util.Properties;

import org.casaca.gpx4j.core.data.CoordinatesObject;
import org.casaca.gpx4j.core.exception.GpxPropertiesException;
import org.casaca.gpx4j.tools.GpxTools;
import org.casaca.gpx4j.tools.data.MeasurementUnit;
import org.casaca.gpx4j.tools.data.Speed;
import org.casaca.gpx4j.tools.exception.GpxSpeedoException;
import org.casaca.gpx4j.tools.util.Constants;

public class KmhSpeedo extends AbstractSpeedo {

	private BigDecimal mile;
	
	public KmhSpeedo(Properties props) throws GpxSpeedoException, NumberFormatException, GpxPropertiesException {
		super(props);
		this.mile = BigDecimal.valueOf(Double.parseDouble(GpxTools.getTools().getToolsProperties().getProperty(Constants.TOOLS_CONVERSION_KM_TO_MILE, Constants.APPLICATION_DEFAULT_CONVERSION_KM_TO_MILE)));
	}

	@Override
	public MeasurementUnit getUnit() {
		return MeasurementUnit.KMH;
	}

	//CONVERTING METHODS
	@Override
	public Speed toMtSeg(Speed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_KMH)==0) return Speed.SPEED_ZERO_METERS_PER_SECOND;
		
		Speed newSpeed = new Speed(speed.getSpeed().multiply(BigDecimal.valueOf(1000)).divide(BigDecimal.valueOf(3600), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT), MeasurementUnit.MT_SEG);
		newSpeed.setCoordinates(speed.getCoordinates()[0], speed.getCoordinates()[1]);
		
		return newSpeed;
	}

	@Override
	public Speed toMph(Speed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_KMH)==0) return Speed.SPEED_ZERO_MPH;
		
		Speed newSpeed = new Speed(speed.getSpeed().multiply(mile), MeasurementUnit.MPH);
		newSpeed.setCoordinates(speed.getCoordinates()[0], speed.getCoordinates()[1]);
		
		return newSpeed;
	}

	@Override
	public Speed toMinKm(Speed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_KMH)==0) return Speed.SPEED_ZERO_MINUTES_PER_KILOMETER;
		
		Speed newSpeed = new Speed(BigDecimal.valueOf(60).divide(speed.getSpeed(), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT), MeasurementUnit.MIN_KM);
		newSpeed.setCoordinates(speed.getCoordinates()[0], speed.getCoordinates()[1]);
		
		return newSpeed;
	}

	@Override
	public Speed toMinMile(Speed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_KMH)==0) return Speed.SPEED_ZERO_MINUTES_PER_MILE;
		
		Speed newSpeed = new Speed(BigDecimal.valueOf(60).divide(speed.getSpeed().multiply(mile), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT), MeasurementUnit.MIN_MI);
		newSpeed.setCoordinates(speed.getCoordinates()[0], speed.getCoordinates()[1]);
		
		return newSpeed;
	}

	@Override
	public Speed toKmh(Speed speed) {
		return speed;
	}
	//END CONVERTING METHODS
	
	@Override
	public Speed getSpeed(CoordinatesObject c1, CoordinatesObject c2) {
		if(c1==null || c2==null) return Speed.SPEED_ZERO_KMH;
		
		Speed speed = super.getSpeed(c1, c2);
		speed.setUnit(MeasurementUnit.KMH);
		speed.setSpeed((speed==null || speed.compareTo(Speed.SPEED_ZERO_KMH)==0)?Speed.SPEED_ZERO_KMH.getSpeed():speed.getSpeed().multiply(BigDecimal.valueOf(3.6)));
		
		return speed;
	}
}
