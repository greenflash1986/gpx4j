package org.casaca.gpx4j.core.jaxb;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Calendar;

import org.casaca.gpx4j.core.data.Waypoint;
import org.junit.Test;

public class CoordinatesObjectTest {

	@Test
	public void testCompareTo() {
		Waypoint wptNullTime = new Waypoint(new BigDecimal("52.11"), new BigDecimal("13.37"));
		Waypoint wptNonNullTime = new Waypoint(new BigDecimal("52.11"), new BigDecimal("13.37"));
		wptNonNullTime.setTime(Calendar.getInstance());
		
		assertEquals(0, wptNullTime.compareTo(wptNullTime));
		assertEquals(0, wptNonNullTime.compareTo(wptNonNullTime));
		assertEquals(-1, wptNullTime.compareTo(wptNonNullTime));
		assertEquals(1, wptNonNullTime.compareTo(wptNullTime));
	}
}
