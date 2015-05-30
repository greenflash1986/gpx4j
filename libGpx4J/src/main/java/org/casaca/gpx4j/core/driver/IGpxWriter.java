package org.casaca.gpx4j.core.driver;

import java.io.OutputStream;

import org.casaca.gpx4j.core.data.GpxDocument;
import org.casaca.gpx4j.core.exception.GpxFileNotFoundException;
import org.casaca.gpx4j.core.exception.GpxIOException;
import org.casaca.gpx4j.core.exception.GpxPropertiesException;
import org.casaca.gpx4j.core.exception.GpxWriterException;

public interface IGpxWriter {
	public void write(GpxDocument doc, String filePath) throws GpxIOException, GpxPropertiesException, GpxFileNotFoundException, GpxWriterException;
	
	public void write(GpxDocument doc, OutputStream output) throws GpxPropertiesException, GpxIOException, GpxWriterException;
	
	public String writeToString(GpxDocument doc) throws GpxPropertiesException, GpxWriterException;
}
