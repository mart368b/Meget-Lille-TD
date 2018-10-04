package game.entities.tiles;

import java.awt.Graphics2D;

import game.entities.Entity;
import gfx.tiles.ImageTile;
import gfx.tiles.ImageTileSet.TileType;
import gfx.tiles.TileSetManager;

public class Tile extends Entity{
	
	private char id;
	private int imgIndex, setIndex;
	public static int TILESIZE = 32;
	private boolean obstical = false;

	public Tile(char id, int imgIndex, int setIndex) {
		super(0, 0);
		this.id = id;
		this.imgIndex = imgIndex;
		this.setIndex = setIndex;
	}
	
	public Tile(char id, int imgIndex, int setIndex, boolean obstical) {
		super(0, 0);
		this.id = id;
		this.imgIndex = imgIndex;
		this.setIndex = setIndex;
		this.obstical = obstical;
	}
	
	public Tile(double x, double y, char id, int imgIndex, int setIndex, boolean obstical) {
		super(x, y);
		this.id = id;
		this.imgIndex = imgIndex;
		this.setIndex = setIndex;
		this.obstical = obstical;
	}
	
	public Tile createCopy(double x, double y) {
		return new Tile(x, y, id, imgIndex, setIndex, obstical);
	}
	
	public char getId() {
		return id;
	}
	
	public boolean equals(Tile t) {
		return id == t.getId();
	}
	public boolean isObstical() {
		ImageTile tile = TileSetManager.getTileset(setIndex).getTile(imgIndex);
		if(tile.getType() == TileType.PATH || tile.getType() == TileType.UNBUILDABLE){
			return true;
		}
		return false;
	}
	
	public void render(Graphics2D g2) {
		g2.drawImage(
				TileSetManager.getTileset(setIndex).getTile(imgIndex).getImage(), 
				(int)(getX() * TILESIZE), 
				(int)(getY() * TILESIZE), 
				null);
	}
	

}
