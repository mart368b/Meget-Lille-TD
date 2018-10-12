package gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import game.entities.tiles.Tile;
import game.state.states.player.interactibles.Button;
import gfx.sprites.ColorRamp;

public class HUD {

	public static BufferedImage image;
	public static String gold, lifes;
	public static ColorRamp healthColors = new ColorRamp(new Color[] {
		Color.RED,
		new Color(255,215,0),
		Color.GREEN
	});
	public static Font textFont = new Font("impact", Font.PLAIN, 24); 
	public static boolean showResources = false, showEndScreen = false;
	public static EndScreen endScreen = new EndScreen(160, 160);
	public static Button endButton = new Button(480, 560, "Next level");
		
	
	public static void displayResources(int newGold, int newLifes) {
		showResources = true;
		gold = Integer.toString(newGold);
		lifes = Integer.toString(newLifes);
	}
	
	public static void setEndScreen(boolean b) {
		endScreen.setWinOrLose(b);
		setEndScreenVisible(b);
	}
	
	public static void setEndScreenVisible(boolean b) {
		showEndScreen = b;
	}
	
	public void loadImage(String fileName){
		String path = "/sprites/huds/" + fileName + ".png";
		try {
			image = ImageIO.read(getClass().getResourceAsStream(path));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void drawCenteredText(Graphics2D g2, String text, int x, int y, int tileWidth) {
		g2.setFont(textFont);
		FontMetrics fontMetrics = g2.getFontMetrics();
		int width = tileWidth * Tile.TILESIZE;
		int textWidth = fontMetrics.stringWidth(text);
		int offset = width - textWidth;
		g2.drawString(text, x + offset/2, y);
	}
	
	public static void tick() {
		
	}

	public static void drawSideMenu(Graphics2D g2) {
		g2.drawImage(image, 960, 0, null);
		
		if(showResources) {
			g2.setColor(Color.WHITE);
			g2.setFont(textFont);
			FontMetrics fontMetrics = g2.getFontMetrics();
			g2.drawString(gold, 1133 - fontMetrics.stringWidth(gold), 182);
			g2.drawString(lifes, 1250 - fontMetrics.stringWidth(lifes), 182);
		}
	}

	public static void drawEndScreen(Graphics2D g2) {
		if (showEndScreen) {
			endScreen.render(g2);			
			endButton.render(g2);
		}
	}
}
