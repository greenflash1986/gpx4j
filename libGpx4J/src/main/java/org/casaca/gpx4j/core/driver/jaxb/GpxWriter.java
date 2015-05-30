package org.casaca.gpx4j.core.driver.jaxb;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.casaca.gpx4j.core.data.GpxDocument;
import org.casaca.gpx4j.core.driver.GpxDriver;
import org.casaca.gpx4j.core.driver.IGpxWriter;
import org.casaca.gpx4j.core.exception.GpxFileNotFoundException;
import org.casaca.gpx4j.core.exception.GpxIOException;
import org.casaca.gpx4j.core.exception.GpxPropertiesException;
import org.casaca.gpx4j.core.exception.GpxWriterException;
import org.casaca.gpx4j.core.util.Constants;

public class GpxWriter implements IGpxWriter {
	
	private Properties appProperties;
	private Properties tagProperties;
	private GpxDriver driver;
	
	public GpxWriter() throws GpxFileNotFoundException, GpxPropertiesException{
		this.driver = GpxDriver.getGpxDriver();
		this.appProperties = this.driver.getDriverProperties();
		this.tagProperties = this.driver.getTagsProperties();
	}

	@Override
	public void write(GpxDocument doc, String filePath) throws GpxIOException, GpxPropertiesException, GpxFileNotFoundException, GpxWriterException {
		try {
			this.write(doc, new FileOutputStream(filePath));
		}catch (FileNotFoundException e) {
			throw new GpxFileNotFoundException(e.getMessage());
		}
	}

	@Override
	public void write(GpxDocument doc, OutputStream output) throws GpxPropertiesException, GpxIOException, GpxWriterException {
		if(this.appProperties==null)
			 throw new GpxPropertiesException("Driver properties not loaded. Please load properties from driver");
		
		try {
			JAXBContext jc = JAXBContext.newInstance(GpxType.class.getPackage().getName());
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.valueOf(this.appProperties.getProperty(Constants.DRIVER_WRITER_NEW_LINE, "true")));
			marshaller.setProperty("com.sun.xml.bind.indentString", this.appProperties.getProperty(Constants.DRIVER_WRITER_INDENTATION_TEXT, "\t"));
			marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, this.appProperties.getProperty(Constants.DRIVER_DTD_URL_GPX_1_1));
			marshaller.setProperty(Marshaller.JAXB_ENCODING, this.appProperties.getProperty(Constants.DRIVER_WRITER_ENCODING, "UTF-8"));
			//marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, this.appProperties.getProperty(Constants.DRIVER_DTD_URL_GPX_1_1));
			marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new JaxbNamespacePrefixMapper());
			
			JAXBElement<GpxType> element = new JAXBElement<GpxType>(new QName(this.tagProperties.getProperty(Constants.TAG_GPX)), GpxType.class, (GpxType)new JaxbAdapter().fromGpxDocument(doc));
			
			marshaller.marshal(element, output);
		} catch (JAXBException e) {
			throw new GpxWriterException(e);
		}
	}

	@Override
	public String writeToString(GpxDocument doc) throws GpxPropertiesException, GpxWriterException {
		if(this.appProperties==null)
			 throw new GpxPropertiesException("Driver properties not loaded. Please load properties from driver");
		
		try {
			JAXBContext jc = JAXBContext.newInstance(GpxType.class.getPackage().getName());
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.valueOf(this.appProperties.getProperty(Constants.DRIVER_WRITER_NEW_LINE, "true")));
			marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, this.appProperties.getProperty(Constants.DRIVER_DTD_URL_GPX_1_1));
			marshaller.setProperty(Marshaller.JAXB_ENCODING, this.appProperties.getProperty(Constants.DRIVER_WRITER_ENCODING, "UTF-8"));
			
			StringWriter sw = new StringWriter();
			JAXBElement<GpxType> element = new JAXBElement<GpxType>(new QName(this.tagProperties.getProperty(Constants.TAG_GPX)), GpxType.class, (GpxType)new JaxbAdapter().fromGpxDocument(doc));
			marshaller.marshal(element, sw);
			
			return sw.toString();
		} catch (JAXBException e) {
			throw new GpxWriterException(e);
		}
	}

}
