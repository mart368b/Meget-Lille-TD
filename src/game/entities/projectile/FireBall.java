package game.entities.projectile;

import java.awt.Graphics2D;

import game.entities.enemies.Enemy;
import game.math.Point2D;
import gfx.particles.MovingParticleSystem;
import gfx.particles.ParticleSystemLibary;

public class FireBall extends FlyingProjectile{

	private MovingParticleSystem ps;
	
	public FireBall(Point2D p0, Enemy target, double speed, double maxRange, int damage) {
		super(p0, target, speed, maxRange, damage);
		ps = new MovingParticleSystem(p0.getX(), p0.getY(), ParticleSystemLibary.FIRE.getValue());
		ps.setDirection(vec);
	}
	
	@Override
	public void render(Graphics2D g2) {
		ps.render(g2);
	}

}
