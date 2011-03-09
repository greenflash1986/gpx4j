package org.casaca.gpx4j.core.exception;

public class GpxPropertiesException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5387343352310214627L;
	
	public GpxPropertiesException(){
		super();
	}
	
	public GpxPropertiesException(String message){
		super(message);
	}
	
	public GpxPropertiesException(Throwable t){
		super(t);
	}
	
	public GpxPropertiesException(String message, Throwable t){
		super(message, t);
	}
}
