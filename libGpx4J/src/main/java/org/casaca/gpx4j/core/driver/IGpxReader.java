package org.casaca.gpx4j.core.driver;

import java.io.File;
import java.io.InputStream;

import org.casaca.gpx4j.core.data.GpxDocument;
import org.casaca.gpx4j.core.exception.GpxFileNotFoundException;
import org.casaca.gpx4j.core.exception.GpxIOException;
import org.casaca.gpx4j.core.exception.GpxPropertiesException;
import org.casaca.gpx4j.core.exception.GpxReaderException;
import org.casaca.gpx4j.core.exception.GpxValidationException;

public interface IGpxReader {
	public GpxDocument readGpxDocument(String filepath)  throws GpxValidationException, GpxFileNotFoundException, GpxIOException, GpxReaderException, GpxPropertiesException;
	
	public GpxDocument readGpxDocument(String filepath, boolean validateDocument)  throws GpxValidationException, GpxFileNotFoundException, GpxIOException, GpxReaderException;
	
	public GpxDocument readGpxDocument(File input, boolean validateDocument)  throws GpxValidationException, GpxFileNotFoundException, GpxIOException, GpxReaderException;
	
	public GpxDocument readGpxDocument(File input) throws GpxValidationException, GpxFileNotFoundException, GpxIOException, GpxReaderException, GpxPropertiesException;
	
	public GpxDocument readGpxDocument(InputStream input, boolean validateDocument)  throws GpxValidationException, GpxIOException, GpxReaderException;
	
	public GpxDocument readGpxDocument(InputStream input) throws GpxValidationException, GpxIOException, GpxReaderException, GpxPropertiesException;
}
