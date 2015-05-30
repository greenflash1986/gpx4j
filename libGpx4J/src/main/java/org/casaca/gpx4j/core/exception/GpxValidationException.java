package org.casaca.gpx4j.core.exception;

public class GpxValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6461604431011677165L;

	public GpxValidationException(){
		super();
	}
	
	public GpxValidationException(String message){
		super(message);
	}
	
	public GpxValidationException(String message, Throwable t){
		super(message, t);
	}
	
	public GpxValidationException(Throwable t){
		super(t);
	}
}
