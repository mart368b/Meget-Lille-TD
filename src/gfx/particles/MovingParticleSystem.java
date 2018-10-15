package gfx.particles;

public class MovingParticleSystem extends DirectionalParticleSystem {

	public MovingParticleSystem(double x0, double y0, ParticleSystemSettings settings) {
		super(x0, y0, settings);
	}
	
	@Override
	public double getXVelocity(double deviation) {
		return -Math.cos(deviation - settings.deviation/2 - angle) * settings.velocity;
	}
	
	@Override
	public double getYVelocity(double deviation) {
		return -Math.sin(deviation - settings.deviation/2 - angle) * settings.velocity;
	}

}
