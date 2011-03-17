package org.casaca.gpx4j.tools.exception;

public class GpxSpeedoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4695235819487052008L;

	public GpxSpeedoException(){
		
	}
	
	public GpxSpeedoException(String message){
		super(message);
	}
	
	public GpxSpeedoException(Throwable t){
		super(t);
	}
	
	public GpxSpeedoException(String message, Throwable t){
		super(message, t);
	}
}
