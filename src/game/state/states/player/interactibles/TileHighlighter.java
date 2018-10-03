package game.state.states.player.interactibles;

import java.awt.Color;
import java.awt.Graphics2D;

import game.Game;
import game.level.Map;

public class TileHighlighter {
	
	public enum HighLightTypes{
		READY,
		ERROR,
		DISABLED
	}
	
	private int x, y;
	private int width = 2, height = 2;
	
	private static final Color c1 = new Color(0,255,255);
	private static final Color c2 = new Color(0,206,209);
	
	private HighLightTypes state = HighLightTypes.READY;
	
	private Color currentColor;
	private boolean visible = false;
	private double t = 0, dt = 0.5/Game.tps;
	
	public TileHighlighter() {
		currentColor = c1;
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
		currentColor = getColor(t);
	}
	
	public void moveTo(int x, int y) {
		this.x = x/Map.TILESIZE;
		this.y = y/Map.TILESIZE;
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
	
	public int getScreenX() {
		return x * Map.TILESIZE;
	}
	
	public int getScreenY() {
		return y * Map.TILESIZE;
	}
	
	public int getScreenWidth() {
		return width * Map.TILESIZE;
	}
	
	public int getScreenHeight() {
		return height * Map.TILESIZE;
	}
	
	private Color getColor(double t) {
		int r = (int) Math.floor(c1.getRed() * t + c2.getRed() * (1 - t));
		int g = (int) Math.floor(c1.getGreen() * t + c2.getGreen() * (1 - t));
		int b = (int) Math.floor(c1.getBlue() * t + c2.getBlue() * (1 - t));
		return new Color(r, g, b, (int) (64*t) + 192);
	}
}
