package gfx.tiles;

import java.awt.image.BufferedImage;

import gfx.tiles.TileSet.TileType;

public class Tile {

	private int id;
	private TileType type;
	private BufferedImage image;
	
	public Tile(int id, BufferedImage image, TileType type){
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
