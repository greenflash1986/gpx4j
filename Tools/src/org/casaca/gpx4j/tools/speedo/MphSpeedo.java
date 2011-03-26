package org.casaca.gpx4j.tools.speedo;

import java.math.BigDecimal;
import java.util.Properties;

import org.casaca.gpx4j.core.data.CoordinatesObject;
import org.casaca.gpx4j.core.exception.GpxPropertiesException;
import org.casaca.gpx4j.tools.GpxTools;
import org.casaca.gpx4j.tools.exception.GpxSpeedoException;
import org.casaca.gpx4j.tools.util.Constants;

public class MphSpeedo extends AbstractSpeedo {
	BigDecimal mile = null;

	public MphSpeedo() throws GpxSpeedoException, GpxPropertiesException {
		super();
		Properties toolsProp = GpxTools.getTools().getToolsProperties();
		mile = BigDecimal.valueOf(Double.parseDouble(toolsProp.getProperty(Constants.TOOLS_CONVERSION_MILE, Constants.APPLICATION_DEFAULT_CONVERSION_MILE)));
	}

	@Override
	public BigDecimal getSpeed(CoordinatesObject c1, CoordinatesObject c2) {
		return super.getSpeed(c1, c2).multiply(BigDecimal.ONE.divide(mile, Constants.APPLICATION_PRECISION_OPERATIONS, Constants.APPLICATION_ROUNDING_MODE));
	}
}
