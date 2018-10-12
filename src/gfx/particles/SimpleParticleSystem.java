package gfx.particles;

import java.awt.Color;

import game.math.Vector2D;

public class SimpleParticleSystem extends ParticleSystem{
	
	private double x0, y0;
	
	public SimpleParticleSystem(double x0, double y0) {
		this.x0 = x0;
		this.y0 = y0;
	}
	
	@Override
	public void play() {
		addParticle(new Particle(x0, y0, new Vector2D(0, 0.2), 20, 5, Color.BLACK));
	}

}
