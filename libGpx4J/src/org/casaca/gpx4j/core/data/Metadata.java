package org.casaca.gpx4j.core.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Metadata extends BaseObject {
	private String name;
	private String desc;
	private Person author;
	private Copyright copyright;
	private List<Link> links;
	private Calendar date;
	private String keywords;
	private Bounds bounds;
	private Extensions extensions;
	
	public Metadata(){
		this.name = null;
		this.desc = null;
		this.author = null;
		this.copyright = null;
		this.links = new ArrayList<Link>();
		this.date = null;
		this.keywords = null;
		this.bounds = null;
		this.extensions = new Extensions();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Person getAuthor() {
		return author;
	}

	public void setAuthor(Person author) {
		this.author = author;
	}

	public Copyright getCopyright() {
		return copyright;
	}

	public void setCopyright(Copyright copyright) {
		this.copyright = copyright;
	}

	public List<Link> getLinks() {
		return links;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Bounds getBounds() {
		return bounds;
	}

	public void setBounds(Bounds bounds) {
		this.bounds = bounds;
	}

	public Extensions getExtensions() {
		return extensions;
	}
}
