package org.casaca.gpx4j.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.casaca.gpx4j.core.exception.GpxIOException;
import org.casaca.gpx4j.core.exception.GpxPropertiesException;
import org.casaca.gpx4j.tools.chronometer.IChronometer;
import org.casaca.gpx4j.tools.compass.ICompass;
import org.casaca.gpx4j.tools.exception.GpxChronometerException;
import org.casaca.gpx4j.tools.exception.GpxCompassException;
import org.casaca.gpx4j.tools.exception.GpxExporterException;
import org.casaca.gpx4j.tools.exception.GpxRangefinderException;
import org.casaca.gpx4j.tools.exception.GpxSpeedoException;
import org.casaca.gpx4j.tools.exporter.IExporter;
import org.casaca.gpx4j.tools.rangefinder.IRangefinder;
import org.casaca.gpx4j.tools.speedo.ISpeedo;
import org.casaca.gpx4j.tools.util.Constants;
import org.casaca.gpx4j.tools.util.Converter;
import org.casaca.gpx4j.tools.util.Formatter;

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
	private IChronometer ch;
	private IExporter xp;
	private ICompass cp;
	private Converter co;
	private Formatter fm;
	
	private Properties toolsProp;
	
	private GpxTools(){
		this.rf = null;
		this.sp = null;
		this.co = null;
		this.ch = null;
		this.xp = null;
		this.fm = null;
		this.cp = null;
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
			
			String className = toolsProp.getProperty(Constants.TOOLS_RANGEFINDER_CLASS_NAME, Constants.APPLICATION_DEFAULT_RANGEFINDER_CLASS_NAME);
			if(className == null)
				throw new GpxRangefinderException("Property "+Constants.TOOLS_RANGEFINDER_CLASS_NAME+" not found in properties file");
			
			try {
				this.rf = this.createRangefinder(Class.forName(className).asSubclass(Tool.class));
			} catch (ClassNotFoundException e) {
				throw new GpxRangefinderException(e);
			}
		}
		
		return this.rf;
	}
	
	public IRangefinder createRangefinder(Class<? extends Tool> clazz) throws GpxRangefinderException, GpxPropertiesException{
		try {
			return (IRangefinder)clazz.getConstructor(Properties.class).newInstance(this.getToolsProperties());
		} catch (IllegalArgumentException e) {
			throw new GpxRangefinderException(e);
		} catch (SecurityException e) {
			throw new GpxRangefinderException(e);
		} catch (InvocationTargetException e) {
			throw new GpxRangefinderException(e);
		} catch (NoSuchMethodException e) {
			throw new GpxRangefinderException(e);
		} catch (InstantiationException e) {
			throw new GpxRangefinderException(e);
		} catch (IllegalAccessException e) {
			throw new GpxRangefinderException(e);
		}
	}
	
	public ISpeedo getSpeedo() throws GpxPropertiesException, GpxSpeedoException{
		if(this.sp==null){
			Properties toolsProp = this.getToolsProperties();
			
			String className = toolsProp.getProperty(Constants.TOOLS_SPEEDO_CLASS_NAME, Constants.APPLICATION_DEFAULT_SPEEDO_CLASS_NAME);
			if(className == null)
				throw new GpxSpeedoException("Property "+Constants.TOOLS_SPEEDO_CLASS_NAME+" not found in properties file");
			
			try {
				this.sp = this.createSpeedo(Class.forName(className).asSubclass(Tool.class));
			} catch (ClassNotFoundException e) {
				throw new GpxSpeedoException(e);
			}
		}
		
		return this.sp;
	}
	
	public ISpeedo createSpeedo(Class<? extends Tool> clazz) throws GpxSpeedoException, GpxPropertiesException{
		try {
			return (ISpeedo)clazz.getConstructor(Properties.class).newInstance(this.getToolsProperties());
		} catch (IllegalArgumentException e) {
			throw new GpxSpeedoException(e);
		} catch (SecurityException e) {
			throw new GpxSpeedoException(e);
		} catch (InvocationTargetException e) {
			throw new GpxSpeedoException(e);
		} catch (NoSuchMethodException e) {
			throw new GpxSpeedoException(e);
		} catch (InstantiationException e) {
			throw new GpxSpeedoException(e);
		} catch (IllegalAccessException e) {
			throw new GpxSpeedoException(e);
		}
	}
	
	public IChronometer getChronometer() throws GpxPropertiesException, GpxChronometerException{
		if(this.ch==null){
			Properties toolsProp = this.getToolsProperties();
			
			String className = toolsProp.getProperty(Constants.TOOLS_CHRONOMETER_CLASS_NAME, Constants.APPLICATION_DEFAULT_CHRONOMETER_CLASS_NAME);
			if(className == null)
				throw new GpxChronometerException("Property "+Constants.TOOLS_CHRONOMETER_CLASS_NAME+" not found in properties file");
			
			try {
				this.ch = this.createChronometer(Class.forName(className).asSubclass(Tool.class));
			} catch (ClassNotFoundException e) {
				throw new GpxChronometerException(e);
			}
		}
		
		return this.ch;
	}
	
	public IChronometer createChronometer(Class<? extends Tool> clazz) throws GpxChronometerException, GpxPropertiesException {
		try {
			return (IChronometer) clazz.getConstructor(Properties.class).newInstance(this.getToolsProperties());
		} catch (SecurityException e) {
			throw new GpxChronometerException(e);
		} catch (NoSuchMethodException e) {
			throw new GpxChronometerException(e);
		} catch (IllegalArgumentException e) {
			throw new GpxChronometerException(e);
		} catch (InstantiationException e) {
			throw new GpxChronometerException(e);
		} catch (IllegalAccessException e) {
			throw new GpxChronometerException(e);
		} catch (InvocationTargetException e) {
			throw new GpxChronometerException(e);
		} catch (ClassCastException e){
			throw new GpxChronometerException(e);
		}
	}
	
	public IExporter getExporter() throws GpxPropertiesException, GpxExporterException{
		if(this.xp==null){
			Properties toolsProp = this.getToolsProperties();
			
			String className = toolsProp.getProperty(Constants.TOOLS_EXPORTER_CLASS_NAME, Constants.APPLICATION_DEFAULT_EXPORTER_CLASS_NAME);
			if(className == null)
				throw new GpxExporterException("Property "+Constants.TOOLS_EXPORTER_CLASS_NAME+" not found in properties file");
			
			try {
				this.xp = this.createExporter(Class.forName(className).asSubclass(Tool.class));
			} catch (ClassNotFoundException e) {
				throw new GpxExporterException(e);
			}
		}
		
		return this.xp;
	}
	
	public IExporter createExporter(Class<? extends Tool> clazz) throws GpxExporterException, GpxPropertiesException {
		try {
			return (IExporter) clazz.getConstructor(Properties.class).newInstance(this.getToolsProperties());
		} catch (SecurityException e) {
			throw new GpxExporterException(e);
		} catch (NoSuchMethodException e) {
			throw new GpxExporterException(e);
		} catch (IllegalArgumentException e) {
			throw new GpxExporterException(e);
		} catch (InstantiationException e) {
			throw new GpxExporterException(e);
		} catch (IllegalAccessException e) {
			throw new GpxExporterException(e);
		} catch (InvocationTargetException e) {
			throw new GpxExporterException(e);
		} catch (ClassCastException e) {
			throw new GpxExporterException(e);
		}
	}
	
	public ICompass getCompass() throws GpxPropertiesException, GpxCompassException{
		if(this.cp==null){
			Properties toolsProp = this.getToolsProperties();
			
			String className = toolsProp.getProperty(Constants.TOOLS_COMPASS_CLASS_NAME, Constants.APPLICATION_DEFAULT_COMPASS_CLASS_NAME);
			if(className == null)
				throw new GpxCompassException("Property "+Constants.TOOLS_COMPASS_CLASS_NAME+" not found in properties file");
			
			try {
				this.cp = this.createCompass(Class.forName(className).asSubclass(Tool.class));
			} catch (ClassNotFoundException e) {
				throw new GpxCompassException(e);
			}
		}
		
		return this.cp;
	}
	
	public ICompass createCompass(Class<? extends Tool> clazz) throws GpxCompassException, GpxPropertiesException{
		try {
			return (ICompass) clazz.getConstructor(Properties.class).newInstance(this.getToolsProperties());
		} catch (IllegalArgumentException e) {
			throw new GpxCompassException(e);
		} catch (SecurityException e) {
			throw new GpxCompassException(e);
		} catch (InstantiationException e) {
			throw new GpxCompassException(e);
		} catch (IllegalAccessException e) {
			throw new GpxCompassException(e);
		} catch (InvocationTargetException e) {
			throw new GpxCompassException(e);
		} catch (NoSuchMethodException e) {
			throw new GpxCompassException(e);
		}
	}
	
	public Converter getConverter() throws GpxPropertiesException{
		if(this.co==null)
			this.co = new Converter(this.getToolsProperties());
		
		return this.co;
	}
	
	public Formatter getFormatter() throws GpxPropertiesException{
		if(this.fm==null)
			this.fm = new Formatter(this.getToolsProperties());
		
		return this.fm;
	}
}
