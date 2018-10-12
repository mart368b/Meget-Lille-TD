package game.entities.towers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import game.entities.enemies.Enemy;
import game.entities.projectile.FlyingProjectile;
import game.entities.projectile.Projectile;
import game.math.Point2D;
import game.state.states.EnemyHandler;
import game.state.states.player.TowerHandler;
import gfx.particles.ParticleSystem;
import gfx.particles.SimpleParticleSystem;

public class BasicTower extends Tower {
	
	private double range = 200;
	private Enemy target;
	private double shootCooldown = 0;
	private long stopShotTime;
	private TowerHandler th;
	private ParticleSystem particleSystem;

	public BasicTower(int[] damage, int[] cost, int[] speed, String[] lore, int x, int y) {
		super(damage, cost, speed, lore, x, y, 20, 0);
	}
	
	public BasicTower(int[] damage, int[] cost, int[] speed, String[] lore, int x, int y, boolean placed, TowerHandler th) {
		super(damage, cost, speed, lore, x, y, 20, 0, placed);
		this.th = th;
		if (placed) {
			particleSystem = new SimpleParticleSystem(getX(), getY());			
		}
	}
	
	public BasicTower clone(double x, double y, TowerHandler th) {
		return new BasicTower(damage, cost, speed, lore,(int) x,(int) y, true, th);
	}
	
	@Override
	public void tick(EnemyHandler enemyHandler) {
		super.tick(enemyHandler);
		if (placed) {
			particleSystem.tick();
		}
		if (target != null && (target.getCenter().distance(getCenter()) > range || target.isDead())) {
			target = null;
		}
		if (target == null) {
			ArrayList<Enemy> enemies = enemyHandler.getEnemiesWithinCircle( getCenter(), range);
			if (enemies.size() == 0) {
				return;
			}
			double maxProgress = 0;
			for (int i = 0; i < enemies.size(); i++) {
				Enemy e = enemies.get(i);
				double progress = e.getProgress();
				if (progress > maxProgress) {
					maxProgress = progress;
					target = e;
				}
			}
		}
		
		if (shootCooldown >= 60) {
			shootCooldown -= 60;
			particleSystem.play();
			th.addProjectile(new FlyingProjectile(getCenter(), target, 6, range, getDamage()));
		}
		shootCooldown += getSpeed()/2.;
		
	}
	
	public void render(Graphics2D g2){
		super.render(g2);
		g2.setColor(Color.WHITE);
		Point2D cp = getCenter();
		if (marked && placed || held) {
			g2.draw(new Ellipse2D.Double(cp.getX() - range, cp.getY() - range, range*2, range*2));
		}
		if (placed) {
			particleSystem.render(g2);
		}
	}
}
