package gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import game.entities.tiles.Tile;
import gfx.sprites.ColorRamp;
import gfx.tiles.TileSetManager;

public class HUD {

	public static BufferedImage image;
	public static String gold, lifes;
	public static Color priceTextColor = Color.WHITE;
	public static ColorRamp healthColors = new ColorRamp(new Color[] {
		Color.RED,
		new Color(255,215,0),
		Color.GREEN
	});
	public static Font textFont = new Font("impact", Font.PLAIN, 24); 
	public static boolean showResources = false;
		
	
	public static void displayResources(int newGold, int newLifes) {
		showResources = true;
		gold = Integer.toString(newGold);
		lifes = Integer.toString(newLifes);
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
		g2.setColor(priceTextColor);
		g2.setFont(textFont);
		FontMetrics fontMetrics = g2.getFontMetrics();
		int width = tileWidth * Tile.TILESIZE;
		int textWidth = fontMetrics.stringWidth(text);
		int offset = width - textWidth;
		g2.drawString(text, x + offset/2, y);
	}

	public static void render(Graphics2D g2) {
		g2.drawImage(image, 960, 0, null);
		
		if(showResources) {
			g2.setColor(Color.WHITE);
			g2.setFont(textFont);
			FontMetrics fontMetrics = g2.getFontMetrics();
			g2.drawString(gold, 1133 - fontMetrics.stringWidth(gold), 182);
			g2.drawString(lifes, 1250 - fontMetrics.stringWidth(lifes), 182);
		}
	}

	public static void setTextColor(Color c) {
		priceTextColor = c;
	}
}
