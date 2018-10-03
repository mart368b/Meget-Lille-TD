package game.state.states.player;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import game.Game;
import game.entities.enemies.BasicEnemy;
import game.entities.enemies.Enemy;
import game.entities.tiles.Tile;
import game.level.Map;
import game.state.GameState;
import game.state.StateManager;
import game.state.StateTypes;
import game.state.states.EnemyHandler;
import game.state.states.player.interactibles.TileHighlighter;
import gfx.HUD;
import gfx.tiles.ImageTileSet;
import gfx.tiles.TileSetManager;

public class levelPlayer extends GameState {
	
	private Map map;
	private ImageTileSet tileset;
	private Round[] rounds;
	private static final int sleepTime = 10;
	private int currentRound, time = 0;
	private EnemyHandler handler = new EnemyHandler();
	private TileHighlighter highlighter;

	public levelPlayer(StateManager sm, Round[] rounds) {
		this.sm = sm;
		init();
		this.rounds = rounds;
	}
	
	@Override
	public void init() {
		map = new Map("level1_0");
		highlighter = new TileHighlighter(map);
		tileset = TileSetManager.getTileset(0);
		this.currentRound = 0;
	}

	@Override
	public void tick() {
		if (time++ > sleepTime) {
			time = 0;
			Round currentRound = rounds[this.currentRound]; 
			if (!currentRound.reachedEnd()) {
				handler.addEnemyWave(currentRound.getWave(map));
			}
		}
		handler.tick();
		highlighter.tick();
		//whenever everything is over
		//close
	}
	
	public void close() {
		sm.setState(StateTypes.MENU);
	}

	@Override
	public void render(Graphics2D g2) {
		//render map
		//g2.drawImage(map.getTexture(map.getTile(x, y), 0).getImage(),
		map.render(g2);
		
		g2.drawImage(HUD.image, 960, 0, null);
		//render enemies from Round Object
		handler.render(g2);
		
		highlighter.render(g2);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Tile[] markedTiles = highlighter.getMarkedTiles();
		String s = "";
		for (int x0 = 0; x0 < highlighter.getWidth(); x0 ++) {
			for (int y0 = 0; y0 < highlighter.getHeight(); y0 ++) {
				s += markedTiles[x0 + y0 * highlighter.getWidth()].toString() + " ";
			}
			s += "\n";
		}
		System.out.println(s);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (e.getX() > 30 * Tile.TILESIZE) {
			highlighter.setVisible(false);
		}else {
			highlighter.setVisible(true);
			highlighter.moveTo(e.getX() - highlighter.getScreenWidth()/2 + 16, e.getY()  - highlighter.getScreenHeight()/2 + 16);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		highlighter.setVisible(false);
		
	}
}
