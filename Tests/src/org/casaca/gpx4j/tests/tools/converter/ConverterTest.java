package org.casaca.gpx4j.tests.tools.converter;

import java.math.BigDecimal;

import junit.framework.TestCase;

import org.casaca.gpx4j.tools.GpxTools;
import org.casaca.gpx4j.tools.converter.Converter;
import org.junit.Before;
import org.junit.Test;

public class ConverterTest extends TestCase {

	private Converter converter;
	
	@Before
	public void setUp(){
		this.converter = GpxTools.getTools().getConverter();
	}
	
	@Test
	public void testCoordinatesFromDecimalToSexagesimal(){
		assertEquals("00¼00'00\"N", this.converter.latitudeFromDecimalToSexagesimal(null));
		assertEquals("52¼12'16.00\"N", this.converter.latitudeFromDecimalToSexagesimal(BigDecimal.valueOf(52.20472)));
		assertEquals("00¼00'00\"E", this.converter.longitudeFromDecimalToSexagesimal(null));
		assertEquals("14¼20'28.00\"W", this.converter.longitudeFromDecimalToSexagesimal(BigDecimal.valueOf(-14.3413)));
	}
	
	@Test
	public void testCoordinatesFromSexagesimalToDecimal(){
		assertEquals(BigDecimal.ZERO, this.converter.latitudeFromSexagesimalToDecimal(null));
		assertEquals("52.2044444444444444444", String.valueOf(this.converter.latitudeFromSexagesimalToDecimal("52¼12'16.00\"N")));
		assertEquals(BigDecimal.ZERO, this.converter.longitudeFromSexagesimalToDecimal(null));
		assertEquals("-14.3411111111111110776", String.valueOf(this.converter.longitudeFromSexagesimalToDecimal("14¼20'28.00\"W")));
	}
}
