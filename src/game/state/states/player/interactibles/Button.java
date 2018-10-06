package game.state.states.player.interactibles;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import game.Game;
import gfx.HUD;

public class Button {
	
	private boolean pressed = false;
	private int x, y;
	private int width, height, padding = 10;
	private String text;
	private Color bgColor = new Color(80, 220, 100);
	
	private long pressEnd;
	
	public Button(int x, int y, String text) {
		this.text = text;
		this.x = x;
		this.y = y;
	}
	
	public void mousePressed(MouseEvent e) {
		int xOffset = width/2;
		int yOffset = height/2;
		if (e.getX() > x - xOffset && e.getX() < x + xOffset && e.getY() > y - height && e.getY() < y + yOffset) {
			pressed = true;
			pressEnd = System.currentTimeMillis() + 200;
		}
	}
	
	public boolean hasBeenPressed() {
		boolean state = pressed;
		pressed = false;
		return state;
	}
	
	public void render(Graphics2D g2) {
		g2.setFont(HUD.textFont);
		FontMetrics metric = g2.getFontMetrics();
		if (width == 0) {
			width = metric.stringWidth(text) + padding * 2;
			height = 24 + padding * 2;
		}
		
		int xOffset = width/2;
		int yOffset = height/2;
		
		g2.setColor(bgColor);
		g2.fillRect(x - xOffset, y - height, width, height);
		g2.setColor(Color.DARK_GRAY);
		g2.drawRect(x - xOffset - 1, y - height - 1, width + 1, height + 1);
		if (System.currentTimeMillis() < pressEnd) {
			g2.setColor(Color.BLACK);
			g2.drawRect(x - xOffset, y - height, width - 1, height - 1);
		}
		
		g2.setColor(Color.WHITE);
		g2.drawString(text, x - xOffset + padding, y - yOffset + padding);
	}

}
