package org.casaca.gpx4j.tools.data;

import java.math.BigDecimal;

import org.casaca.gpx4j.core.data.BaseObject;

public class Speed extends BaseObject implements ISpeed{
	
	public static final ISpeed SPEED_ZERO_KMH = new Speed(BigDecimal.ZERO, MeasurementUnit.KMH);
	public static final ISpeed SPEED_ZERO_MPH = new Speed(BigDecimal.ZERO, MeasurementUnit.MPH);
	public static final ISpeed SPEED_ZERO_METERS_PER_SECOND = new Speed(BigDecimal.ZERO, MeasurementUnit.MT_SEG);
	public static final ISpeed SPEED_ZERO_MINUTES_PER_KILOMETER = new Speed(BigDecimal.ZERO, MeasurementUnit.MIN_KM);
	public static final ISpeed SPEED_ZERO_MINUTES_PER_MILE = new Speed(BigDecimal.ZERO, MeasurementUnit.MIN_MI);
	
	private BigDecimal speed;
	private IMeasurementUnit unit;
	
	public Speed(BigDecimal speed, IMeasurementUnit unit) {
		super();
		this.speed = speed;
		this.unit = unit;
	}

	/* (non-Javadoc)
	 * @see org.casaca.gpx4j.tools.data.ISpeed#getSpeed()
	 */
	@Override
	public BigDecimal getSpeed() {
		return speed;
	}

	/* (non-Javadoc)
	 * @see org.casaca.gpx4j.tools.data.ISpeed#setSpeed(java.math.BigDecimal)
	 */
	@Override
	public void setSpeed(BigDecimal speed) {
		if(speed==null) throw new IllegalArgumentException("Null is not an allowed value for speed");
		this.speed = speed;
	}

	/* (non-Javadoc)
	 * @see org.casaca.gpx4j.tools.data.ISpeed#getUnit()
	 */
	@Override
	public IMeasurementUnit getUnit() {
		return unit;
	}

	/* (non-Javadoc)
	 * @see org.casaca.gpx4j.tools.data.ISpeed#setUnit(org.casaca.gpx4j.tools.data.IMeasurementUnit)
	 */
	@Override
	public void setUnit(IMeasurementUnit unit) {
		if(unit==null) throw new IllegalArgumentException("Null is not an allowed value for unit speed");
		this.unit = unit;
	}

	@Override
	public int compareTo(ISpeed o) {
		if(this.speed == null)
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
		result = prime * result + ((speed == null) ? 0 : speed.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
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
		if (speed == null) {
			if (other.speed != null)
				return false;
		} else if (!speed.equals(other.speed))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		return true;
	}
}
