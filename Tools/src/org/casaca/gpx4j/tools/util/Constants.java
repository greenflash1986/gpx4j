package org.casaca.gpx4j.tools.util;

import java.math.BigDecimal;

public class Constants {

	private Constants(){
		
	}
	
	public static final String APPLICATION_DEFAULT_TOOLS_PROPERTIES_FILENAME = "gpx4jTools_default.properties";
	public static final int APPLICATION_PRECISION_OPERATIONS = 8;
	public static final int APPLICATION_ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;
	public static final String APPLICATION_DEFAULT_CONVERSION_MILE = "1.609344";
	
	public static final String TOOLS_RANGEFINDER_CLASS_NAME = "TOOLS_RANGEFINDER_CLASS_NAME";
	public static final String TOOLS_SPEEDO_CLASS_NAME = "TOOLS_SPEEDO_CLASS_NAME";
	public static final String TOOLS_CONVERSION_MILE = "TOOLS_CONVERSION_MILE";
}
