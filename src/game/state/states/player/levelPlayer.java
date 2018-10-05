package game.state.states.player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import game.Game;

import javax.swing.text.Highlighter.Highlight;

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
	private static final int roundPauseTime = 10*60;
	private int currentRound, time = 0;
	private EnemyHandler enemyHandler = new EnemyHandler();
	private TowerHandler towerHandler;
	private TileHighlighter highlighter;
	private Tile backgroundTile;
	private int gold, lifes;
	
	private ArrayList<Tower> towers = new ArrayList<Tower>();
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
		towerHandler = new TowerHandler(map, enemyHandler);
		highlighter = new TileHighlighter(map, towerHandler);
		tileset = TileSetManager.getTileset(0);
		this.currentRound = 0;
		
		//temperary to test towers
		/*int[] cost = new int[3];
		int[] speed = new int[3];
		int[] damage = new int[3];
		String[] lore = new String[3];
		for(int i = 0; i < 3; i++){
			damage[i] = Game.config.getInt("basic_tower" + i + ".damage");
			cost[i] = Game.config.getInt("basic_tower" + i + ".cost");
			speed[i] = Game.config.getInt("basic_tower" + i + ".speed");
			lore[i] = Game.config.getString("basic_tower" + i + ".lore");
		}
		towers.add(new BasicTower(cost, speed, damage, lore, 5*32, 6*32));
		towers.add(new BasicTower(cost, speed, damage, lore, 7*32, 6*32));
		towers.add(new BasicTower(cost, speed, damage, lore, 9*32, 6*32));
		
		towers.get(1).lvlUp();
		towers.get(2).lvlUp();
		towers.get(2).lvlUp();
		
		cost = new int[3];
		speed = new int[3];
		damage = new int[3];
		lore = new String[3];
		for(int i = 0; i < 3; i++){
			damage[i] = Game.config.getInt("home_tower" + i + ".damage");
			cost[i] = Game.config.getInt("home_tower" + i + ".cost");
			speed[i] = Game.config.getInt("home_tower" + i + ".speed");
			lore[i] = Game.config.getString("home_tower" + i + ".lore");
		}
		towers.add(new HomeTower(cost, speed, damage, lore, 5*32, 8*32));
		towers.add(new HomeTower(cost, speed, damage, lore, 7*32, 8*32));
		towers.add(new HomeTower(cost, speed, damage, lore, 9*32, 8*32));
			
		towers.get(4).lvlUp();
		towers.get(5).lvlUp();
		towers.get(5).lvlUp();*/
	}

	@Override
	public void tick() {
		
		enemyHandler.tick();
		towerHandler.tick();
		highlighter.tick();
		
		int lostLifes = enemyHandler.getLifesLost();
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
			if (enemyHandler.isEmpty()) {
				time++;
			}
			if (time > roundPauseTime) {
				time = sleepTime + 1;
				this.currentRound++;
				if (this.currentRound >= rounds.length) {
					setVictory();
				}else{
					currentRound = rounds[this.currentRound];					
				}
			}
		}else {
			time++;
		}
		
		if (!currentRound.reachedEnd() && time > sleepTime) {
			time = 0;
			enemyHandler.addEnemyWave(currentRound.getWave(map));
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
		for(Tower tower : towers){
			tower.render(g2);
		}
		
		//render enemies from Round Object
		enemyHandler.render(g2);		
		highlighter.render(g2);
		
		HUD.render(g2);
		
		towerHandler.render(g2);
	}

	@Override
	public void mousePressed(MouseEvent e) {
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
			if (t.getPrice() > gold) {
				HUD.setTextColor(Color.RED);
			}else {
				HUD.setTextColor(Color.WHITE);
			}
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
