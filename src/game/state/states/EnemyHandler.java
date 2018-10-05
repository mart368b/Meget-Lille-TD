package game.state.states;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

import game.entities.enemies.Enemy;
import game.entities.tiles.Tile;
import game.math.Point2D;

public class EnemyHandler {
	
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private int lifesLost = 0;
	
	public EnemyHandler() {}

	public void addEnemy(Enemy e) {
		enemies.add(e);
	}
	
	public void addEnemyWave(Enemy[] wave) {
		for (Enemy e: wave) {
			enemies.add(e);
		}
	}
	
	public void tick() {
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			if (!e.isActive()) {
				continue;
			}
			if (e.isDead()) {
				enemies.remove(i);
				i--;
			}else {
				e.tick();
				if (e.reachedEnd()) {
					enemies.remove(i);
					i--;
					lifesLost++;
				}
			}
		}
	}
	
	public boolean isEmpty() {
		return enemies.size() == 0;
	}
	
	public void render(Graphics2D g2) {
		Iterator<Enemy> ite = enemies.stream().sorted((e1, e2)->{return (int) (e1.getY() - e2.getY());}).iterator();
		while (ite.hasNext()) {
			ite.next().render(g2);
		}
	}
	
	public int getLifesLost() {
		int l = lifesLost;
		lifesLost = 0;
		return l;
	}

	public ArrayList<Enemy> getEnemiesWithinCircle(Point2D point2d, double range) {
		ArrayList<Enemy> enemiesWithinRange = new ArrayList<Enemy>();
		for (Enemy e: enemies) {
			if (e.getCenter().distance(point2d) < range) {
				enemiesWithinRange.add(e);
			}
		}
		return enemiesWithinRange;
	}
}
