package org.casaca.gpx4j.tests.tools.chronometer;

import junit.framework.TestCase;

import org.casaca.gpx4j.tools.GpxTools;
import org.casaca.gpx4j.tools.chronometer.IChronometer;
import org.casaca.gpx4j.tools.chronometer.MillisChronometer;
import org.casaca.gpx4j.tools.exception.GpxChronometerException;
import org.junit.Before;
import org.junit.Test;

public class MillisChronometerTest extends TestCase {

	private IChronometer chronometer;
	
	@Before
	public void setUp(){
		try {
			this.chronometer = GpxTools.getTools().createChronometer(MillisChronometer.class);
		} catch (GpxChronometerException e) {
			e.printStackTrace();
		}
	}
	
	
}
