package game.state;

import java.util.ArrayList;

import game.Game;
import game.entities.Entity;
import game.entities.enemies.BasicEnemy;
import game.level.Map;

public class Round {

	private int id;
	private Map map;
	private ArrayList<Entity> enemies = new ArrayList<Entity>();
	
	private int winbonus;
	
	public Round(int roundnumber, Map map){
		this.id = roundnumber;
		this.map = map;
		populate();
	}
	
	private void populate(){
		int multiplier = Game.config.getInt("rounds.multiplier");
		for(int i = 0; i < id*multiplier; i++){
			enemies.add(new BasicEnemy(0,0));
		}
	}
	
	public void start(){
		//Get spawnlocation from map and spawn enemies
		for(Entity ent : enemies){
			ent.spawn(map.getSpawnPoints().get(0));
		}
	}
	
	//Track and see if all enemies are killed
}
