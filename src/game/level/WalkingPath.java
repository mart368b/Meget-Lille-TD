package game.level;

import java.nio.file.Paths;
import java.util.ArrayList;

import game.math.Point2D;
import game.math.Vector2D;

public class WalkingPath {
	
	private ArrayList<Point2D> points = new ArrayList<Point2D>();
	//TODO add entity count lose condition
	@SuppressWarnings("unused")
	private boolean loop = false;
	@SuppressWarnings("unused")
	private int loopIndex;

	public WalkingPath() {}
	
	public void addPoint(Point2D p) {
		points.add(p);
	}
	
	public int indexOf(Point2D p0) {
		for (int i = 0; i < points.size(); i++) {
			if (p0.equals(points.get(i))) {
				return i;
			}
		}
		return -1;
	}
	
	public Point2D getPosition (double t) {
		int t0 = (int) Math.floor(t);
		//check if t > paths.size()
		if (t0 < 0) {
			return points.get(0);
		}
		
		int t1 = (int) Math.ceil(t);
		//check if t < 0
		if (t1 >= points.size()) {
			return points.get(points.size() - 1);
		}
		
		Point2D p0 = points.get(t0);
		
		//check if exactly on point
		if (t0 == t1) {
			return p0; 
		}
		
		Point2D p1 = points.get(t1);
		//create vector between point
		Vector2D vec = new Vector2D(p0, p1);
		//get current progress between p0 and p1
		double progress = t - t0;
		
		//return a point based on progress made
		vec.scale(progress);
		Point2D p = p0.clone();
		p.translate(vec);
		return p;
	}
	
	public double getStartingX() {
		return points.get(0).getX();
	}
	
	public double getStartingY() {
		return points.get(0).getY();
	}
	
	public int getLength() {
		return points.size();
	}
	
	public WalkingPath clone() {
		WalkingPath newPath = new WalkingPath();
		for (Point2D p: points) {
			newPath.addPoint(p);
		}
		return newPath;
	}
	
	public String toString() {
		String output = "";
		for (Point2D p: points) {
			output += p.toString() + " ";
		}
		return output;
	}
	
}
