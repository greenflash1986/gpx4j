package org.casaca.gpx4j.tools.data;

import java.math.BigDecimal;

import org.casaca.gpx4j.core.data.CoordinatesObject;

public interface ISpeed extends Comparable<ISpeed> {

	public abstract BigDecimal getSpeed();

	public abstract void setSpeed(BigDecimal speed);

	public abstract IMeasurementUnit getUnit();

	public abstract void setUnit(IMeasurementUnit unit);

	public abstract CoordinatesObject[] getCoordinates();
	
	public abstract void setCoordinates(CoordinatesObject c1, CoordinatesObject c2);
}