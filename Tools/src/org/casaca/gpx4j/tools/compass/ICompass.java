package org.casaca.gpx4j.tools.compass;

import java.math.BigDecimal;

import org.casaca.gpx4j.core.data.CoordinatesObject;
import org.casaca.gpx4j.tools.data.IMeasurementUnit;

public interface ICompass {
	
	public IMeasurementUnit getUnit();
	
	public <T extends CoordinatesObject> BigDecimal getBearing(T c1, T c2);
	
	public CardinalDirection getCardinalDirection(BigDecimal bearing);
}
