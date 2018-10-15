package gfx.particles;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Particle {
	
	public int t, color;
	public double vx, vy;
	
	public Particle(double vx, double vy, int t0, int color) {
		this.t = t0;
		this.vx = vx;
		this.vy = vy;
		this.color = color;
	}
	
	public Particle( int t, double vx, double vy, int color) {
		this.t = t;
		this.vx = vx;
		this.vy = vy;
		this.color = color;
	}
	
	public boolean age(int maxAge) {
		return ++t == maxAge;
	}
	
	public double getX(double gravity) {
		return vx * t;
	}
	
	public double getY(double gravity) {
		return vy * t + gravity * Math.pow(t, 2);
	}

	public void render(Graphics2D g2, double xOffset, double yOffset, ParticleSystemSettings settings) {
		double progress = (t + 0.) / settings.particleMaxLifeTimer;
		double invProgress = 1 - progress;
		double r = settings.particleVariationSize * invProgress + settings.initialParticleSize;
		g2.setColor(settings.palet.colors[color]);
		g2.fill(new Ellipse2D.Double(xOffset + getX(settings.gravity) - r, yOffset + getY(settings.gravity) - r, r*2, r*2));
	}

}
