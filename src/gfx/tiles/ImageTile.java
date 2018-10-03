package gfx.tiles;

import java.awt.image.BufferedImage;

import gfx.tiles.ImageTileSet.TileType;

public class ImageTile {

	private int id;
	private TileType type;
	private BufferedImage image;
	
	public ImageTile(int id, BufferedImage image, TileType type){
		this.id = id;
		this.image = image;
		this.type = type;
	}
	
	public int getId(){
		return id;
	}

	public TileType getType() {
		return type;
	}

	public BufferedImage getImage() {
		return image;
	}
}
