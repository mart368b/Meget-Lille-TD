package gfx.sprites;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Sprite {
	
	public static final int DEFAULTSPRITESIZE = 32;
	
	private HashMap<Integer, BufferedImage[]> frames = new HashMap<Integer, BufferedImage[]>();
	private int size;
	
	/**
	 * 
	 * The spriteclass holds a sprite's images or frames for its moves.
	 * 
	 * @param filePath The path for the spritesheet file.
	 * @param spriteSize The size of each frame/subimage.
	 */
	
	public Sprite(String filePath, int spriteSize){
		this.size = spriteSize;
		try{
			BufferedImage sheet = ImageIO.read(getClass().getResourceAsStream(filePath));
			int width = sheet.getWidth()/size;
			int height = sheet.getHeight()/size;
			for(int index = 0; index < height; index++){
				BufferedImage[] images = new BufferedImage[width];
				for(int i = 0; i < width; i++){
					images[i] = sheet.getSubimage(i * size, index * size, size, size);
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
