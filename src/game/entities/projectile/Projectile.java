package game.entities.projectile;

import game.entities.Entity;
import game.entities.enemies.Enemy;
import game.math.Point2D;
import game.state.states.EnemyHandler;

public class Projectile extends Entity {
	
	protected double progress = 0, speed;
	protected boolean hit, alive = true;
	protected Enemy target;

	public Projectile(Point2D p0, Enemy target, double speed) {
		super(p0.getX(), p0.getY());
		this.speed = speed;
		this.target = target;
	}
		
	public void tick(EnemyHandler eh) {}
	
	protected void kill() {
		alive = false;
	}
	
	protected void hit() {}

	public boolean isDead() {
		return !alive;
	}
}
