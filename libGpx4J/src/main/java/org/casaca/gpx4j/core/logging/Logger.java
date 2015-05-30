package org.casaca.gpx4j.core.logging;

public class Logger {

	private org.apache.log4j.Logger logger4j;
	
	protected Logger(String name){
		this.logger4j = org.apache.log4j.Logger.getLogger(name);
	}
	
	protected Logger(Class clazz){
		this.logger4j = org.apache.log4j.Logger.getLogger(clazz);
	}
	
	//STATIC
	private static Logger logger = null;
	
	public static Logger getLogger(String name){
		if(logger == null){
			logger = new Logger(name);
		}
		
		return logger;
	}
	
	public static Logger getLogger(Class clazz){
		if(logger == null){
			logger = new Logger(clazz);
		}
		
		return logger;
	}
	//END STATIC
	
	public void info(String message){
		this.logger4j.info(message);
	}
	
	public void info(String message, Throwable t){
		this.logger4j.info(message, t);
	}
	
	public void debug(String message){
		this.logger4j.debug(message);
	}
	
	public void debug(String message, Throwable t){
		this.logger4j.debug(message, t);
	}
	
	public void warn(String message){
		this.logger4j.warn(message);
	}
	
	public void warn(String message, Throwable t){
		this.logger4j.warn(message, t);
	}
	
	public void error(String message){
		this.logger4j.error(message);
	}
	
	public void error(String message, Throwable t){
		this.logger4j.error(message, t);
	}
}