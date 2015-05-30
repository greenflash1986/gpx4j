package org.casaca.gpx4j.core.data;

import java.net.URL;

public class Link extends BaseObject {
	private String text;
	private String type;
	private URL href;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public URL getHref() {
		return href;
	}
	public void setHref(URL href) {
		this.href = href;
	}
}
