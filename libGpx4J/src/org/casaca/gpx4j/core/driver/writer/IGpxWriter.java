package org.casaca.gpx4j.core.driver.writer;

import java.io.OutputStream;

import org.casaca.gpx4j.core.data.GpxDocument;
import org.casaca.gpx4j.core.exception.GpxFileNotFoundException;
import org.casaca.gpx4j.core.exception.GpxIOException;
import org.casaca.gpx4j.core.exception.GpxPropertiesException;

public interface IGpxWriter {
	public void write(GpxDocument doc, String filePath) throws GpxIOException, GpxPropertiesException, GpxFileNotFoundException;
	
	public void write(GpxDocument doc, OutputStream output) throws GpxPropertiesException, GpxIOException;
	
	public String writeToString(GpxDocument doc) throws GpxPropertiesException;
}
