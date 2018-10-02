package gfx.sprites;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Sprite {
	
	private BufferedImage[] frames;
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
			frames = new BufferedImage[width];
			for(int i = 0; i < width; i++){
				frames[i] = sheet.getSubimage(i * size, 0, size, size);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public BufferedImage[] getFrames(){
		return frames;
	}
}
