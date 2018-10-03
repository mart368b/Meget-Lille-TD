package game.state.states;

import java.awt.Graphics2D;

import game.Game;
import game.entities.enemies.BasicEnemy;
import game.entities.enemies.Enemy;
import game.level.Map;
import game.state.GameState;
import game.state.Round;
import game.state.StateManager;
import game.state.StateTypes;
import gfx.tiles.TileSet;
import gfx.tiles.TileSetManager;

public class level1_0 extends GameState {
	
	private Map map;
	private TileSet tileset;
	private Round[] rounds;
	private int currentRound, sleepTime = 5, time = 0;
	private EnemyHandler handler = new EnemyHandler();

	public level1_0(StateManager sm) {
		this.sm = sm;
		init();
	}
	
	@Override
	public void init() {
		map = new Map("level1_0");
		tileset = TileSetManager.getTileset(0);
		
		int rounds = Game.config.getInt("level1_0.rounds");
		this.rounds = new Round[rounds];
		for(int i = 0; i < rounds; i++){
			this.rounds[i] = new Round(i + 1, map, new Enemy[] {new BasicEnemy()},10);
							}
		this.currentRound = 0;
	}

	@Override
	public void tick() {
		if (time++ > sleepTime) {
			time = 0;
			Round currentRound = rounds[this.currentRound]; 
			if (!currentRound.reachedEnd()) {
				handler.addEnemyWave(currentRound.getWave());
			}
		}
		handler.tick();
		//whenever everything is over
		//close
		
	}
	
	public void close() {
		sm.setState(StateTypes.MENU);
	}

	@Override
	public void render(Graphics2D g2) {
		//render map
		for(int x = 0; x < map.getWidth(); x++){
			int locX = x * tileset.getTileSize();
			for(int y = 0; y < map.getHeight(); y++){
				int locY = y * tileset.getTileSize();
				
				g2.drawImage(map.getTexture(map.getTile(x, y), 0).getImage(),
						locX, locY, null);
			}
		}
		//render enemies from Round Object
		handler.render(g2);
	}

	@Override
	public void keyPressed(int k) {
		
	}

	@Override
	public void keyReleased(int k) {
		
	}
}
