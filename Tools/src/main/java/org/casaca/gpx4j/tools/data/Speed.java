package org.casaca.gpx4j.tools.data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.casaca.gpx4j.core.data.BaseObject;
import org.casaca.gpx4j.core.data.CoordinatesObject;
import org.casaca.gpx4j.core.data.IExtensible;

public class Speed extends BaseObject implements IExtensible, Comparable<Speed>{
	
	public static final Speed SPEED_ZERO_KMH = new Speed(BigDecimal.ZERO, MeasurementUnit.KMH);
	public static final Speed SPEED_ZERO_MPH = new Speed(BigDecimal.ZERO, MeasurementUnit.MPH);
	public static final Speed SPEED_ZERO_METERS_PER_SECOND = new Speed(BigDecimal.ZERO, MeasurementUnit.MT_SEG);
	public static final Speed SPEED_ZERO_MINUTES_PER_KILOMETER = new Speed(BigDecimal.ZERO, MeasurementUnit.MIN_KM);
	public static final Speed SPEED_ZERO_MINUTES_PER_MILE = new Speed(BigDecimal.ZERO, MeasurementUnit.MIN_MI);
	
	private BigDecimal value;
	private MeasurementUnit measurementUnit;
	private CoordinatesObject[] coordinates;
	
	//IExtensible attributes
	private String name;
	//End IExtensible attributes
	
	//Coded for reflection use
	@SuppressWarnings("unused")
	private Speed(){
		super();
		
		this.value = BigDecimal.ZERO;
		this.measurementUnit = null;
		this.coordinates = new CoordinatesObject[2];
		
		this.name = this.getClass().getSimpleName().substring(0,1).toLowerCase()+this.getClass().getSimpleName().substring(1);
	}
	
	public Speed(BigDecimal speed, MeasurementUnit unit) {
		super();
		this.value = speed;
		this.measurementUnit = unit;
		this.coordinates = new CoordinatesObject[2];
		
		this.name = this.getClass().getSimpleName().substring(0,1).toLowerCase()+this.getClass().getSimpleName().substring(1);
	}

	public BigDecimal getSpeed() {
		return value;
	}

	public void setSpeed(BigDecimal speed) {
		if(speed==null) throw new IllegalArgumentException("Null is not an allowed value for speed");
		this.value = speed;
	}

	public MeasurementUnit getUnit() {
		return measurementUnit;
	}

	public void setUnit(MeasurementUnit unit) {
		if(unit==null) throw new IllegalArgumentException("Null is not an allowed value for unit speed");
		this.measurementUnit = unit;
	}

	public CoordinatesObject[] getCoordinates() {
		return this.coordinates;
	}

	public void setCoordinates(CoordinatesObject c1, CoordinatesObject c2) {
		if(c1==null && c2!=null){
			this.coordinates[0] = null;
			this.coordinates[1] = c2;
		}
		else if(c1==null && c2==null)
			this.coordinates[0]=this.coordinates[1]=null;
		else if(c1!=null && c2==null){
			this.coordinates[0] = null;
			this.coordinates[1] = c1;
		}
		else{
			this.coordinates[0] = (c1.compareTo(c2)<=0)?c1:c2;
			this.coordinates[1] = (c1.compareTo(c2)>0)?c1:c2;
		}
	}

	@Override
	public int compareTo(Speed o) {
		if(this.value == null)
			return 1;
		else
			if(o==null || o.getSpeed()==null)
				return -1;
			else
				return this.getSpeed().compareTo(o.getSpeed());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + ((measurementUnit == null) ? 0 : measurementUnit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Speed other = (Speed) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		if (measurementUnit == null) {
			if (other.measurementUnit != null)
				return false;
		} else if (!measurementUnit.equals(other.measurementUnit))
			return false;
		return true;
	}

	//IExtensible methods
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getCanonicalClassName() {
		return this.getClass().getCanonicalName();
	}

	@Override
	public Map<String, Object> getFields() {
		Map<String, Object> map = new HashMap<String, Object>();
		//KEY IS THE SAME OF ATTRIBUTE NAME BECAUSE IS USED IN REFLECTION
		map.put("speed", this.value);
		this.measurementUnit.setName("measurementUnit");
		map.put("measurementUnit", this.measurementUnit);
		
		return map;
	}
	//End IExtensible methods

	@Override
	public String toString() {
		return this.getSpeed().toString()+" "+this.getUnit();
	}
}
