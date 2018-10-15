package game.state.states.player;

import java.util.ArrayList;
import java.util.Iterator;

import game.entities.enemies.Enemy;
import game.level.Map;
import game.level.PathFinder;

public class Round {

	private int enemyCount, releasedCount = 0;
	private Enemy[] enemyTypes;
	private int currentEnemy = 0;
	
	private int winbonus;
	
	public Round(Enemy[] ent, int enemyCount){
		this.enemyCount = enemyCount;
		this.enemyTypes = ent;
	}
	
	/**
	 * Spawn a Basic enemy on every spawn point
	 */
	public Enemy[] getWave(Map map){
		if (releasedCount >= enemyCount) {
			return null;
		}
		//Get spawnlocation from map and spawn enemies
		Iterator<int[]> ite = map.getSpawnPointIterator();
		// go over all spawn points
		int[] spawnPoint;
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		int i = 0;
		while (ite.hasNext()) {
				spawnPoint = ite.next();
				// get path from that spawnpoint
				PathFinder paths = map.getPathFinder(spawnPoint);
				Enemy newEnt = enemyTypes[currentEnemy++].clone(paths.nextPath());
				enemies.add(newEnt);
				if (currentEnemy >= enemyTypes.length) {
					currentEnemy = 0;
				}
				if (++releasedCount >= enemyCount) {
					Enemy[] enemyArray = new Enemy[enemies.size()];
					return enemies.toArray(enemyArray);
				}
		}
		Enemy[] enemyArray = new Enemy[enemies.size()];
		return enemies.toArray(enemyArray);
	}
	
	public void reset() {
		releasedCount = 0;
		currentEnemy = 0;
	}
	
	public boolean reachedEnd() {
		return releasedCount >= enemyCount;
	}
	
	//Track and see if all enemies are killed
}
