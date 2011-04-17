package org.casaca.gpx4j.tools.compass;

import java.math.BigDecimal;

import org.casaca.gpx4j.core.data.CoordinatesObject;
import org.casaca.gpx4j.tools.data.CardinalDirection;
import org.casaca.gpx4j.tools.data.MeasurementUnit;

public interface ICompass {
	
	public MeasurementUnit getUnit();
	
	public <T extends CoordinatesObject> BigDecimal getBearing(T c1, T c2);
	
	public CardinalDirection getCardinalDirection(BigDecimal bearing);
}
