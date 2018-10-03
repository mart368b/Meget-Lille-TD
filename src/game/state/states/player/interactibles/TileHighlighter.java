package game.state.states.player.interactibles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import game.Game;
import game.entities.tiles.Tile;
import game.level.Map;

public class TileHighlighter {	
	
	private static final int SELECT = 0, PLACING = 1;
	
	private static final Color SELECT_COLOR_1 = new Color(0,255,255);
	private static final Color SELECT_COLOR_2 = new Color(0,206,209);
	
	private static final Color PLACING_COLOR_1 = new Color(80, 220, 100);
	private static final Color PLACING_COLOR_2 = new Color(80, 200, 120);
	
	private static final Color PLACING_OCCUPIED_COLOR_1 = new Color(237, 41, 57);
	private static final Color PLACING_OCCUPIED_COLOR_2 = new Color(255, 36, 0);
	
	private Color c1, c2;
	
	private int x, y;
	private int width = 2, height = 2;
	private int currentMode = PLACING;
	
	private Color currentColor;
	private boolean visible = false;
	private double t = 0, dt = 0.5/Game.tps;
	private Map map;
	
	public TileHighlighter(Map map) {
		c1 = SELECT_COLOR_1;
		c2 = SELECT_COLOR_2;
		currentColor = c1;
		this.map = map;
	}
	
	public void changeMode(int mode) {
		currentMode = mode;
		switch(mode) {
		case SELECT:
			c1 = SELECT_COLOR_1;
			c2 = SELECT_COLOR_2;
			break;
		case PLACING:
			c1 = PLACING_COLOR_1;
			c2 = PLACING_COLOR_2;
			break;
		}
	}
	
	public void setVisible(boolean b) {
		visible = b;
	}
	
	public void tick() {
		t += dt;
		if (t > 1) {
			t = 1 - (t - 1);
			dt = -dt;
		}
		if (t < 0) {
			t = -t;
			dt = -dt;
		}
		if (currentMode == PLACING) {
			if (isBlocked()) {
				c1 = PLACING_OCCUPIED_COLOR_1;
				c2 = PLACING_OCCUPIED_COLOR_2;
			}else {
				c1 = PLACING_COLOR_1;
				c2 = PLACING_COLOR_2;
			}
		}
		currentColor = getColor(t);
	}
	
	public void moveTo(int x, int y) {
		this.x = x/Tile.TILESIZE;
		if (this.x + width > 30) {
			this.x = 30 - this.width; 
		}
		this.y = y/Tile.TILESIZE;
		if (this.y + height > 30) {
			this.y = 30 - this.width; 
		}
	}
	
	public void setDimension(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void render(Graphics2D g2) {
		if (visible) {
			g2.setColor(currentColor);
			int screenWidth = getScreenWidth();
			int screenHeight = getScreenHeight();
			g2.drawRect(getScreenX() - 1, getScreenY() - 1, screenWidth + 1, screenHeight + 1);
		}
	}
	
	
	public Tile[] getMarkedTiles() {
		ArrayList<Tile> markedTiles = new ArrayList<Tile>();
		for (int x0 = 0; x0 < width; x0 ++) {
			for (int y0 = 0; y0 < width; y0 ++) {
				markedTiles.add(map.getTile(x + x0, y + y0));
			}
		}
		return markedTiles.toArray(new Tile[markedTiles.size()]);
	}
	
	public boolean isBlocked() {
		ArrayList<Tile> markedTiles = new ArrayList<Tile>();
		for (int x0 = 0; x0 < width; x0 ++) {
			for (int y0 = 0; y0 < width; y0 ++) {
				Tile tile = map.getTile(x + x0, y + y0);
				if (tile.isObstical()) {
					return true;
				}
			}
		}
		return false;
	}
	
	private Color getColor(double t) {
		int r = (int) Math.floor(c1.getRed() * t + c2.getRed() * (1 - t));
		int g = (int) Math.floor(c1.getGreen() * t + c2.getGreen() * (1 - t));
		int b = (int) Math.floor(c1.getBlue() * t + c2.getBlue() * (1 - t));
		return new Color(r, g, b, (int) (64*t) + 192);
	}
	
	public int getScreenX() {
		return x * Tile.TILESIZE;
	}
	
	public int getScreenY() {
		return y * Tile.TILESIZE;
	}
	
	public int getScreenWidth() {
		return width * Tile.TILESIZE;
	}
	
	public int getScreenHeight() {
		return height * Tile.TILESIZE;
	}
	
	public void setWidth(int widh) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
