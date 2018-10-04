package gfx.sprites;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Sprite {
	
	public static final int DEFAULTSPRITESIZE = 32;
	
	private HashMap<Integer, BufferedImage[]> frames = new HashMap<Integer, BufferedImage[]>();
	
	/**
	 * 
	 * The spriteclass holds a sprite's images or frames for its moves.
	 * 
	 * @param filePath The path for the spritesheet file.
	 * @param spriteSize The size of each frame/subimage.
	 */
	
	public Sprite(String filePath, int spriteSizeW, int spriteSizeH){
		try{
			BufferedImage sheet = ImageIO.read(getClass().getResourceAsStream(filePath));
			int width = sheet.getWidth()/spriteSizeW;
			int height = sheet.getHeight()/spriteSizeH;
			for(int index = 0; index < height; index++){
				BufferedImage[] images = new BufferedImage[width];
				for(int i = 0; i < width; i++){
					images[i] = sheet.getSubimage(i * spriteSizeW, index * spriteSizeH, spriteSizeW, spriteSizeH);
				}
				frames.put(index, images);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public BufferedImage[] getFrames(int i){
		return frames.get(i);
	}
}
