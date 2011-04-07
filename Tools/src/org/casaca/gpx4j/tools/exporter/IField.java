package org.casaca.gpx4j.tools.exporter;

import org.casaca.gpx4j.tools.exception.GpxExporterException;

public interface IField {
	
	public String getName();
	
	public void setName(String name);
	
	public Object getObject();
	
	public void setObject(Object o);
	
	public String getContent() throws GpxExporterException;
}
