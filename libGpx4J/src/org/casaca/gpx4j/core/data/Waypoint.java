package org.casaca.gpx4j.core.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Waypoint extends BaseObject {
	//POSITION INFO
	private BigDecimal elevation;
	private Calendar time;
	private Degrees magvar;
	private BigDecimal geoIdHeight;
	private BigDecimal latitude;
	private BigDecimal longitude;
	
	//DESCRIPTION INFO
	private String name;
	private String cmt;
	private String description;
	private String src;
	private List<Link> links;
	private String sym;
	private String type;
	
	//ACCURACY INFO
	private Fix fix;
	private BigInteger sat;
	private BigDecimal hdop;
	private BigDecimal vdop;
	private BigDecimal pdop;
	private BigDecimal ageOfDgpsData;
	private DgpsStation dGpsId;
	private Extensions extensions;
	
	public Waypoint(BigDecimal latitude, BigDecimal longitude) throws IllegalArgumentException{
		if(latitude == null || longitude == null) throw new IllegalArgumentException("Error creating waypoint. Latitude and longitude must not be null");
		
		this.elevation = null;
		this.time = null;
		this.magvar = null;
		this.geoIdHeight = null;
		this.latitude = latitude;
		this.longitude = longitude;
		
		this.name = null;
		this.cmt = null;
		this.description = null;
		this.src = null;
		this.links = new ArrayList<Link>();
		this.sym = null;
		this.type = null;
		
		this.fix = null;
		this.sat = null;
		this.hdop = null;
		this.vdop = null;
		this.pdop = null;
		this.ageOfDgpsData = null;
		this.dGpsId = null;
		this.extensions = new Extensions();
	}

	public BigDecimal getElevation() {
		return elevation;
	}

	public void setElevation(BigDecimal elevation) {
		this.elevation = elevation;
	}

	public Calendar getTime() {
		return time;
	}

	public void setTime(Calendar time) {
		this.time = time;
	}

	public Degrees getMagvar() {
		return magvar;
	}

	public void setMagvar(Degrees magvar) {
		this.magvar = magvar;
	}

	public BigDecimal getGeoIdHeight() {
		return geoIdHeight;
	}

	public void setGeoIdHeight(BigDecimal geoIdHeight) {
		this.geoIdHeight = geoIdHeight;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	public void addLink(Link link){
		this.links.add(link);
	}

	public String getSym() {
		return sym;
	}

	public void setSym(String sym) {
		this.sym = sym;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Fix getFix() {
		return fix;
	}

	public void setFix(Fix fix) {
		this.fix = fix;
	}

	public BigInteger getSat() {
		return sat;
	}

	public void setSat(BigInteger sat) {
		this.sat = sat;
	}

	public BigDecimal getHdop() {
		return hdop;
	}

	public void setHdop(BigDecimal hdop) {
		this.hdop = hdop;
	}

	public BigDecimal getVdop() {
		return vdop;
	}

	public void setVdop(BigDecimal vdop) {
		this.vdop = vdop;
	}

	public BigDecimal getPdop() {
		return pdop;
	}

	public void setPdop(BigDecimal pdop) {
		this.pdop = pdop;
	}

	public BigDecimal getAgeOfDgpsData() {
		return ageOfDgpsData;
	}

	public void setAgeOfDgpsData(BigDecimal ageOfDgpsData) {
		this.ageOfDgpsData = ageOfDgpsData;
	}

	public DgpsStation getdGpsId() {
		return dGpsId;
	}

	public void setdGpsId(DgpsStation dGpsId) {
		this.dGpsId = dGpsId;
	}

	public Extensions getExtensions() {
		return extensions;
	}

	public void setExtensions(Extensions extensions) {
		this.extensions = extensions;
	}
}
