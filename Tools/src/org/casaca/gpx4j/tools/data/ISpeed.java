package org.casaca.gpx4j.tools.data;

import java.math.BigDecimal;

public interface ISpeed extends Comparable<ISpeed> {

	public abstract BigDecimal getSpeed();

	public abstract void setSpeed(BigDecimal speed);

	public abstract IMeasurementUnit getUnit();

	public abstract void setUnit(IMeasurementUnit unit);

}