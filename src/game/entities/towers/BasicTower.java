package game.entities.towers;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import game.entities.enemies.Enemy;
import game.entities.tiles.Tile;
import game.math.Point2D;
import game.state.states.EnemyHandler;

public class BasicTower extends Tower {
	
	private double range = 200;
	private Enemy target;
	private int shootCooldown = 0;

	public BasicTower(int[] damage, int[] cost, int[] speed, String[] lore, int x, int y) {
		super(damage, cost, speed, lore, x, y, 20, 0);
	}
	
	public BasicTower(int[] damage, int[] cost, int[] speed, String[] lore, int x, int y, boolean placed) {
		super(damage, cost, speed, lore, x, y, 20, 0, placed);
	}
	
	public BasicTower clone(double x, double y) {
		return new BasicTower(damage, cost, speed, lore,(int) x,(int) y, true);
	}
	
	@Override
	public void tick(EnemyHandler enemyHandler) {
		super.tick(enemyHandler);
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
			target.hit(getDamage());
			if (target.isDead()) {
				target = null;
			}
		}
		shootCooldown += getSpeed();
		
	}
	
	public void render(Graphics2D g2){
		super.render(g2);
		Point2D cp = getCenter();
		if (marked && placed || held) {
			g2.draw(new Ellipse2D.Double(cp.getX() - range, cp.getY() - range, range*2, range*2));
		}
		if (target != null) {
			Point2D ecp = target.getCenter();
			g2.drawLine((int)cp.getX(),(int)cp.getY(),(int) ecp.getX(),(int) ecp.getY());
		}
	}
}
