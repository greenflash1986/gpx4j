package org.casaca.gpx4j.tools.util;

import java.math.MathContext;
import java.math.RoundingMode;

import org.casaca.gpx4j.tools.chronometer.MillisChronometer;
import org.casaca.gpx4j.tools.compass.Compass;
import org.casaca.gpx4j.tools.exporter.csv.CSVExporter;
import org.casaca.gpx4j.tools.rangefinder.SphericalRangefinder;
import org.casaca.gpx4j.tools.speedo.MtSegSpeedo;

public class Constants {

	private Constants(){
		
	}
	
	public static final String APPLICATION_DEFAULT_TOOLS_PROPERTIES_FILENAME = "gpx4jTools_default.properties";
	public static final int APPLICATION_DEFAULT_PRECISION_OPERATIONS = 8;
	public static final RoundingMode APPLICATION_DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;
	public static final MathContext APPLICATION_BIGDECIMAL_MATH_CONTEXT = MathContext.DECIMAL128;
	public static final String APPLICATION_DEFAULT_CONVERSION_MILE_TO_KM = "1.609344";
	public static final String APPLICATION_DEFAULT_CONVERSION_KM_TO_MILE = "0,6213711922";
	public static final String APPLICATION_DEFAULT_LOCALE = "DEFAULT";
	public static final String APPLICATION_DEFAULT_CHRONOMETER_CLASS_NAME = MillisChronometer.class.getCanonicalName();
	public static final String APPLICATION_DEFAULT_EXPORTER_CLASS_NAME = CSVExporter.class.getCanonicalName();
	public static final String APPLICATION_DEFAULT_RANGEFINDER_CLASS_NAME = SphericalRangefinder.class.getCanonicalName();
	public static final String APPLICATION_DEFAULT_SPEEDO_CLASS_NAME = MtSegSpeedo.class.getCanonicalName();
	public static final String APPLICATION_DEFAULT_COMPASS_CLASS_NAME = Compass.class.getCanonicalName();
	
	public static final String TOOLS_RANGEFINDER_CLASS_NAME = "TOOLS_RANGEFINDER_CLASS_NAME";
	public static final String TOOLS_SPEEDO_CLASS_NAME = "TOOLS_SPEEDO_CLASS_NAME";
	public static final String TOOLS_CHRONOMETER_CLASS_NAME = "TOOLS_CHRONOMETER_CLASS_NAME";
	public static final String TOOLS_EXPORTER_CLASS_NAME = "TOOLS_EXPORTER_CLASS_NAME";
	public static final String TOOLS_COMPASS_CLASS_NAME = "TOOLS_COMPASS_CLASS_NAME";
	public static final String TOOLS_CONVERSION_MILE_TO_KM = "TOOLS_CONVERSION_MILE_TO_KM";
	public static final String TOOLS_CONVERSION_KM_TO_MILE = "TOOLS_CONVERSION_KM_TO_MILE";
	public static final String TOOLS_PRECISION_OPERATORS = "TOOLS_PRECISION_OPERATORS";
	public static final String TOOLS_ROUNDING_MODE = "TOOLS_ROUNDING_MODE";
	public static final String TOOLS_LOCALE = "TOOLS_LOCALE";
}
