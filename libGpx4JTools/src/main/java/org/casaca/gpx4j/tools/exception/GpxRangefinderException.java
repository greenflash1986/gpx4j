package org.casaca.gpx4j.tools.exception;

public class GpxRangefinderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 70747507730475837L;
	
	public GpxRangefinderException(){
		super();
	}
	
	public GpxRangefinderException(String message){
		super(message);
	}
	
	public GpxRangefinderException(Throwable t){
		super(t);
	}
	
	public GpxRangefinderException(String message, Throwable t){
		super(message, t);
	}
}
