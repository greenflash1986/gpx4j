package org.casaca.gpx4j.core.data;

import java.util.ArrayList;
import java.util.List;

public class TrackSegment extends BaseObject {
	private List<Waypoint> waypoints;
	private Extensions extensions;
	
	public TrackSegment(){
		this.waypoints = new ArrayList<Waypoint>();
		this.extensions = new Extensions();
	}

	public List<Waypoint> getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(List<Waypoint> waypoints) {
		this.waypoints = waypoints;
	}
	
	public void addWaypoint(Waypoint waypoint){
		this.waypoints.add(waypoint);
	}

	public Extensions getExtensions() {
		return extensions;
	}

	public void setExtensions(Extensions extensions) {
		this.extensions = extensions;
	}
}
