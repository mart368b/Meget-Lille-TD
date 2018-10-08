package game.entities.projectile;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import game.Game;
import game.entities.Entity;
import game.entities.enemies.Enemy;
import game.math.Point2D;

public class Projectile extends Entity{

	private double x0, y0, x1, y1, lx, ly;
	private Enemy target;
	private int damage;
	private double progress = 0, speed;
	private boolean hit, alive = true;
	
	public Projectile(Point2D p0, Enemy target, double speed, int damage) {
		super(p0.getX(), p0.getY());
		x0 = p0.getX();
		y0 = p0.getY();
		x1 = target.getX();
		y1 = target.getY();
		lx = x0;
		ly = y0;
		this.speed = speed/Game.tps;
		this.damage = damage;
	}
	
	@Override
	public void tick() {
		if (hit) {
			alive = false;
		}
		updatePosition();
		if (progress >= 1) {
			if (!hit) {
				target.hit(damage);
			}
			hit = true;
		}
	}
	
	public void updatePosition() {
		progress += speed;
		double nx = x0*progress + x1*(1 - progress);
		double ny = x0*progress + y1*(1 - progress);
		lx = getX();
		ly = getY();
		moveTo(nx, ny);
	}
	
	public void render(Graphics2D g2) {
		g2.draw(new Rectangle2D.Double(getX(), getY(), lx, ly));
	}
	
	public boolean isDead() {
		return !alive;
	}

}

