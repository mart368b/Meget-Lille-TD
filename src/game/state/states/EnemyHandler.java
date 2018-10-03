package game.state.states;

import java.awt.Graphics2D;
import java.util.ArrayList;

import game.entities.enemies.Enemy;

public class EnemyHandler {
	
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private int lifeLost = 0;
	
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
					lifeLost++;
				}
			}
		}
	}
	
	public void render(Graphics2D g2) {
		for (Enemy e: enemies) {
			e.render(g2);
		}
	}
}
