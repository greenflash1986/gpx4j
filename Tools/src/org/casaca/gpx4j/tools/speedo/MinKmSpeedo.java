package org.casaca.gpx4j.tools.speedo;

import java.math.BigDecimal;

import org.casaca.gpx4j.core.data.CoordinatesObject;
import org.casaca.gpx4j.tools.exception.GpxSpeedoException;
import org.casaca.gpx4j.tools.util.Constants;

public class MinKmSpeedo extends AbstractSpeedo {

	public MinKmSpeedo() throws GpxSpeedoException {
		super();
	}

	@Override
	public BigDecimal getSpeed(CoordinatesObject c1, CoordinatesObject c2) {
		return BigDecimal.valueOf(60).divide(super.getSpeed(c1, c2), Constants.APPLICATION_PRECISION_OPERATIONS, Constants.APPLICATION_ROUNDING_MODE);
	}
}
