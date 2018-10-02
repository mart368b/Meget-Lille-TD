package game.level;

import java.util.ArrayList;

import game.math.Point2D;
import game.math.Vector2D;

public class WalkingPath {
	
	private Point2D[] points;

	public WalkingPath(Point2D[] points) {
		this.points = points;
	}
	
	public Point2D[] clone() {
		return points.clone();
	}
}
