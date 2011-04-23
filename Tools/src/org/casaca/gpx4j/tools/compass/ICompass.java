package org.casaca.gpx4j.tools.compass;

import java.math.BigDecimal;

import org.casaca.gpx4j.core.data.CoordinatesObject;
import org.casaca.gpx4j.tools.data.Bearing;
import org.casaca.gpx4j.tools.data.CardinalDirection;
import org.casaca.gpx4j.tools.data.MeasurementUnit;

public interface ICompass {
	
	public MeasurementUnit getUnit();
	
	public <T extends CoordinatesObject> Bearing getBearing(T c1, T c2, boolean storeBearing);
	
	public CardinalDirection getCardinalDirection(BigDecimal bearing);
}
