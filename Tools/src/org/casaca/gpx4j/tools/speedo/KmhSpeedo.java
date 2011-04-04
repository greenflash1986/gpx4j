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

public class KmhSpeedo extends AbstractSpeedo {

	private BigDecimal mile;
	
	public KmhSpeedo() throws GpxSpeedoException, NumberFormatException, GpxPropertiesException {
		super();
		this.mile = BigDecimal.valueOf(Double.parseDouble(GpxTools.getTools().getToolsProperties().getProperty(Constants.TOOLS_CONVERSION_KM_TO_MILE, Constants.APPLICATION_DEFAULT_CONVERSION_KM_TO_MILE)));
	}

	@Override
	public String getUnit() {
		return "Km/h";
	}

	//CONVERTING METHODS
	@Override
	public ISpeed toMtSeg(ISpeed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_KMH)==0) return Speed.SPEED_ZERO_METERS_PER_SECOND;
		
		speed.setSpeed(speed.getSpeed().multiply(BigDecimal.valueOf(1000)).divide(BigDecimal.valueOf(3600), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
		speed.setUnit(MeasurementUnit.MT_SEG);
		
		return speed;
	}

	@Override
	public ISpeed toMph(ISpeed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_KMH)==0) return Speed.SPEED_ZERO_MPH;
		
		speed.setSpeed(speed.getSpeed().multiply(mile));
		speed.setUnit(MeasurementUnit.MPH);
		
		return speed;
	}

	@Override
	public ISpeed toMinKm(ISpeed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_KMH)==0) return Speed.SPEED_ZERO_MINUTES_PER_KILOMETER;
		
		speed.setSpeed(BigDecimal.valueOf(60).divide(speed.getSpeed(), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
		speed.setUnit(MeasurementUnit.MIN_KM);
		
		return speed;
	}

	@Override
	public ISpeed toMinMile(ISpeed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_KMH)==0) return Speed.SPEED_ZERO_MINUTES_PER_MILE;
		
		speed.setSpeed(BigDecimal.valueOf(60).divide(speed.getSpeed().multiply(mile), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
		speed.setUnit(MeasurementUnit.MIN_MI);
		
		return speed;
	}

	@Override
	public ISpeed toKmh(ISpeed speed) {
		return speed;
	}
	//END CONVERTING METHODS
	
	@Override
	public ISpeed getSpeed(CoordinatesObject c1, CoordinatesObject c2) {
		if(c1==null || c2==null) return Speed.SPEED_ZERO_KMH;
		
		ISpeed speed = super.getSpeed(super.getRangefinder().getDistance(c1, c2), Math.abs(c2.getTime().getTimeInMillis()-c1.getTime().getTimeInMillis()));
		speed.setUnit(MeasurementUnit.KMH);
		speed.setSpeed((speed==null || speed.compareTo(Speed.SPEED_ZERO_KMH)==0)?Speed.SPEED_ZERO_KMH.getSpeed():speed.getSpeed().multiply(BigDecimal.valueOf(3.6)));
		
		return speed;
	}
}
