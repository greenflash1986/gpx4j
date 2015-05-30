package org.casaca.gpx4j.core.jaxb;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;

import org.casaca.gpx4j.core.data.GpxDocument;
import org.casaca.gpx4j.core.data.Waypoint;
import org.casaca.gpx4j.core.driver.GpxDriver;
import org.casaca.gpx4j.core.driver.IGpxWriter;
import org.casaca.gpx4j.core.exception.GpxPropertiesException;
import org.casaca.gpx4j.core.exception.GpxWriterException;
import org.casaca.gpx4j.core.util.SortedList;
import org.junit.Test;

public class JaxbGPXWriterTest {
	
	@Test
	public void writeOutAndCheckNameSpace() throws GpxPropertiesException, GpxWriterException, IOException {
		GpxDriver driver = GpxDriver.getGpxDriver();
		driver.loadDefaultDriverProperties();
		GpxDocument doc = new GpxDocument();
		SortedList<Waypoint> wpts = doc.getWaypoints();
		wpts.add(new Waypoint(new BigDecimal(13.34), new BigDecimal(54.32)));
		
		IGpxWriter writer = driver.createWriter();
		String gpx = writer.writeToString(doc);
		
		String[] lines = gpx.split("\n");
		for (String line : lines) {
			if (line.contains("wpt")) {
				if (!line.contains("<wpt>") && !line.contains("</wpt>")) {
					System.out.println(gpx);
					fail();
				}
			}
		}
	}
}
