package org.casaca.gpx4j.core.data;

import org.casaca.gpx4j.core.util.SortedList;

public class TrackSegment extends BaseObject {
	private SortedList<Waypoint> waypoints;
	private Extensions extensions;
	
	public TrackSegment(){
		this.waypoints = new SortedList<Waypoint>();
		this.extensions = new Extensions();
	}

	public SortedList<Waypoint> getWaypoints() {
		return this.waypoints;
	}

	public void setWaypoints(SortedList<Waypoint> waypoints) {;
		this.waypoints = waypoints;
	}

	public Extensions getExtensions() {
		return extensions;
	}

	public void setExtensions(Extensions extensions) {
		this.extensions = extensions;
	}
}
