package game.state.states;

import java.awt.Graphics2D;

import game.Game;
import game.entities.enemies.BasicEnemy;
import game.level.Map;
import game.state.GameState;
import game.state.Round;
import game.state.StateManager;
import gfx.tiles.TileSet;
import gfx.tiles.TileSetManager;

public class level1_0 extends GameState {
	
	private Map map;
	private TileSet tileset;
	private Round[] rounds;
	private int currentRound;

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
			this.rounds[i] = new Round((i + 1), map, new BasicEnemy());
		}
		this.currentRound = 0;
	}

	@Override
	public void tick() {
		//whenever everything is over
		//sm.setState(StateManager.MENU);
	}

	@Override
	public void render(Graphics2D g) {
		//render map
		for(int x = 0; x < map.getWidth(); x++){
			int locX = x * tileset.getTileSize();
			for(int y = 0; y < map.getHeight(); y++){
				int locY = y * tileset.getTileSize();
				
				g.drawImage(map.getTexture(map.getTile(x, y), 0).getImage(),
						locX, locY, null);
			}
		}
		//render enemies from Round Object
	}

	@Override
	public void keyPressed(int k) {
		
	}

	@Override
	public void keyReleased(int k) {
		
	}
}
