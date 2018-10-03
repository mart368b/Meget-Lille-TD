package gfx;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class HUD {

	public static BufferedImage image;
	
	public void loadImage(String fileName){
		String path = "/sprites/huds/" + fileName + ".png";
		try {
			image = ImageIO.read(getClass().getResourceAsStream(path));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
