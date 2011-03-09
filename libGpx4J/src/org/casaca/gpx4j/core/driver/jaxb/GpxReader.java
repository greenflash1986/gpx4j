package org.casaca.gpx4j.core.driver.jaxb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.casaca.gpx4j.core.data.GpxDocument;
import org.casaca.gpx4j.core.driver.GpxDriver;
import org.casaca.gpx4j.core.driver.IGpxReader;
import org.casaca.gpx4j.core.exception.GpxFileNotFoundException;
import org.casaca.gpx4j.core.exception.GpxIOException;
import org.casaca.gpx4j.core.exception.GpxPropertiesException;
import org.casaca.gpx4j.core.exception.GpxReaderException;
import org.casaca.gpx4j.core.exception.GpxValidationException;
import org.casaca.gpx4j.core.logging.Logger;
import org.casaca.gpx4j.core.util.Constants;
import org.xml.sax.SAXException;

public class GpxReader implements IGpxReader {
	
	private GpxDriver driver;
	private Properties driverProp;
	private Logger logger;
	
	public GpxReader() throws GpxPropertiesException, GpxFileNotFoundException{
		this.driver = GpxDriver.getGpxDriver();
		this.driverProp = this.driver.getDriverProperties();
		this.logger = Logger.getLogger(this.getClass());
	}
	
	private boolean validateDocumentLocal(InputStream input) throws GpxValidationException{
		try {
			SchemaFactory schFact = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);
			Schema sch = schFact.newSchema(Constants.class.getResource(Constants.APPLICATION_DEFAULT_GPX_DTD_1_1_FILENAME));
			Validator validator = sch.newValidator();
			validator.validate(new StreamSource(input));
			
			return true;
		} catch (SAXException e) {
			throw new GpxValidationException("Validation failed", e);
		} catch (IOException e) {
			throw new GpxValidationException("Validation failed", e);
		}
	}
	
	private boolean validateDocumentRemote(InputStream input) throws GpxValidationException, GpxIOException{
		SchemaFactory schFact = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);
		try {
			URL url = new URL(this.driverProp.getProperty(Constants.DRIVER_DTD_URL_GPX_1_1));
			Schema sch = null;
			try {
				sch = schFact.newSchema(new StreamSource(url.openStream()));
			} catch (IOException e) {
				throw new GpxIOException(e);
			}
			Validator validator = sch.newValidator();
			validator.validate(new StreamSource(input));
			
			return true;
		} catch (MalformedURLException e) {
			throw new GpxValidationException("Validation failed", e);
		} catch (SAXException e) {
			throw new GpxValidationException("Validation failed", e);
		} catch (IOException e) {
			throw new GpxValidationException("Validation failed", e);
		}
	}
	
	@Override
	public GpxDocument readGpxDocument(String filepath, boolean validateDocument) throws GpxFileNotFoundException, GpxIOException, GpxReaderException, GpxValidationException {
		return this.readGpxDocument(new File(filepath), validateDocument);
	}

	@Override
	public GpxDocument readGpxDocument(String filepath) throws GpxFileNotFoundException, GpxIOException, GpxReaderException, GpxPropertiesException, GpxValidationException {
		return this.readGpxDocument(new File(filepath));
	}

	@Override
	public GpxDocument readGpxDocument(File input, boolean validateDocument) throws GpxFileNotFoundException, GpxIOException, GpxReaderException, GpxValidationException {
		try {
			return this.readGpxDocument(new FileInputStream(input), validateDocument);
		} catch (FileNotFoundException e) {
			throw new GpxFileNotFoundException(e.getMessage());
		}
	}

	@Override
	public GpxDocument readGpxDocument(File input) throws GpxFileNotFoundException, GpxIOException, GpxReaderException, GpxPropertiesException, GpxValidationException{
		Properties properties = GpxDriver.getGpxDriver().getDriverProperties();
		if(properties == null) throw new GpxPropertiesException("Driver properties not loaded. Please load properties from driver");
		
		return this.readGpxDocument(input, Boolean.valueOf(properties.getProperty(Constants.DRIVER_VALIDATE_GPX_FILE, "false")));
	}
	

	@Override
	public GpxDocument readGpxDocument(InputStream input) throws GpxIOException, GpxReaderException, GpxPropertiesException, GpxValidationException {
		if(driverProp == null) throw new GpxPropertiesException("Driver properties not loaded. Please load properties from driver");
		
		return this.readGpxDocument(input, Boolean.valueOf(driverProp.getProperty(Constants.DRIVER_VALIDATE_GPX_FILE, "false")));
	}
	
	@Override
	public GpxDocument readGpxDocument(InputStream input, boolean validateDocument) throws GpxIOException, GpxReaderException, GpxValidationException {
		try {
			JAXBContext jc = JAXBContext.newInstance(GpxType.class.getPackage().getName());
			Unmarshaller u = jc.createUnmarshaller();
			
			//VALIDATING DOCUMENT
			if(validateDocument){
				try {
					this.validateDocumentRemote(input);
				} catch (GpxValidationException e) {
					//LOG
					throw e;
				} catch (GpxIOException e){
					try {
						this.validateDocumentLocal(input);
					} catch (GpxValidationException e1) {
						//LOG
						throw e1;
					}
				}
			}
			
			GpxType gpxDoc = (GpxType)((JAXBElement)u.unmarshal(input)).getValue();
			return new JaxbAdapter().toGpxDocument(gpxDoc);
		} catch (JAXBException e) {
			throw new GpxReaderException(e);
		}
	}
}
