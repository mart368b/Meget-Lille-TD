package game.state.states.player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import game.Game;
import game.entities.enemies.Enemy;
import game.entities.tiles.BuyableTile;
import game.entities.tiles.Tile;
import game.entities.towers.Tower;
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
	private static final int roundPauseTime = 3*60, startUpPauseTime = 3*60;
	private int currentRoundIndex, time = 0;
	private EnemyHandler enemyHandler = new EnemyHandler();
	private TowerHandler towerHandler;
	private TileHighlighter highlighter;
	private Tile backgroundTile;
	private int gold, lifes;
	private boolean startup = true;
	
	private ArrayList<Tower> towers = new ArrayList<Tower>();
	private boolean finished = false, victory = false, lose = false, collectedGold = true;
	private Round currentRound;

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
		towerHandler = new TowerHandler(map, enemyHandler);
		highlighter = new TileHighlighter(map, towerHandler);
		tileset = TileSetManager.getTileset(0);
		this.currentRoundIndex = 0;
	}

	@Override
	public void tick() {
		
		enemyHandler.tick();
		towerHandler.tick();
		highlighter.tick();
		
		int lostLifes = enemyHandler.getLifesLost();
		lifes -= lostLifes;
		if (lifes < 0) {
			lifes = 0;
			setLose();
		}
		
		int goldGained = enemyHandler.getGoldgained();
		
		gold += goldGained;
		
		HUD.displayResources(gold, lifes);
		
		if (!finished) {
			manageRounds();
		}
			
	}
	
	private void manageRounds() {
		if (victory) {
			return;
		}
		if (startup) {
			if (time > startUpPauseTime) {
				time = 0;
				startup = false;
			}else {
				time++;
				return;
			}
		}
		Round currentRound = rounds[this.currentRoundIndex];
		if (currentRound.reachedEnd()) {
			if (enemyHandler.isEmpty()) {
				if (!collectedGold) {
					gold += towerHandler.getEarnedGold();
					HUD.displayResources(gold, lifes);
					collectedGold = true;
				}
				time++;
			}
			if (time > roundPauseTime) {
				collectedGold = false;
				time = sleepTime + 1;
				this.currentRoundIndex++;
				if (this.currentRoundIndex < rounds.length) {
					currentRound = rounds[this.currentRoundIndex];					
				}
			}
		}else {
			time++;
		}
		
		if (!currentRound.reachedEnd() && time > sleepTime) {
			time = 0;
			Enemy[] newEnemies = currentRound.getWave(map);
			for (Enemy e: newEnemies) {
				e.setRoundScaling(currentRoundIndex);
			}
			enemyHandler.addEnemyWave(newEnemies);
		}
		if (this.currentRoundIndex >= rounds.length - 1 && enemyHandler.isEmpty() ) {
			setVictory();
		}
	}
	
	private void setVictory() {
		finished = true;
		victory = true;
		HUD.setEndScreen(true);
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
		map.render(g2);
		
		for(Tower tower : towers){
			tower.render(g2);
		}
		
		//render enemies from Round Object
		enemyHandler.render(g2);		
		highlighter.render(g2);		
		
		HUD.drawSideMenu(g2);
		
		towerHandler.render(g2);
		
		HUD.drawEndScreen(g2);
		g2.setColor(Color.WHITE);
		HUD.drawCenteredText(g2, currentRoundIndex + 1 + "", 555, 785, 32);
		HUD.drawCenteredText(g2, rounds.length + "", 665, 785, 32);;
		
		if (!victory) {
			g2.setColor(Color.BLACK);
			Round currentRound = rounds[this.currentRoundIndex];
			String cornerMessage = ""; 
			if (startup) {
				cornerMessage = "Starting in " + ((startUpPauseTime - time)/Game.tps + 1) + " s";
				g2.drawString(cornerMessage, 0, 30);			
			}
			if (currentRound.reachedEnd() && enemyHandler.isEmpty()) {
				cornerMessage = "Next round " + ((roundPauseTime - time)/Game.tps + 1) + " s";
				g2.drawString(cornerMessage, 0, 30);			
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (finished) {
			HUD.endButton.mousePressed(e);
		}
		switch (highlighter.getMode()) {
		case TileHighlighter.SELECT:
			if (e.getX() > 960) {
				towerHandler.grabTower(e.getX(), e.getY(), highlighter);
			}else {
				Tower tower = towerHandler.getTower(e.getX() / Tile.TILESIZE, e.getY() / Tile.TILESIZE);
				if (tower != null && tower.getCost() != -1 && gold >= tower.getCost()) {
					gold -= tower.getCost();
					HUD.displayResources(gold, lifes);
					tower.lvlUp();
				}else {
					buyTile();					
				}
			}
			break;
		case TileHighlighter.PLACING:
			if (e.getButton() != e.BUTTON1) {
				cancelPlacing();
				mouseMoved(e);
				break;
			}
			if (!highlighter.isOccupied() && gold >= towerHandler.getHeldTower().getCost()) {
				gold -= towerHandler.getHeldTower().getCost();
				HUD.displayResources(gold, lifes);
				buyTower();
			}
			break;
		}
		towerHandler.isToExpensive(gold);
	}
	
	private void buyTower() {
		towerHandler.placeTower(highlighter.getScreenX(), highlighter.getScreenY());
		
	}

	private void buyTile() {
		if (highlighter.isVisible()) {
			Tile markedTile = highlighter.getMarkedTile();
			if (markedTile.isBuyable() && markedTile.getPrice() <= gold) {
				gold -= markedTile.getPrice();
				HUD.displayResources(gold, lifes);
				markedTile.buy(backgroundTile, map);
				highlighter.setVisible(false);
			}
		}
	}

	public void cancelPlacing() {
		highlighter.changeMode(TileHighlighter.SELECT);
		towerHandler.releaseTower();
		// drop draged object
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (highlighter.getMode() == TileHighlighter.SELECT) {
			towerHandler.markTower(e.getX(), e.getY());
		}
		if (e.getX() > 30 * Tile.TILESIZE) {
			highlighter.setVisible(false);
		}else {
			highlighter.getMarkedTile().setMarked(false);
			int x = e.getX() - highlighter.getScreenWidth()/2 + 16;
			int y = e.getY()  - highlighter.getScreenHeight()/2 + 16;
			highlighter.moveTo( x, y );
			switch (highlighter.getMode()) {
			case TileHighlighter.SELECT:
				moveWhileSelecting(e);
				break;
			case TileHighlighter.PLACING:
				moveHighlightWhilePlacing(e);
				towerHandler.moveHeldTower(highlighter.getScreenX(), highlighter.getScreenY());
				break;
			}
		}
	}
	
	private void moveWhileSelecting(MouseEvent e) {
		Tile t = highlighter.getMarkedTile();
		Tower tower;
		t.setMarked(true);
		if (t.isBuyable()) {
			t.setMarked(false);
			highlighter.setVisible(true);
			Point2D tileOrigin = t.getOrigin();
			highlighter.moveToTile((int) tileOrigin.getX(), (int) tileOrigin.getY());
			highlighter.getMarkedTile().setMarked(true);
			highlighter.setDimension(t.getWidth(), t.getHeight());
			((BuyableTile) t).toExpensive (t.getPrice() > gold);
		}else if((tower = towerHandler.getTower(e.getX() / Tile.TILESIZE, e.getY() / Tile.TILESIZE)) != null){
			highlighter.moveTo((int)(tower.getX()), (int)(tower.getY()));
			highlighter.setDimension(tower.getWidth(), tower.getHeight());
			highlighter.setVisible(true);
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
