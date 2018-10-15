package game.entities.projectile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;

import game.Game;
import game.entities.enemies.Enemy;
import game.math.Point2D;
import game.math.Vector2D;
import game.state.states.EnemyHandler;

public class FlyingProjectile extends Projectile{

	private int damage;
	protected Vector2D vec;
	private int maxLifeSpane, lifeSpan;
	private Point2D[] trail;
	
	public FlyingProjectile(Point2D p0, Enemy target, double speed, double maxRange, int damage) {
		super(p0, target, speed);
		this.speed = speed/Game.tps;
		this.damage = damage;
		trail = new Point2D[3];
		for (int i = 0; i < trail.length; i++) {
			trail[i] = ((Point2D) p0).clone();
		}
		vec = new Vector2D(p0, target.getCenter());
		vec.normalize();
		vec.scale(speed);
		maxLifeSpane = (int) Math.ceil(maxRange/speed);
	}
	
	public boolean hasTrail() {
		return trail != null;
	}
	
	@Override
	public void tick(EnemyHandler eh) {
		if (lifeSpan++ > maxLifeSpane) {
			kill();
		}
		updatePosition();
		
		Enemy collidedEnemy = eh.getEnemyWithinCircle(this, 32);
		if(alive && collidedEnemy != null) {
			kill();
			collidedEnemy.hit(damage);
		}
	}

	protected void hit() {
		target.hit(damage);
	}
	
	public void updatePosition() {
		translate(vec);
		if (hasTrail()) {
			for (int i = 0; i < trail.length - 1; i++) {
				trail[i] = trail[i + 1];
			}
			trail[trail.length - 1] = ((Point2D) this).clone();
		}
	}
	
	public void render(Graphics2D g2) {
		if (!hit && hasTrail()) {
			Stroke s = g2.getStroke();
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(3));
			Point2D p = trail[0];
			g2.draw(new Line2D.Double(getX(), getY(), p.getX(), p.getY()));
			g2.setStroke(s);
		}
	}

}

