package org.casaca.gpx4j.tools.exporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.casaca.gpx4j.tools.exception.GpxExporterException;

public abstract class AbstractExporter implements IExporter {
	
	private List<IField> fields;
	private List<Object> data;
	private boolean showingHeader;
	
	public AbstractExporter(){
		this.fields = new ArrayList<IField>();
		this.data = new ArrayList<Object>();
		this.showingHeader = true;
	}

	@Override
	public void showHeader(boolean header) {
		this.showingHeader = header;
	}

	@Override
	public boolean isShowingHeader() {
		return this.showingHeader;
	}

	@Override
	public List<IField> getFields() {
		return this.fields;
	}

	@Override
	public List<Object> getData() {
		return this.data;
	}

	@Override
	public void export(String filePath) throws GpxExporterException {
		this.export(new File(filePath));
	}

	@Override
	public void export(File file) throws GpxExporterException {
		try {
			this.export(new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			throw new GpxExporterException(e);
		}
	}
}
