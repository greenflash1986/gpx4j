package org.casaca.gpx4j.tools.exporter.csv;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Properties;

import org.casaca.gpx4j.tools.exception.GpxExporterException;
import org.casaca.gpx4j.tools.exporter.AbstractExporter;
import org.casaca.gpx4j.tools.exporter.IField;


public class CSVExporter extends AbstractExporter {
	
	private boolean quotes;
	private String sQuotes;
	private String delimiter;
	
	public CSVExporter(Properties props){
		super(props);
		
		this.quotes = true;
		this.sQuotes = (this.quotes)?"\"":"";
		this.delimiter = ",";
	}

	public boolean isQuotes() {
		return quotes;
	}

	public void setQuotes(boolean quotes) {
		this.quotes = quotes;
		this.sQuotes = (this.quotes)?"\"":"";
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	@Override
	public void export(OutputStream output) throws GpxExporterException {
		PrintWriter writer = new PrintWriter(output);
		StringBuffer sb = new StringBuffer();
		String s;
		if(this.isShowingHeader()){
			Iterator<? extends IField> fields = this.getFields().iterator();
			while(fields.hasNext()){
				s=fields.next().getName();
				sb.append(this.sQuotes).append((s!=null)?s:"").append(this.sQuotes).append(delimiter);
			}
			sb = sb.deleteCharAt(sb.lastIndexOf(delimiter));
			writer.println(sb.toString());
			sb.delete(0, sb.length());
		}
		
		for (Object o : this.getData()) {
			for (IField field : this.getFields()) {
				field.setObject(o);
				s=field.getContent();
				if(s.equals(""))
					sb.append(this.delimiter);
				else
					sb.append(this.sQuotes).append((s!=null)?s:"").append(this.sQuotes).append(this.delimiter);
			}
			sb = sb.deleteCharAt(sb.lastIndexOf(this.delimiter));
			writer.println(sb.toString());
			sb.delete(0, sb.length());
		}
		
		try {
			writer.close();
			output.close();
		} catch (IOException e) {
			throw new GpxExporterException(e);
		}
	}
}