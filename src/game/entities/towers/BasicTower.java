package game.entities.towers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import game.entities.enemies.Enemy;
import game.entities.projectile.FlyingProjectile;
import game.math.Point2D;
import game.math.Vector2D;
import game.state.states.EnemyHandler;
import game.state.states.player.TowerHandler;
import gfx.particles.DirectionalParticleSystem;
import gfx.particles.ParticleSystemLibary;

public class BasicTower extends Tower {
	
	protected Enemy target;
	protected int[] range;
	private double shootCooldown = 0;
	protected TowerHandler th;
	protected DirectionalParticleSystem dps;

	public BasicTower(int[] damage, int[] cost, int[] speed, String[] lore, int[] range, int x, int y) {
		super(damage, cost, speed, lore, x, y, 20, 0);
		this.range = range;
	}
	
	public BasicTower(int[] damage, int[] cost, int[] speed, String[] lore, int[] range, int x, int y, boolean placed, TowerHandler th) {
		super(damage, cost, speed, lore, x, y, 20, 0, placed);
		if (placed) {
			Point2D center = getCenter();
			if (hasParticleSystem()) {
				dps = new DirectionalParticleSystem(center.getX(), center.getY(), ParticleSystemLibary.SHOT.getValue());
			}
		}
		this.range = range;
		this.th = th;
	}
	
	public BasicTower clone(double x, double y, TowerHandler th) {
		return new BasicTower(damage, cost, speed, lore, range,(int) x,(int) y, true, th);
	}
	
	public int getRange() {
		return range[lvl];
	}
	
	@Override
	public void tick(EnemyHandler enemyHandler) {
		super.tick(enemyHandler);
		
		if (dps != null) {
			dps.tick();
		}
		
		if (target != null && (target.getCenter().distance(getCenter()) > getRange() || target.isDead() || target.reachedEnd())) {
			target = null;
		}
		if (target == null) {
			ArrayList<Enemy> enemies = enemyHandler.getEnemiesWithinCircle( getCenter(), getRange());
			if (enemies.size() == 0) {
				return;
			}
			double maxProgress = 0;
			for (int i = 0; i < enemies.size(); i++) {
				Enemy e = enemies.get(i);
				double progress = e.getProgress();
				if (!e.reachedEnd() && progress > maxProgress) {
					maxProgress = progress;
					target = e;
				}
			}
		}
		
		if (shootCooldown >= 60) {
			shootCooldown -= 60;
			shoot();
		}
		shootCooldown += getSpeed()/2.;
		
	}
	
	private void shoot() {
		Vector2D vec = new Vector2D(getCenter(), target.getCenter());
		if (hasParticleSystem()) {
			dps.setDirection(vec);
			dps.play();
		}
		shootProjectile();
	}

	protected void shootProjectile() {
		th.addProjectile(new FlyingProjectile(getCenter(), target, 6, getRange(), getDamage()));		
	}

	public boolean hasParticleSystem() {
		return true;
	}
	
	public void render(Graphics2D g2){
		super.render(g2);
		g2.setColor(Color.WHITE);
		Point2D cp = getCenter();
		if (marked && placed || held) {
			int range = getRange();
			g2.draw(new Ellipse2D.Double(cp.getX() - range, cp.getY() - range, range*2, range*2));
		}
		if (dps != null) {
			dps.render(g2);
		}
	}
}
