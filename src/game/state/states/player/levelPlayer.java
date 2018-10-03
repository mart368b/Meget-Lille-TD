package game.state.states.player;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import game.Game;
import game.entities.enemies.BasicEnemy;
import game.entities.enemies.Enemy;
import game.level.Map;
import game.state.GameState;
import game.state.StateManager;
import game.state.StateTypes;
import game.state.states.EnemyHandler;
import game.state.states.player.interactibles.TileHighlighter;
import gfx.tiles.TileSet;
import gfx.tiles.TileSetManager;

public class levelPlayer extends GameState {
	
	private Map map;
	private TileSet tileset;
	private Round[] rounds;
	private static final int sleepTime = 10;
	private int currentRound, time = 0;
	private EnemyHandler handler = new EnemyHandler();
	private TileHighlighter higlighter = new TileHighlighter();

	public levelPlayer(StateManager sm) {
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
		higlighter.tick();
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
		
		higlighter.render(g2);
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (e.getX() > 30 * Map.TILESIZE) {
			higlighter.setVisible(false);
		}else {
			higlighter.setVisible(true);
			higlighter.moveTo(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		higlighter.setVisible(false);
		
	}
}
