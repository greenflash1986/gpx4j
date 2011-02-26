package org.casaca.gpx4j.core.exception;

public class GpxWriterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5205797111016461086L;

	public GpxWriterException(){
		super();
	}
	
	public GpxWriterException(String message){
		super(message);
	}
	
	public GpxWriterException(String message, Throwable t){
		super(message, t);
	}
	
	public GpxWriterException(Throwable t){
		super(t);
	}
}
