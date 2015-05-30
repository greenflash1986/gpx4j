package org.casaca.gpx4j.core.driver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.casaca.gpx4j.core.exception.GpxFileNotFoundException;
import org.casaca.gpx4j.core.exception.GpxIOException;
import org.casaca.gpx4j.core.exception.GpxPropertiesException;
import org.casaca.gpx4j.core.exception.GpxReaderException;
import org.casaca.gpx4j.core.exception.GpxWriterException;
import org.casaca.gpx4j.core.logging.Logger;
import org.casaca.gpx4j.core.util.Constants;

public class GpxDriver {
	private IGpxReader reader;
	private IGpxWriter writer;
	private Properties driverProperties;
	private Properties loggerProperties;
	private Properties tagProperties;
	private Logger logger;
	
	private static GpxDriver driver = null;
	
	public static GpxDriver getGpxDriver() throws GpxFileNotFoundException, GpxPropertiesException{
		if(driver == null)
			driver = new GpxDriver();
		
		return driver;
	}
	
	private GpxDriver() throws GpxPropertiesException, GpxFileNotFoundException{
		String defaultTagsFileName = Constants.APPLICATION_KEYS_FILE_PREFIX_FILENAME+Constants.APPLICATION_KEYS_FILE_DEFAULT_BODY_FILENAME+Constants.APPLICATION_KEYS_FILE_SUFIX_FILENAME;
		this.driverProperties = null;
		this.loggerProperties = null;
		try {
			this.tagProperties = new Properties();
			this.tagProperties.load(GpxDriver.class.getClassLoader().getResourceAsStream(defaultTagsFileName));
		} catch (IOException e) {
			throw new GpxFileNotFoundException(defaultTagsFileName+" properties file does not found.\n"+e.getMessage());
		}
		this.reader = null;
		this.writer = null;
	}
	
	public void loadDriverProperties(String filePath) throws GpxIOException{
		try {
			this.loadDriverProperties(new FileInputStream(filePath));
		}catch (IOException e) {
			throw new GpxIOException(e);
		}
	}
	
	public void loadDriverProperties(InputStream input) throws GpxIOException{
		this.driverProperties = new Properties();
		try {
			this.driverProperties.load(input);
		}catch (IOException e) {
			throw new GpxIOException(e);
		}
	}
	
	public void loadDriverProperties(Properties properties){
		if(properties==null)
			throw new IllegalArgumentException("Error loading driver properties. Properties must not be null");
		
		this.driverProperties = properties;
	}
	
	public void loadDefaultDriverProperties() throws GpxFileNotFoundException, GpxIOException {
		try {
			this.loadDriverProperties(GpxDriver.class.getClassLoader().getResourceAsStream(Constants.APPLICATION_DEFAULT_DRIVER_PROPERTIES_FILENAME));
		}catch (IOException e) {
			throw new GpxIOException("Error loading default driver properties.", e);
		}
	}
	
	public void loadLoggerProperties(String filePath) throws GpxFileNotFoundException, GpxIOException{
		this.loggerProperties = new Properties();
		try {
			this.loggerProperties.load(new FileInputStream(filePath));
			PropertyConfigurator.configure(this.loggerProperties);
		} catch (FileNotFoundException e) {
			throw new GpxFileNotFoundException("Not found the logger properties file \""+filePath+"\"");
		} catch (IOException e) {
			throw new GpxIOException(e);
		}
	}
	
	public void loadLoggerProperties(Properties properties) throws IllegalArgumentException{
		if(properties==null)
			throw new IllegalArgumentException("Error loading logger properties. Properties must not be null");
		this.loggerProperties = properties;
		PropertyConfigurator.configure(this.loggerProperties);
	}
	
	public void loadLoggerProperties() throws GpxPropertiesException, GpxFileNotFoundException, GpxIOException{
		if(this.driverProperties==null)
			throw new GpxPropertiesException("Error loading logger properties. Please first load driver properties to load logger properties from configuration file");
		String filePath = null;
		try {
			filePath = this.driverProperties.getProperty(Constants.APPLICATION_LOGGER_PROPERTIES_FILEPATH);
			if(filePath==null)
				throw new GpxPropertiesException("Error loading logger properties. Key "+Constants.APPLICATION_LOGGER_PROPERTIES_FILEPATH+" not exists in application properties file");
			this.loggerProperties.load(new FileInputStream(filePath));
			PropertyConfigurator.configure(this.loggerProperties);
		} catch (FileNotFoundException e) {
			throw new GpxFileNotFoundException("Not found the logger properties file \""+filePath+"\"");
		} catch (IOException e) {
			throw new GpxIOException(e);
		}
		
	}
	
