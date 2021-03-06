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
	private Round[] rounds;
	private final int sleepTime = 20;
	private final int roundPauseTime = 2*60, startUpPauseTime = 4*60;
	private int currentRoundIndex, roundOffset, time = 0;
	private EnemyHandler enemyHandler = new EnemyHandler();
	private TowerHandler towerHandler;
	private TileHighlighter highlighter;
	private Tile backgroundTile;
	private int gold, lifes, loopCount, roundCount;
	private boolean startup = true;
	
	private ArrayList<Tower> towers = new ArrayList<Tower>();
	private boolean finished = false, victory = false, lose = false, collectedGold = true;

	public levelPlayer(StateManager sm, Round[] rounds, Tile backgroundTile, int lifes, int gold, int loopCount) {
		this.backgroundTile = backgroundTile;
		this.gold = gold;
		this.loopCount = loopCount;
		this.lifes = lifes;
		HUD.displayResources(gold, lifes);
		this.sm = sm;
		init();
		this.rounds = rounds;
		roundCount = rounds.length * loopCount;
	}
	
	@Override
	public void init() {
		map = new Map("level1_0");
		towerHandler = new TowerHandler(map, enemyHandler);
		highlighter = new TileHighlighter(map, towerHandler);
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
		towerHandler.isToExpensive(gold);
		HUD.displayResources(gold, lifes);
		
		if (!finished) {
			manageRounds();
		}else {
			if (HUD.endButton.hasBeenPressed()) {
				close();
			}
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
		Round currentRound = rounds[getCurrentRoundIndex()];
		if (currentRound.reachedEnd()) {
			if (enemyHandler.isEmpty()) {
				if (!collectedGold) {
					gold += towerHandler.getEarnedGold();
					towerHandler.isToExpensive(gold);
					HUD.displayResources(gold, lifes);
					collectedGold = true;
				}
				time++;
			}
			if (time > roundPauseTime) {
				collectedGold = false;
				time = sleepTime + 1;
				this.currentRoundIndex++;
				if (getCurrentRoundIndex() < rounds.length) {
					currentRound = rounds[getCurrentRoundIndex()];					
				}
			}
		}else {
			time++;
		}
		
		if (!currentRound.reachedEnd() && time > sleepTime) {
			time = 0;
			Enemy[] newEnemies = currentRound.getWave(map);
			for (Enemy e: newEnemies) {
				e.setRoundScaling(getCurrentRoundIndex());
			}
			enemyHandler.addEnemyWave(newEnemies);
		}
		if (getCurrentRoundIndex() >= rounds.length - 1 && enemyHandler.isEmpty() && currentRound.reachedEnd()) {
			if (loopCount-- > 1) {
				for (Round r: rounds) {
					r.reset();
				}
				++currentRoundIndex;
				roundOffset = currentRoundIndex;
				System.out.println(currentRoundIndex + " " + roundOffset);
				collectedGold = false;
				time = 0;
				startup = true;
			}else {
				setVictory();
			}
		}
	}
	
	private int getCurrentRoundIndex() {
		return currentRoundIndex - roundOffset;
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
		HUD.setEndScreenVisible(false);
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
		HUD.drawCenteredText(g2,  (currentRoundIndex + 1) + "", 555, 785, 32);
		HUD.drawCenteredText(g2, roundCount + "", 665, 785, 32);;
		
		if (!victory) {
			g2.setColor(Color.BLACK);
			Round currentRound = rounds[getCurrentRoundIndex()];
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
					towerHandler.isToExpensive(gold);
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
				towerHandler.isToExpensive(gold);
				HUD.displayResources(gold, lifes);
				buyTower();
			}
			break;
		}
	}
	
	private void buyTower() {
		towerHandler.placeTower(highlighter.getScreenX(), highlighter.getScreenY());
		
	}

	private void buyTile() {
		if (highlighter.isVisible()) {
			Tile markedTile = highlighter.getMarkedTile();
			if (markedTile.isBuyable() && markedTile.getPrice() <= gold) {
				gold -= markedTile.getPrice();
				towerHandler.isToExpensive(gold);
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
			towerHandler.isToExpensive(gold);
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
