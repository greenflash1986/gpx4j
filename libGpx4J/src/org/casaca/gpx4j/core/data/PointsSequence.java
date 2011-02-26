package org.casaca.gpx4j.core.data;

import java.util.ArrayList;
import java.util.List;

public class PointsSequence extends BaseObject {
	private List<Point> points;
	
	public PointsSequence(){
		this.points = new ArrayList<Point>();
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}
	
	public void addPoint(Point point){
		this.points.add(point);
	}
}