	public void loadDefaultLoggerProperties() throws GpxFileNotFoundException, GpxIOException {
		this.loggerProperties = new Properties();
		try {
			this.loggerProperties.load(Logger.class.getResourceAsStream(Constants.APPLICATION_DEFAULT_LOGGER_PROPERTIES_FILENAME));
			PropertyConfigurator.configure(this.loggerProperties);
		} catch (FileNotFoundException e) {
			throw new GpxFileNotFoundException("Error loading default logger properties");
		} catch (IOException e) {
			throw new GpxIOException(e);
		}
	}
	
	public Properties getDriverProperties() throws GpxPropertiesException{
		if(this.driverProperties == null)
			throw new GpxPropertiesException("Driver properties not loaded. Please load properties before use the reader");
		
		return this.driverProperties;
	}
	
	public Properties getLoggerProperties(){
		return this.loggerProperties;
	}
	
	public Properties getTagsProperties(){
		return this.tagProperties;
	}
	
	public Logger getLogger() throws GpxPropertiesException{
		if(this.loggerProperties == null)
			throw new GpxPropertiesException("Logger properties not loaded. Please load properties from driver before use the logger");
		
		if(this.logger == null)
			this.logger = Logger.getLogger(this.getClass());
		
		return this.logger;
	}
	
	public IGpxReader getReader() throws GpxReaderException, GpxPropertiesException{
		if(this.reader == null){
			Properties driverProp = this.getDriverProperties();
			
			String className = driverProp.getProperty(Constants.DRIVER_READER_CLASS_NAME);
			if(className == null)
				throw new GpxReaderException("Property "+Constants.DRIVER_READER_CLASS_NAME+" not found in properties file");
			
			try {
				this.reader = (IGpxReader)Class.forName(className).newInstance();
			} catch (InstantiationException e) {
				throw new GpxReaderException(e);
			} catch (IllegalAccessException e) {
				throw new GpxReaderException(e);
			} catch (ClassNotFoundException e) {
				throw new GpxReaderException(e);
			}
		}
		
		return this.reader;
	}
	
	public IGpxReader createReader() throws GpxReaderException, GpxPropertiesException{
		if(this.driverProperties == null)
			throw new GpxPropertiesException("Driver properties not loaded. Please load properties before use the reader");

		String className = this.driverProperties.getProperty(Constants.DRIVER_READER_CLASS_NAME);
		if(className == null)
			throw new GpxReaderException("Property "+Constants.DRIVER_READER_CLASS_NAME+" not found in properties file");

		return this.createReader(className);
	}
	
	public IGpxReader createReader(String classname) throws GpxReaderException, IllegalArgumentException, ClassCastException{
		IGpxReader reader = null;
		if(classname == null)
			throw new IllegalArgumentException("Error creating reader from a classname. Classname must not be null");

		try {
			reader = (IGpxReader)Class.forName(classname).newInstance();
			return reader;
		} catch (InstantiationException e) {
			throw new GpxReaderException(e);
		} catch (IllegalAccessException e) {
			throw new GpxReaderException(e);
		} catch (ClassNotFoundException e) {
			throw new GpxReaderException(e);
		}
	}
	
	public IGpxWriter getWriter() throws GpxWriterException, GpxPropertiesException {
		if(this.writer == null){
			if(this.driverProperties == null)
				throw new GpxPropertiesException("Driver properties not loaded. Please load properties before use the writer");
			
			String className = this.driverProperties.getProperty(Constants.DRIVER_WRITER_CLASS_NAME);
			if(className == null)
				throw new GpxWriterException("Property "+Constants.DRIVER_WRITER_CLASS_NAME+" not found in properties file");
			
			try {
				this.writer = (IGpxWriter)Class.forName(className).newInstance();
			} catch (InstantiationException e) {
				throw new GpxWriterException(e);
			} catch (IllegalAccessException e) {
				throw new GpxWriterException(e);
			} catch (ClassNotFoundException e) {
				throw new GpxWriterException(e);
			}
		}
		
		return this.writer;
	}
	
	public IGpxWriter createWriter() throws GpxWriterException, GpxPropertiesException {
		String className = this.getDriverProperties().getProperty(Constants.DRIVER_WRITER_CLASS_NAME);
		if(className == null)
			throw new GpxWriterException("Property "+Constants.DRIVER_WRITER_CLASS_NAME+" not found in properties file");

		return this.createWriter(className);
	}
	
	public IGpxWriter createWriter(String classname) throws GpxWriterException{
		IGpxWriter writer = null;
		if(classname == null)
			throw new IllegalArgumentException("Error creating writer from a classname. Classname must not be null");

		try {
			writer = (IGpxWriter)Class.forName(classname).newInstance();
			return writer;
		} catch (InstantiationException e) {
			throw new GpxWriterException(e);
		} catch (IllegalAccessException e) {
			throw new GpxWriterException(e);
		} catch (ClassNotFoundException e) {
			throw new GpxWriterException(e);
		}
	}
}
