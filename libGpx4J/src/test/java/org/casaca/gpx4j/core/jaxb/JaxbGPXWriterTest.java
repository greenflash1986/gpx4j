package org.casaca.gpx4j.core.jaxb;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.casaca.gpx4j.core.data.GpxDocument;
import org.casaca.gpx4j.core.data.Waypoint;
import org.casaca.gpx4j.core.driver.GpxDriver;
import org.casaca.gpx4j.core.driver.IGpxWriter;
import org.casaca.gpx4j.core.exception.GpxFileNotFoundException;
import org.casaca.gpx4j.core.exception.GpxIOException;
import org.casaca.gpx4j.core.exception.GpxPropertiesException;
import org.casaca.gpx4j.core.exception.GpxWriterException;
import org.junit.Test;

public class JaxbGPXWriterTest {
	
	@Test
	public void writeOutAndCheckNameSpace() throws GpxPropertiesException, GpxWriterException, IOException {
		GpxDriver driver = GpxDriver.getGpxDriver();
		driver.loadDefaultDriverProperties();
		GpxDocument doc = new GpxDocument();
		List<Waypoint> wpts = doc.getWaypoints();
		wpts.add(new Waypoint(new BigDecimal("13.34"), new BigDecimal("54.32")));
		
		IGpxWriter writer = driver.createWriter();
		String gpx = writer.writeToString(doc);
		
		String[] lines = gpx.split("\n");
		for (String line : lines) {
			if (line.contains("wpt")) {
				if (!line.contains("<wpt ") && !line.contains("</wpt>")) {
					fail("Namespace not correct. Line was: \n\n" + line + "\n\nAnd the complete gpx was:\n\n" + gpx);
				}
			}
			
			if (line.contains("gpx")) {
				if (!line.contains("<gpx") && !line.contains("</gpx>")) {
					fail("Namespace not correct. Line was: \n\n" + line + "\n\nAnd the complete gpx was:\n\n" + gpx);
				}
			}
		}
		System.out.println("The complete gpx was:\n" + gpx);
	}
	
	@Test
	public void formatBigDecimal() throws GpxFileNotFoundException, GpxPropertiesException, GpxWriterException, GpxIOException {
		GpxDriver driver = GpxDriver.getGpxDriver();
		driver.loadDefaultDriverProperties();
		GpxDocument doc = new GpxDocument();
		List<Waypoint> wpts = doc.getWaypoints();
		wpts.add(new Waypoint(new BigDecimal("13.37"), new BigDecimal("54.31")));
		
		IGpxWriter writer = driver.createWriter();
		String gpx = writer.writeToString(doc);
		
		String[] lines = gpx.split("\n");
		for (String line : lines) {
			if (line.contains("<wpt")) {
				if (!line.contains("lat=\"13.37\"") && !line.contains("lon=\"54.31\"")) {
					fail("Format not correct. Line was: \n\n" + line + "\n\nAnd the complete gpx was:\n\n" + gpx);
				}
			}
		}
	}
}
