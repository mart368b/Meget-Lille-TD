package gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import gfx.sprites.SpriteManager;

public class EndScreen {
	
	private BufferedImage victoryImage, loseImage;
	private boolean victoryOrLose = true;
	private int x, y;
	
	public EndScreen(int x, int y) {
		try {
			victoryImage = ImageIO.read(getClass().getResourceAsStream("/sprites/huds/VictoryImage.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.x = x;
		this.y = y;
	}
	
	public void setWinOrLose(boolean b) {
		victoryOrLose = b;
	}
	
	public void render(Graphics2D g2) {
		if (victoryOrLose) {
			g2.drawImage(victoryImage, x, y, null);			
		}
	}


}
