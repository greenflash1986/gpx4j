package org.casaca.gpx4j.tools.exporter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.casaca.gpx4j.tools.exception.GpxExporterException;

public class MethodClassField extends AbstractField{

	private Method method;
	private Object[] arguments;
	
	public MethodClassField(){
		super();
		this.method = null;
		this.arguments = null;
	}
	
	public Method getMethod(){
		return this.method;
	}
	
	public void setMethod(Method method){
		this.method = method;
	}
	
	public void setArguments(Object... args){
		this.arguments = args;
	}
	
	public Object[] getArguments(){
		return this.arguments;
	}

	@Override
	public String getContent() throws GpxExporterException {
		try {
			Object result;
			if(!this.method.isAccessible()){
				this.method.setAccessible(true);
				result = this.method.invoke(this.getObject(), this.arguments);
				this.method.setAccessible(false);
			}
			else
				result = this.method.invoke(this.getObject(), this.arguments);
			
			return (result==null)?"":String.valueOf(result);
		} catch (IllegalArgumentException e) {
			throw new GpxExporterException(e);
		} catch (IllegalAccessException e) {
			throw new GpxExporterException(e);
		} catch (InvocationTargetException e) {
			throw new GpxExporterException(e);
		}
	}
}