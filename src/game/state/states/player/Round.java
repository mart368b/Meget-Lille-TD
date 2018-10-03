package game.state.states.player;

import java.util.ArrayList;
import java.util.Iterator;

import game.Game;
import game.entities.Entity;
import game.entities.enemies.BasicEnemy;
import game.entities.enemies.Enemy;
import game.level.Map;
import game.level.PathFinder;

public class Round {

	private int id, enemyCount, releasedCount = 0;
	private Map map;
	private Enemy[] enemyTypes;
	private int currentEnemy = 0;
	
	private int winbonus;
	
	public Round(int roundnumber, Map map, Enemy[] ent, int enemyCount){
		this.id = roundnumber;
		this.enemyCount = enemyCount;
		this.map = map;
		this.enemyTypes = ent;
	}
	
	/**
	 * Spawn a Basic enemy on every spawn point
	 */
	public Enemy[] getWave(){
		if (releasedCount >= enemyCount - 1) {
			return null;
		}
		//Get spawnlocation from map and spawn enemies
		Iterator<int[]> ite = map.getSpawnPointIterator();
		// go over all spawn points
		int[] spawnPoint;
		Enemy[] enemies = new Enemy[map.getSpawnPointCount()];
		int i = 0;
		while (ite.hasNext()) {
				spawnPoint = ite.next();
				// get path from that spawnpoint
				PathFinder paths = map.getPathFinder(spawnPoint);
				Enemy newEnt = enemyTypes[currentEnemy++].clone(paths.nextPath());
				enemies[i++] = newEnt;
				if (++releasedCount >= enemyCount - 1) {
					return enemies;
				}
		}
		if (currentEnemy >= enemyTypes.length) {
			currentEnemy = 0;
		}
		return enemies;
	}
	
	public boolean reachedEnd() {
		return releasedCount >= enemyCount - 1;
	}
	
	//Track and see if all enemies are killed
}
