package org.casaca.gpx4j.tools.data;

import org.casaca.gpx4j.core.data.BaseObject;

public class MeasurementUnit extends BaseObject implements IMeasurementUnit {
	
	//DISTANCE
	public static final IMeasurementUnit METER = new MeasurementUnit("m", "meter");
	public static final IMeasurementUnit KILOMETER = new MeasurementUnit("km", "kilometer");
	public static final IMeasurementUnit MILE = new MeasurementUnit("mi", "mile");
	
	//TIME
	public static final IMeasurementUnit MILLISECOND = new MeasurementUnit("milli", "millisecond");
	public static final IMeasurementUnit SECOND = new MeasurementUnit("s", "second");
	public static final IMeasurementUnit MINUTE = new MeasurementUnit("min", "minute");
	public static final IMeasurementUnit HOUR = new MeasurementUnit("h", "hour");
	
	//SPEED
	public static final IMeasurementUnit KMH = new MeasurementUnit("km/h", "kilometers per hour");
	public static final IMeasurementUnit MPH = new MeasurementUnit("mi/h", "miles per hour");
	public static final IMeasurementUnit MT_SEG = new MeasurementUnit("m/s", "meters per second");
	public static final IMeasurementUnit KMS = new MeasurementUnit("km/s", "kilometers per second");
	public static final IMeasurementUnit MPS = new MeasurementUnit("mi/s", "miles per second");
	public static final IMeasurementUnit MIN_KM = new MeasurementUnit("min/km", "minutes per kilometer");
	public static final IMeasurementUnit MIN_MI = new MeasurementUnit("min/mi", "minutes per mile");
	
	//BEARING
	public static final IMeasurementUnit DEGREES = new MeasurementUnit("¼", "degrees");
	public static final IMeasurementUnit DEGREES_MINUTES = new MeasurementUnit("'", "minutes");
	public static final IMeasurementUnit DEGREES_SECONDS = new MeasurementUnit("\"", "seconds");
	
	private String symbol;
	private String name;
	
	public MeasurementUnit(String symbol, String name){
		this.symbol = symbol;
		this.name = name;
	}

	@Override
	public int compareTo(IMeasurementUnit o) {
		if(this.symbol==null) return 1;
		
		if(o==null || o.getSymbol()==null)
			return -1;
		
		return this.symbol.compareTo(o.getSymbol());
	}

	@Override
	public String getSymbol() {
		return this.symbol;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return this.symbol;
	}
}
