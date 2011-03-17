package org.casaca.gpx4j.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.casaca.gpx4j.core.exception.GpxIOException;
import org.casaca.gpx4j.core.exception.GpxPropertiesException;
import org.casaca.gpx4j.tools.converter.Converter;
import org.casaca.gpx4j.tools.exception.GpxRangefinderException;
import org.casaca.gpx4j.tools.exception.GpxSpeedoException;
import org.casaca.gpx4j.tools.rangefinder.IRangefinder;
import org.casaca.gpx4j.tools.speedo.ISpeedo;
import org.casaca.gpx4j.tools.util.Constants;

public class GpxTools {

	//STATIC
	private static GpxTools tools;
	static{
		tools = null;
	}
	
	public static GpxTools getTools(){
		if(tools==null)
			tools = new GpxTools();
		
		return tools;
	}
	//END STATIC
	
	private IRangefinder rf;
	private ISpeedo sp;
	private Converter co;
	
	private Properties toolsProp;
	
	private GpxTools(){
		this.rf = null;
		this.sp = null;
		this.co = null;
	}
	
	public void loadToolsProperties(String filePath) throws GpxIOException{
		try {
			this.loadToolsProperties(new FileInputStream(filePath));
		}catch (IOException e) {
			throw new GpxIOException(e);
		}
	}
	
	public void loadToolsProperties(InputStream input) throws GpxIOException{
		this.toolsProp=new Properties();
		try{
			this.toolsProp.load(input);
		}
		catch(IOException e){
			throw new GpxIOException(e);
		}
	}
	
	public void loadToolsProperties(Properties properties){
		if(properties==null)
			throw new IllegalArgumentException("Error loading tools properties. Properties must not be null");
		
		this.toolsProp=properties;
	}
	
	public void loadDefaultToolsProperties() throws GpxIOException{
		try{
			this.loadToolsProperties(GpxTools.class.getResourceAsStream(Constants.APPLICATION_DEFAULT_TOOLS_PROPERTIES_FILENAME));
		}
		catch(IOException e){
			throw new GpxIOException("Error loading default tools properties", e);
		}
	}
	
	public Properties getToolsProperties() throws GpxPropertiesException{
		if(this.toolsProp==null)
			throw new GpxPropertiesException("Tools properties not loaded. Please oad tools properties from GpxTools before use it");
		
		return this.toolsProp;
	}
	
	public IRangefinder getRangefinder() throws GpxRangefinderException, GpxPropertiesException{
		if(this.rf==null){
			Properties toolsProp = this.getToolsProperties();
			
			String className = toolsProp.getProperty(Constants.TOOLS_RANGEFINDER_CLASS_NAME);
			if(className == null)
				throw new GpxRangefinderException("Property "+Constants.TOOLS_RANGEFINDER_CLASS_NAME+" not found in properties file");
			
			try {
				this.rf = (IRangefinder)Class.forName(className).newInstance();
			} catch (InstantiationException e) {
				throw new GpxRangefinderException(e);
			} catch (IllegalAccessException e) {
				throw new GpxRangefinderException(e);
			} catch (ClassNotFoundException e) {
				throw new GpxRangefinderException(e);
			}
		}
		
		return this.rf;
	}
	
	@SuppressWarnings("rawtypes")
	public IRangefinder createRangefinder(Class clazz) throws GpxRangefinderException{
		try {
			return (IRangefinder)clazz.newInstance();
		} catch (InstantiationException e) {
			throw new GpxRangefinderException(e);
		} catch (IllegalAccessException e) {
			throw new GpxRangefinderException(e);
		}
	}
	
	public ISpeedo getSpeedo() throws GpxPropertiesException, GpxSpeedoException{
		if(this.sp==null){
			Properties toolsProp = this.getToolsProperties();
			
			String className = toolsProp.getProperty(Constants.TOOLS_SPEEDO_CLASS_NAME);
			if(className == null)
				throw new GpxSpeedoException("Property "+Constants.TOOLS_SPEEDO_CLASS_NAME+" not found in properties file");
			
			try {
				this.sp = (ISpeedo)Class.forName(className).newInstance();
			} catch (InstantiationException e) {
				throw new GpxSpeedoException(e);
			} catch (IllegalAccessException e) {
				throw new GpxSpeedoException(e);
			} catch (ClassNotFoundException e) {
				throw new GpxSpeedoException(e);
			}
		}
		
		return this.sp;
	}
	
	@SuppressWarnings("rawtypes")
	public ISpeedo getSpeedo(Class clazz) throws GpxSpeedoException{
		try {
			return (ISpeedo)clazz.newInstance();
		} catch (InstantiationException e) {
			throw new GpxSpeedoException(e);
		} catch (IllegalAccessException e) {
			throw new GpxSpeedoException(e);
		}
	}
	
	public Converter getConverter(){
		if(this.co==null)
			this.co = new Converter();
		
		return this.co;
	}
}
