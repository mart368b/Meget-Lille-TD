package game.state.states.player.interactibles;

import java.awt.Color;
import java.awt.Graphics2D;

import game.Game;
import game.level.Map;

public class TileHighlighter {
	
	private int x, y;
	private static final Color c1 = new Color(0,255,255);
	private static final Color c2 = new Color(0,206,209);
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
	
	public void render(Graphics2D g2) {
		if (visible) {
			g2.setColor(currentColor);
			g2.drawRect(x * Map.TILESIZE - 1, y * Map.TILESIZE - 1, Map.TILESIZE + 1, Map.TILESIZE + 1);
		}
	}
	
	private Color getColor(double t) {
		int r = (int) Math.floor(c1.getRed() * t + c2.getRed() * (1 - t));
		int g = (int) Math.floor(c1.getGreen() * t + c2.getGreen() * (1 - t));
		int b = (int) Math.floor(c1.getBlue() * t + c2.getBlue() * (1 - t));
		return new Color(r, g, b, (int) (64*t) + 192);
	}
}
