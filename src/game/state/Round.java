package game.state;

import java.util.ArrayList;
import java.util.Iterator;

import game.Game;
import game.entities.Entity;
import game.entities.enemies.BasicEnemy;
import game.entities.enemies.Enemy;
import game.level.Map;
import game.level.PathFinder;

public class Round {

	private int id;
	private Map map;
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Enemy> initialEnemies = new ArrayList<Enemy>();
	private Enemy ent;
	
	private int winbonus;
	
	public Round(int roundnumber, Map map, Enemy ent){
		this.id = roundnumber;
		this.map = map;
		this.ent = ent;
		populate();
	}
	
	/**
	 * Spawn a Basic enemy on every spawn point
	 */
	private void populate(){
		int multiplier = Game.config.getInt("rounds.multiplier");
		//Get spawnlocation from map and spawn enemies
		Iterator<int[]> ite = map.getSpawnPointIterator();
		int[] spawnPoint;
		// go over all spawn points
		while (ite.hasNext()) {
			spawnPoint = ite.next();
			// get path from that spawnpoint
			PathFinder paths = map.getPathFinder(spawnPoint);
			for(int i = 0; i < id*multiplier; i++){
				// create enemy with a specific path (by cycling throught them)
				Enemy newEnt = ent.clone(paths.nextPath());
				enemies.add(newEnt);
				initialEnemies.add(newEnt);
			}
		}
	}
	
	public void reset() {
		enemies = new ArrayList<Enemy>();
		for (Enemy ent: initialEnemies) {
			enemies.add(ent.clone());
		}
	}
	
	public Enemy getNextEnemy() {
		if (enemies.size() == 0) {
			return null;
		}
		return enemies.remove(0); 
	}
	
	//Track and see if all enemies are killed
}
