package game.state.states.player;

import java.awt.Graphics2D;
import java.util.ArrayList;

import game.entities.enemies.Enemy;
import game.entities.projectile.Projectile;
import game.entities.tiles.Tile;
import game.entities.towers.Tower;
import game.level.Map;
import game.state.states.EnemyHandler;
import game.state.states.player.interactibles.TileHighlighter;
import libaries.TowerLibary;

public class TowerHandler {
	
	private Tower[] previewTowers = new Tower[] {
			TowerLibary.BASIC.getValue(),
			TowerLibary.HOME.getValue(),
			TowerLibary.BUBBLE.getValue()
	};
	private Tower[] map;
	private int width;
	private ArrayList<Tower> towers = new ArrayList<Tower>();
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	private Tower heldTower, markedTower;
	private EnemyHandler enemyHandler;
	
	public TowerHandler(Map map, EnemyHandler enemyHandler) {
		this.map = new Tower[map.getWidth() * map.getHeight()];
		width = map.getWidth();
		this.enemyHandler = enemyHandler;
	}
	
	public void placeTower(int x, int y) {
		Tower t = heldTower.clone(x, y, this);
		int tx = x / Tile.TILESIZE;
		int ty = y / Tile.TILESIZE;
		map[tx + ty * width] = t;
		map[tx + 1 + ty * width] = t;
		map[tx + (ty + 1) * width] = t;
		map[tx + 1 + (ty + 1) * width] = t;
		towers.add(t);
	}
	
	public void grabTower(int x, int y, TileHighlighter highlighter) {
		for (Tower t: previewTowers) {
			if (x > t.getX() && x < t.getX() + Tile.TILESIZE * 2 && y > t.getY() && y < t.getY() + Tile.TILESIZE * 2) {
				if (heldTower != null) {
					heldTower.setHeld(false);
				}
				heldTower = t;
				heldTower.setHeld(true);
				highlighter.setDimension(t.getWidth(), t.getHeight());
				highlighter.changeMode(TileHighlighter.PLACING);
				if (markedTower != null) {
					markedTower.setMark(false);
				}
				return;
			}
		}
	}
	
	public void releaseTower() {
		heldTower.reset();
		heldTower.setMark(false);
		heldTower.setHeld(false);
		heldTower = null;
	}
	
	public boolean isOccupied(int x, int y) {
		return getTower(x, y) != null;
	}
	
	public Tower getHeldTower() {
		return heldTower;
	}
	
	public int getEarnedGold() {
		int goldEarned = 0;
		for (Tower t: towers) {
			goldEarned += t.getGold();
		}
		return goldEarned;
	}
	
	public void isToExpensive(int gold) {
		for (Tower t: previewTowers) {
			t.isToExpensive(gold);
		}
		for (Tower t: towers) {
			t.isToExpensive(gold);
		}
	}
	
	public Tower getTower(int x, int y) {
		return map[x + y * width];
	}
	
	public void render(Graphics2D g2) {
		for (Tower t: previewTowers) {
			t.render(g2);
		}
		for (Tower t: towers) {
			t.render(g2);
		}
		for (Projectile p: projectiles) {
			p.render(g2);
		}
	}
	
	public void moveHeldTower(int x, int y) {
		heldTower.moveTo(x, y);
	}
	
	public void markTower(int x, int y) {		
		if(x > 960) {
			for (Tower t: previewTowers) {
				if (x > t.getX() && x < t.getX() + Tile.TILESIZE * 2 && y > t.getY() && y < t.getY() + Tile.TILESIZE * 2) {
					t.setMark(true);
				}else {
					t.setMark(false);
				}
			}			
		}else {
			int tx = x / Tile.TILESIZE;
			int ty = y / Tile.TILESIZE;
			Tower t = getTower(tx, ty);
			if (markedTower != null) {
				markedTower.setMark(false);
			}
			markedTower = t;
			if (markedTower == null) {
				return;
			}
			t.setMark(true);
		}
	}
	
	public void addProjectile(Projectile p) {
		projectiles.add(p);
	}
	
	public void tick() {
		for(Tower t: towers) {
			t.tick(enemyHandler);
		}
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = projectiles.get(i);
			if (!p.isActive()) {
				continue;
			}
			if (p.isDead()) {
				projectiles.remove(i);
				i--;
			}else {
				p.tick(enemyHandler);
			}
		}
	}
}
