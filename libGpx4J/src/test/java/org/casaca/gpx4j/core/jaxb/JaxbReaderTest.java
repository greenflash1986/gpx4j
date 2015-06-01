package org.casaca.gpx4j.core.jaxb;

import static org.junit.Assert.*;

import java.util.List;

import org.casaca.gpx4j.core.data.GpxDocument;
import org.casaca.gpx4j.core.data.Waypoint;
import org.casaca.gpx4j.core.driver.GpxDriver;
import org.casaca.gpx4j.core.driver.IGpxReader;
import org.casaca.gpx4j.core.exception.GpxFileNotFoundException;
import org.casaca.gpx4j.core.exception.GpxIOException;
import org.casaca.gpx4j.core.exception.GpxPropertiesException;
import org.casaca.gpx4j.core.exception.GpxReaderException;
import org.casaca.gpx4j.core.exception.GpxValidationException;
import org.junit.Test;

public class JaxbReaderTest {

	@Test
	public void testWptOrder() throws GpxFileNotFoundException, GpxPropertiesException, GpxIOException, GpxReaderException,
			GpxValidationException {
		GpxDriver driver = GpxDriver.getGpxDriver();
		driver.loadDefaultDriverProperties();
		IGpxReader reader = driver.createReader();
		GpxDocument doc = reader.readGpxDocument(getClass().getClassLoader().getResourceAsStream("Waypoints.gpx"));
		List<Waypoint> wpts = doc.getWaypoints();
		String order = "";
		boolean failed = false;
		for (int i = 0; i < wpts.size(); ++i) {
			if (!Integer.toString(i).equals(wpts.get(i).getName())) {
				failed = true;
			}
			order += wpts.get(i).getName() + "\n";
		}

		assertFalse("Wrong order of waypoints. The order was:\n" + order, failed);
	}
}
