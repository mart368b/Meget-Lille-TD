package game.math;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 * Acts a directional vector in 2d space. Going from ( 0, 0) to ( x, y)
 * @author mart368b
 *
 */
public class Vector2D {
	
	private double x, y;
	public static final Vector2D HORIZONTALVECTOR = new Vector2D(1, 0);
	public static final Vector2D VERTICALVECTOR = new Vector2D(0, -1);

	public Vector2D(double x, double y) {
		setTarget(x, y);
	}
	
	public Vector2D(double x0, double y0, double x1, double y1) {
		setTarget(x0, y0, x1, y1);
	}
	
	public Vector2D(Point2D p0, Point2D p1) {
		setTarget(p0, p1);
	}
	
	/**
	 * Update vector direction
	 */
	public void setTarget(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Update vector direction
	 */
	public void setTarget(Point2D p1, Point2D p2) {
		this.x = p2.getX() - p1.getX();
		this.y = p2.getY() - p1.getY();
	}
	
	/**
	 * Update vector direction
	 */
	public void setTarget(double x0, double y0, double x1, double y1) {
		this.x = x1-x0;
		this.y = y1-y0;
	}

	/**
	 * @return magnitude of vector 
	 */
	public double getMagnitude() {
		return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	/**
	 * Normalize the vector by making the magnitud = 1
	 */
	public void normalize() {
		double len = getMagnitude();
		x = x/len;
		y = y/len;
	}
	
	/**
	 * Scale the vector by a konstant
	 * @param k - Kontanst to be scaled with
	 */
	public void scale(double k) {
		x *= k;
		y *= k;
	}
	
	/**
	 * Rotate the vector 90° counter clockwise
	 */
	public void rotateCounterClockwise() {
		Double temp = this.x;
		
		x = y;
		y = -temp;
	}
	
	/**
	 * Rotate the vector 90° clockwise
	 */
	public void rotateClockwise() {
		Double temp = y;
		
		y = x;
		x = -temp;
	}
	
	/**
	 * Get the Vector as a string representation
	 * @return output represented as: "( x, y)"
	 */
	public String toString() {
		return "( " + String.format("%.2f", x) + ", " + String.format("%.2f", y) + ")";
	}
	
	/**
	 * Render vector based on a point of origin
	 */
	public void draw(Graphics2D g2, double x0, double y0) {
		g2.draw(new Line2D.Double(x0, y0, x0 + x, y0 + y));
	}
	
	/**
	 * Render vector based on a point of origin
	 */
	public void draw(Graphics2D g2, Point2D p0) {
		g2.draw(new Line2D.Double(p0.getX(), p0.getY(), p0.getX() + this.x, p0.getY() + this.y));
	}
	
	/**
	 * Get angle between two vectors
	 */
	public double angleBetweenVector( Vector2D v1) {
		return Math.acos(( (v1.getX() * x) + (v1.getY() * y) )/( v1.getMagnitude() * getMagnitude() ));
	}

}
