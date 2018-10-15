package gfx.particles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import game.math.Point2D;
import game.math.Vector2D;

public class ParticleD extends Point2D{

	public boolean alive = true;
	public Color c, currentColor;
	private Vector2D vec;
	private int lifeTime, maxLifeTime;
	private double size;
	
	public ParticleD(double x, double y, Vector2D vec, int maxLifeTime, double size, Color c) {
		super(x, y);
		this.vec = vec;
		this.maxLifeTime = maxLifeTime;
		this.size = size;
		this.c = c;
		currentColor = c;
	}
	
	public void tick() {
		if (!alive) {
			return;
		}else {
			currentColor = getColor();
		}
		this.translate(vec);
		if (lifeTime++ > maxLifeTime) {
			kill();
		}
	}
	
	public void render(Graphics2D g2) {
		double progress = 1 - (lifeTime + 0.)/maxLifeTime;
		double currentSize = size * progress;
		Color lc = g2.getColor();
		g2.setColor(Color.RED);
		g2.fill(new Ellipse2D.Double(getX() - currentSize/2, getY() - currentSize/2, currentSize, currentSize));
		g2.setColor(lc);
	}
	
	public Color getColor() {
		return new Color(c.getRed(), c.getGreen(), c.getBlue(), lifeTime/maxLifeTime);
	}
	
	public void kill() {
		alive = false;
	}
	
	public boolean isDead() {
		return !alive;
	}

}
