package org.casaca.gpx4j.core.data;

public class Copyright extends BaseObject {
	private Integer year;
	private String author;
	private String license;
	
	public Copyright(Integer year, String author, String license){
		this.year = year;
		this.author = author;
		this.license = license;
	}

	public Integer getYear() {
		return year;
	}

	public String getAuthor() {
		return author;
	}

	public String getLicense() {
		return license;
	}
}
