package org.casaca.gpx4j.tools.util;

import java.math.BigDecimal;
import java.math.MathContext;

public class Constants {

	private Constants(){
		
	}
	
	public static final String APPLICATION_DEFAULT_TOOLS_PROPERTIES_FILENAME = "gpx4jTools_default.properties";
	public static final int APPLICATION_PRECISION_OPERATIONS = 8;
	public static final int APPLICATION_ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;
	public static final MathContext APPLICATION_BIGDECIMAL_MATH_CONTEXT = MathContext.DECIMAL128;
	public static final String APPLICATION_DEFAULT_CONVERSION_MILE_TO_KM = "1.609344";
	public static final String APPLICATION_DEFAULT_CONVERSION_KM_TO_MILE = "0,6213711922";
	
	public static final String TOOLS_RANGEFINDER_CLASS_NAME = "TOOLS_RANGEFINDER_CLASS_NAME";
	public static final String TOOLS_SPEEDO_CLASS_NAME = "TOOLS_SPEEDO_CLASS_NAME";
	public static final String TOOLS_CHRONOMETER_CLASS_NAME = "TOOLS_CHRONOMETER_CLASS_NAME";
	public static final String TOOLS_CONVERSION_MILE_TO_KM = "TOOLS_CONVERSION_MILE_TO_KM";
	public static final String TOOLS_CONVERSION_KM_TO_MILE = "TOOLS_CONVERSION_KM_TO_MILE";
}
