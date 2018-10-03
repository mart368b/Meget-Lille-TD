package game.math;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * Acts as a point in 2d
 * @author mart368b
 *
 */
public class Point2D{
	
	private double x, y; 
	//display radius
	public static double d = 3;
	
	/**
	 * Create a new Point
	 */
	public Point2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Create a deep copy of the instance
	 */
	public Point2D clone() {
		return new Point2D(x, y);
	}
	
	/**
	 * Move the point to a given position
	 */
	public void moveTo(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Move the point to a given position
	 */
	public void moveTo(Point2D p) {
		this.x = p.getX();
		this.y = p.getY();
	}
	
	/**
	 * Move the point by a specific amount
	 */
	public void translate (double vx, double vy) {
		this.x += vx;
		this.y += vy;
	}
	
	/**
	 * Move the point using a Vector to describe its movement
	 */
	public void translate (Vector2D v) {
		this.x += v.getX();
		this.y += v.getY();
	}
	
	/**
	 * Return the distance from a point specified by its x and y value
	 */
	public double distance (double x, double y) {
		return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
	}
	
	/**
	 * Return the distance from a given Point
	 */
	public double distance (Point2D p) {
		return Math.sqrt(Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2));
	}
	
	/**
	 * Render the point
	 */
	public void render(Graphics2D g2) {
		g2.draw(new Ellipse2D.Double(x - d/2, y - d/2, d, d));
	}
	
	/**
	 * Get the point as a string representation
	 * @return output represented as: "( x, y)"
	 */
	public String toString() {
		return "( " + String.format("%.2f", x) + ", " + String.format("%.2f", y) + ")";
	}
	
	/**
	 * Rotate point around a secondary point by a specified angle
	 */
	public void rotateAroundOrigin(double x0, double y0, double angle) {
		double lx = x;
		double ly = y;
		double c = Math.cos(angle);
		double s = Math.sin(angle);
		x = c * (lx - x0) - s * (ly - y0) + x0;
		y = s * (lx - x0) + c * (ly - y0) + y0;
	}
	
	/**
	 * Rotate point around a secondary point by a specified angle
	 */
	public void rotateAroundOrigin(Point2D p, double angle) {
		double lx = x;
		double ly = y;
		double c = Math.cos(angle);
		double s = Math.sin(angle);
		x = c * (lx - p.x) - s * (ly - p.y) + p.x;
		y = s * (lx - p.x) + c * (ly - p.y) + p.y;
	}
	
	/**
	 * Calculate the distance from a line described by the points p0 and p1
	 */
	public double distanceFromLine(Point2D p0, Point2D p1) {
		return ((p1.y - p0.y)*x - (p1.x - p0.x)*y + p1.x * p0.y - p1.y * p0.x) / Math.sqrt(Math.pow((p1.y - p0.y), 2) + Math.pow(p1.x - p0.x, 2));
	}
	
	public boolean equals(Point2D p) {
		return p.getX() == x && p.getY() == y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}


}
