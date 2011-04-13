package org.casaca.gpx4j.core.data;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.casaca.gpx4j.core.util.SortedList;

public class Route extends BaseObject {
	private String name;
	private String cmt;
	private String desc;
	private String src;
	private List<Link> links;
	private BigInteger number;
	private String type;
	private Extensions extensions;
	private SortedList<Waypoint> waypoints;
	
	public Route(){
		this.name = null;
		this.cmt = null;
		this.desc = null;
		this.src = null;
		this.links = new ArrayList<Link>();
		this.number = null;
		this.type = null;
		this.extensions = new Extensions();
		this.waypoints = new SortedList<Waypoint>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCmt() {
		return cmt;
	}

	public void setCmt(String cmt) {
		this.cmt = cmt;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public List<Link> getLinks() {
		return links;
	}

	public BigInteger getNumber() {
		return number;
	}

	public void setNumber(BigInteger number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Extensions getExtensions() {
		return extensions;
	}

	public SortedList<Waypoint> getWaypoints() {
		return this.waypoints;
	}
}