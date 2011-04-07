package org.casaca.gpx4j.core.data;

import org.casaca.gpx4j.core.util.SortedList;

public class PointsSequence extends BaseObject {
	private SortedList<Point> points;
	
	public PointsSequence(){
		this.points = new SortedList<Point>();
	}

	public SortedList<Point> getPoints() {
		return points;
	}
}
