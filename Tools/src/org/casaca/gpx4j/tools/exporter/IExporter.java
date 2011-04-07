package org.casaca.gpx4j.tools.exporter;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

import org.casaca.gpx4j.tools.exception.GpxExporterException;


public interface IExporter {
	
	public void showHeader(boolean header);
	
	public boolean isShowingHeader();
	
	public List<IField> getFields();
	
	public List<Object> getData();
	
	public void export(String filePath) throws GpxExporterException;
	
	public void export(File file) throws GpxExporterException;
	
	public void export(OutputStream output) throws GpxExporterException;
}
