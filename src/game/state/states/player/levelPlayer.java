package game.state.states.player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.text.Highlighter.Highlight;

import game.entities.tiles.Tile;
import game.level.Map;
import game.math.Point2D;
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
	private static final int roundPauseTime = 10*60;
	private int currentRound, time = 0;
	private EnemyHandler handler = new EnemyHandler();
	private TileHighlighter highlighter;
	private Tile backgroundTile;
	private int gold, lifes;
	private boolean finished = false, victory = false, lose = true;

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
		
		if (!finished) {
			manageRounds();
		}
			
	}
	
	private void manageRounds() {
		Round currentRound = rounds[this.currentRound];
		if (currentRound.reachedEnd()) {
			if (handler.isEmpty()) {
				time++;
			}
			if (time > roundPauseTime) {
				time = sleepTime + 1;
				this.currentRound++;
				if (this.currentRound >= rounds.length) {
					setVictory();
				}else {
					currentRound = rounds[this.currentRound];					
				}
			}
		}else {
			time++;
		}
		
		if (!currentRound.reachedEnd() && time > sleepTime) {
			time = 0;
			handler.addEnemyWave(currentRound.getWave(map));
		}
	}
	
	private void setVictory() {
		finished = true;
		//TODO add victory screen
		victory = true;
		//close();
	}
	
	private void setLose() {
		finished = true;
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
		switch (highlighter.getMode()) {
		case TileHighlighter.SELECT:
			buyTile();
			break;
		case TileHighlighter.PLACING:
			if (e.getButton() != e.BUTTON1) {
				highlighter.changeMode(TileHighlighter.SELECT);
				mouseMoved(e);
			}
			buyTower();
			break;
		}
	}
	
	private void buyTower() {
		// TODO Auto-generated method stub
		
	}

	private void buyTile() {
		if (highlighter.isVisible()) {
			Tile markedTile = highlighter.getMarkedTile();
			if (markedTile.getPrice() <= gold) {
				gold -= markedTile.getPrice();
				HUD.displayResources(gold, lifes);
				markedTile.buy(backgroundTile, map);
				highlighter.setVisible(false);
			}
		}
	}

	public void cancelPlacing() {
		highlighter.changeMode(TileHighlighter.SELECT);
		// drop draged object
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (e.getX() > 30 * Tile.TILESIZE) {
			highlighter.setVisible(false);
		}else {
			highlighter.getMarkedTile().setMarked(false);
			highlighter.moveTo(e.getX() - highlighter.getScreenWidth()/2 + 16, e.getY()  - highlighter.getScreenHeight()/2 + 16);
			switch (highlighter.getMode()) {
			case TileHighlighter.SELECT:
				moveWhileSelecting(e);
				break;
			case TileHighlighter.PLACING:
				moveHighlightWhilePlacing(e);
				break;
			}
		}
	}
	
	private void moveWhileSelecting(MouseEvent e) {
		Tile t = highlighter.getMarkedTile();
		t.setMarked(true);
		if (t.isBuyable()) {
			t.setMarked(false);
			highlighter.setVisible(true);
			Point2D tileOrigin = t.getOrigin();
			highlighter.moveToTile((int) tileOrigin.getX(), (int) tileOrigin.getY());
			highlighter.getMarkedTile().setMarked(true);
			highlighter.setDimension(t.getWidth(), t.getHeight());
			if (t.getPrice() > gold) {
				HUD.setTextColor(Color.RED);
			}else {
				HUD.setTextColor(Color.WHITE);
			}
		}else {
			highlighter.setVisible(false);
		}
	}

	public void moveHighlightWhilePlacing(MouseEvent e) {
		highlighter.setVisible(true);
		//colouring is done inside TileHiglight
	}

	@Override
	public void mouseExited(MouseEvent e) {
		highlighter.setVisible(false);
		
	}
}
