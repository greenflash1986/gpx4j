package org.casaca.gpx4j.tools.exporter;

import java.lang.reflect.Field;

import org.casaca.gpx4j.tools.exception.GpxExporterException;


public class FieldClassField extends AbstractField {
	
	private Field field;
	
	public FieldClassField(){
		super();
		this.field=null;
	}

	public Field getField(){
		return this.field;
	}
	
	public void setField(Field field){
		this.field = field;
	}

	@Override
	public String getContent() throws GpxExporterException {
		try {
			String result;
			if(!this.field.isAccessible()){
				this.field.setAccessible(true);
				result = String.valueOf(this.field.get(this.getObject()));
				this.field.setAccessible(false);
			}
			else
				result = String.valueOf(this.field.get(this.getObject()));
			
			return (result==null)?"":result;
		} catch (IllegalArgumentException e) {
			throw new GpxExporterException(e);
		} catch (IllegalAccessException e) {
			throw new GpxExporterException(e);
		}
	}
}
