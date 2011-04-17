package org.casaca.gpx4j.tools.data;

import java.util.HashMap;
import java.util.Map;

import org.casaca.gpx4j.core.data.BaseObject;
import org.casaca.gpx4j.core.data.IExtensible;

public class MeasurementUnit extends BaseObject implements IExtensible, Comparable<MeasurementUnit> {
	
	//DISTANCE
	public static final MeasurementUnit METER = new MeasurementUnit("m", "meter");
	public static final MeasurementUnit KILOMETER = new MeasurementUnit("km", "kilometer");
	public static final MeasurementUnit MILE = new MeasurementUnit("mi", "mile");
	
	//TIME
	public static final MeasurementUnit MILLISECOND = new MeasurementUnit("milli", "millisecond");
	public static final MeasurementUnit SECOND = new MeasurementUnit("s", "second");
	public static final MeasurementUnit MINUTE = new MeasurementUnit("min", "minute");
	public static final MeasurementUnit HOUR = new MeasurementUnit("h", "hour");
	
	//SPEED
	public static final MeasurementUnit KMH = new MeasurementUnit("km/h", "kilometers per hour");
	public static final MeasurementUnit MPH = new MeasurementUnit("mi/h", "miles per hour");
	public static final MeasurementUnit MT_SEG = new MeasurementUnit("m/s", "meters per second");
	public static final MeasurementUnit KMS = new MeasurementUnit("km/s", "kilometers per second");
	public static final MeasurementUnit MPS = new MeasurementUnit("mi/s", "miles per second");
	public static final MeasurementUnit MIN_KM = new MeasurementUnit("min/km", "minutes per kilometer");
	public static final MeasurementUnit MIN_MI = new MeasurementUnit("min/mi", "minutes per mile");
	
	//BEARING
	public static final MeasurementUnit DEGREES = new MeasurementUnit("¼", "degrees");
	public static final MeasurementUnit DEGREES_MINUTES = new MeasurementUnit("'", "minutes");
	public static final MeasurementUnit DEGREES_SECONDS = new MeasurementUnit("\"", "seconds");
	
	private String symbol;
	private String unitName;
	
	//IExtensible attributes
	private String name;
	//End IExtensible attributes
	
	//Coded for reflection use
	private MeasurementUnit(){
		super();
		this.symbol = this.unitName = null;
		
		this.name = this.getClass().getSimpleName().substring(0,1).toLowerCase()+this.getClass().getSimpleName().substring(1);
	}
	
	public MeasurementUnit(String symbol, String name){
		this.symbol = symbol;
		this.unitName = name;
		
		this.name = this.getClass().getSimpleName().substring(0,1).toLowerCase()+this.getClass().getSimpleName().substring(1);
	}

	@Override
	public int compareTo(MeasurementUnit o) {
		if(this.symbol==null) return 1;
		
		if(o==null || o.getSymbol()==null)
			return -1;
		
		return this.symbol.compareTo(o.getSymbol());
	}

	public String getSymbol() {
		return this.symbol;
	}

	public String getUnitName() {
		return this.unitName;
	}

	//IExtensible methods
	@Override
	public String getCanonicalClassName() {
		return this.getClass().getCanonicalName();
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Map<String, Object> getFields() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("symbol", this.symbol);
		map.put("unitName", this.unitName);
		
		return map;
	}
	//End IExtensible methods

	@Override
	public String toString() {
		return this.symbol;
	}
}
