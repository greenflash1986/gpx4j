package org.casaca.gpx4j.test;

import java.io.File;

import org.casaca.gpx4j.core.driver.reader.sax.GpxReader;
import org.casaca.gpx4j.core.exception.GpxFileNotFoundException;
import org.casaca.gpx4j.core.exception.GpxIOException;
import org.casaca.gpx4j.core.exception.GpxReaderException;

public class Main {

	public static void main(String args[]){
		try {
			GpxReader reader = new GpxReader();
			reader.readGpxDocument(new File("/Users/Kiko/Downloads/230211.gpx"), false);
		} catch (GpxFileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GpxIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GpxReaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
