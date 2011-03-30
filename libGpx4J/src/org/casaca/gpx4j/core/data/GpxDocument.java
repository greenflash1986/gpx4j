package org.casaca.gpx4j.core.data;

import java.util.ArrayList;
import java.util.List;

import org.casaca.gpx4j.core.util.Constants;
import org.casaca.gpx4j.core.util.SortedList;

public class GpxDocument extends BaseObject {
	private String version;
	private String creator;
	
	private List<Track> tracks;
	private SortedList<Waypoint> waypoints;
	private List<Route> routes;
	private Extensions extensions;
	private Metadata metadata;
	
	public GpxDocument(){
		this.version = Constants.APPLICATION_GPX_VERSION;
		this.creator = Constants.APPLICATION_NAME;
		this.tracks = new ArrayList<Track>();
		this.waypoints = new SortedList<Waypoint>();
		this.routes = new ArrayList<Route>();
		this.extensions = null;
		this.metadata = null;
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public List<Track> getTracks() {
		return tracks;
	}
	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}
	public void addTrack(Track track){
		this.tracks.add(track);
	}
	public SortedList<Waypoint> getWaypoints() {
		return waypoints;
	}
	public void setWaypoints(SortedList<Waypoint> waypoints) {
		this.waypoints = waypoints;
	}
	public List<Route> getRoutes() {
		return routes;
	}
	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}
	public void addRoute(Route route){
		this.routes.add(route);
	}
	public Extensions getExtensions() {
		return extensions;
	}
	public void setExtensions(Extensions extensions) {
		this.extensions = extensions;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
}
