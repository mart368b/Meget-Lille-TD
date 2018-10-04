package gfx.tiles;

import java.awt.image.BufferedImage;

public class ImageTile {

	private int id;
	private BufferedImage image;
	
	public ImageTile(int id, BufferedImage image){
		this.id = id;
		this.image = image;
	}
	
	public int getId(){
		return id;
	}

	public BufferedImage getImage() {
		return image;
	}
}
