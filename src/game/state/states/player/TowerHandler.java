package game.state.states.player;

import java.awt.Graphics2D;

import game.entities.tiles.Tile;
import game.entities.towers.Tower;
import game.entities.towers.TowerLibary;
import game.level.Map;

public class TowerHandler {
	
	private Tower[] previewTowers = new Tower[] {
			TowerLibary.BASIC.getValue(),
			TowerLibary.HOME.getValue()
	};
	private Tower[] map;
	private int width;
	private Tower heldTower;
	
	public TowerHandler(Map map) {
		this.map = new Tower[map.getWidth() * map.getHeight()];
		width = map.getWidth();
	}
	
	public void placeTower(int x, int y) {
		map[x + y * width] = heldTower.clone(x * Tile.TILESIZE, y * Tile.TILESIZE);
	}
	
	public void grabTower(int x, int y) {
		for (Tower t: previewTowers) {
			if (x > t.getX() && x < t.getX() + Tile.TILESIZE && y > t.getY() && y < t.getY() + Tile.TILESIZE) {
				heldTower = t;
				return;
			}
		}
	}
	
	public void releaseTower() {
		heldTower = null;
	}
	
	public boolean isOccupied(int x, int y) {
		return getTower(x, y) != null;
	}
	
	public Tower getHeldTower() {
		return heldTower;
	}
	
	public Tower getTower(int x, int y) {
		return map[x + y * width];
	}
	
	public void render(Graphics2D g2) {
		for (Tower t: previewTowers) {
			t.render(g2);
		}
	}
	
	public void markTower(int x, int y) {
		for (Tower t: previewTowers) {
			System.out.println(x > t.getX() );
			if (x > t.getX() && x < t.getX() + Tile.TILESIZE && y > t.getY() && y < t.getY() + Tile.TILESIZE) {
				t.setMark(true);
			}else {
				t.setMark(false);
			}
		}
	}
}
