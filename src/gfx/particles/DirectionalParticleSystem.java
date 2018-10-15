package gfx.particles;

import game.math.Vector2D;

public class DirectionalParticleSystem extends ParticleSystem{
	
	protected double angle;
	
	public DirectionalParticleSystem(double x0, double y0, ParticleSystemSettings settings) {
		super(x0, y0, settings);
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	public void setDirection(Vector2D vec) {
		vec.normalize();
		if (vec.getY() > 0) {
			setAngle(Math.PI*2- Math.acos(vec.getX()));
		}else{
			setAngle(Math.acos(vec.getX()));
		}
	}

	@Override
	public double getXVelocity(double deviation) {
		return Math.cos(deviation - settings.deviation/2 - angle) * settings.velocity;
	}
	
	@Override
	public double getYVelocity(double deviation) {
		return Math.sin(deviation - settings.deviation/2 - angle) * settings.velocity;
	}

}
