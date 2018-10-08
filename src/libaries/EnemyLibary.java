package libaries;

import game.entities.enemies.BasicEnemy;
import game.entities.enemies.Enemy;
import game.entities.tiles.Tile;

public enum EnemyLibary {

	SHEEP(new BasicEnemy());
	
	
	private final Enemy enemy;
	
	EnemyLibary(final Enemy enemy) {
		this.enemy = enemy;
    }
	
	public Enemy getValue() {
		return enemy; 
	} 
}
