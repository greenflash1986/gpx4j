package org.casaca.gpx4j.core.exception;

import java.io.IOException;

public class GpxIOException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4386355490863575429L;

	public GpxIOException(){
		super();
	}
	
	public GpxIOException(String message){
		super(message);
	}
	
	public GpxIOException(String message, Throwable t){
		super(message, t);
	}
	
	public GpxIOException(Throwable t){
		super(t);
	}
}
