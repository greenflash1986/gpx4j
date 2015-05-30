package org.casaca.gpx4j.core.data;

import java.util.Calendar;

public class Copyright extends BaseObject {
	private Calendar year;
	private String author;
	private String license;
	
	public Copyright(Calendar year, String author, String license){
		this.year = year;
		this.author = author;
		this.license = license;
	}

	public Calendar getYear() {
		return year;
	}

	public String getAuthor() {
		return author;
	}

	public String getLicense() {
		return license;
	}

	@Override
	public String toString() {
		return this.author+" "+this.year+" "+this.license;
	}
}
