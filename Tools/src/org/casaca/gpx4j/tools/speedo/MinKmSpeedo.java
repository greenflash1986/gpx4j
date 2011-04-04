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

public class MinKmSpeedo extends AbstractSpeedo {
	
	private BigDecimal mile;

	public MinKmSpeedo() throws GpxSpeedoException, GpxPropertiesException {
		super();
		this.mile = BigDecimal.valueOf(Double.parseDouble(GpxTools.getTools().getToolsProperties().getProperty(Constants.TOOLS_CONVERSION_MILE_TO_KM, Constants.APPLICATION_DEFAULT_CONVERSION_MILE_TO_KM)));
	}

	@Override
	public String getUnit() {
		return "min/Km";
	}
	
	//CONVERTING METHODS
	@Override
	public ISpeed toMtSeg(ISpeed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_MINUTES_PER_KILOMETER)==0) return Speed.SPEED_ZERO_METERS_PER_SECOND;
		
		speed.setSpeed(BigDecimal.valueOf(1000).divide(speed.getSpeed().multiply(BigDecimal.valueOf(60)), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
		speed.setUnit(MeasurementUnit.MIN_KM);
		
		return speed;
	}

	@Override
	public ISpeed toMph(ISpeed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_MINUTES_PER_KILOMETER)==0) return Speed.SPEED_ZERO_MPH;
		
		speed.setSpeed(BigDecimal.valueOf(60).divide(speed.getSpeed().multiply(mile), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
		speed.setUnit(MeasurementUnit.MIN_KM);
		
		return speed;
		
	}

	@Override
	public ISpeed toMinKm(ISpeed speed) {
		return speed;
	}

	@Override
	public ISpeed toMinMile(ISpeed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_MINUTES_PER_KILOMETER)==0) return Speed.SPEED_ZERO_MINUTES_PER_MILE;
		
		speed.setSpeed(speed.getSpeed().multiply(mile));
		speed.setUnit(MeasurementUnit.MIN_KM);
		
		return speed;
	}

	@Override
	public ISpeed toKmh(ISpeed speed) {
		if(speed==null || speed.compareTo(Speed.SPEED_ZERO_MINUTES_PER_KILOMETER)==0) return Speed.SPEED_ZERO_KMH;
		
		speed.setSpeed(BigDecimal.valueOf(60).divide(speed.getSpeed(), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
		speed.setUnit(MeasurementUnit.KMH);
		
		return speed;
	}
	//END CONVERTING METHODS

	@Override
	public ISpeed getSpeed(CoordinatesObject c1, CoordinatesObject c2) {
		if(c1==null || c2==null) return Speed.SPEED_ZERO_MINUTES_PER_KILOMETER;
		
		ISpeed speed = super.getSpeed(super.getRangefinder().getDistance(c1, c2), Math.abs(c2.getTime().getTimeInMillis()-c1.getTime().getTimeInMillis()));
		speed.setSpeed((speed==null || speed.compareTo(Speed.SPEED_ZERO_MINUTES_PER_KILOMETER)==0)?Speed.SPEED_ZERO_MINUTES_PER_KILOMETER.getSpeed():BigDecimal.valueOf(1000).divide((speed.getSpeed().multiply(BigDecimal.valueOf(60))), Constants.APPLICATION_BIGDECIMAL_MATH_CONTEXT));
		speed.setUnit(MeasurementUnit.MIN_KM);
		
		return speed;
	}
}
