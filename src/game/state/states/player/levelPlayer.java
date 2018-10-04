package game.state.states.player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
	private static final int sleepTime = 20;
	private static final int roundPauseTime = 120;
	private int currentRound, time = 0;
	private EnemyHandler handler = new EnemyHandler();
	private TileHighlighter highlighter;
	private Tile backgroundTile;
	private int gold, lifes;
	private boolean complete = false, victory = false, lose = true;

	public levelPlayer(StateManager sm, Round[] rounds, Tile backgroundTile, int lifes, int gold) {
		this.backgroundTile = backgroundTile;
		this.gold = gold;
		this.lifes = lifes;
		HUD.displayResources(gold, lifes);
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
		time++;
		Round currentRound = rounds[this.currentRound];
		if (currentRound.reachedEnd()) {
			if (time > roundPauseTime) {
				time = sleepTime + 1;
				this.currentRound++;
				if (this.currentRound >= rounds.length) {
					setVictory();
				}else {
					currentRound = rounds[this.currentRound];					
				}
			}
		}
		
		if (!currentRound.reachedEnd() && time > sleepTime) {
			time = 0;
			handler.addEnemyWave(currentRound.getWave(map));
		}
		
		handler.tick();
		highlighter.tick();
		
		int lostLifes = handler.getLifesLost();
		if (lostLifes > 0) {
			lifes -= lostLifes;
			if (lifes < 0) {
				lifes = 0;
				setLose();
			}
			HUD.displayResources(gold, lifes);
		}
	}
	
	private void setVictory() {
		complete = true;
		//TODO add victory screen
		victory = true;
		//close();
	}
	
	private void setLose() {
		complete = true;
		//TODO add lose screen
		lose = true;
		//close();
	}
	
	public void close() {
		sm.setState(StateTypes.MENU);
	}

	@Override
	public void render(Graphics2D g2) {
		//render map
		//g2.drawImage(map.getTexture(map.getTile(x, y), 0).getImage(),
		map.render(g2);
		
		//render enemies from Round Object
		handler.render(g2);		
		highlighter.render(g2);
		
		HUD.render(g2);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		ArrayList<Tile> markedTiles = highlighter.getMarkedTiles();
		String s = "";
		for (int x0 = 0; x0 < highlighter.getWidth(); x0 ++) {
			for (int y0 = 0; y0 < highlighter.getHeight(); y0 ++) {
				Tile t = markedTiles.get(x0 + y0 * highlighter.getWidth());
				s += t.isBuyable() + " ";
				if (t.isBuyable() && gold >= t.getPrice()) {
					gold -= t.getPrice();
					HUD.displayResources(gold, lifes);
					t.buy(backgroundTile, map);
				}
			}
			s += "\n";
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (e.getX() > 30 * Tile.TILESIZE) {
			highlighter.setVisible(false);
		}else {
			highlighter.setVisible(true);
			highlighter.getMarkedTile().setMarked(false);
			highlighter.moveTo(e.getX() - highlighter.getScreenWidth()/2 + 16, e.getY()  - highlighter.getScreenHeight()/2 + 16);
			Tile t = highlighter.getMarkedTile();
			t.setMarked(true);
			if (t.isBuyable()) {
				if (t.getPrice() > gold) {
					HUD.setTextColor(Color.RED);
				}else {
					HUD.setTextColor(Color.WHITE);
				}
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		highlighter.setVisible(false);
		
	}
}
