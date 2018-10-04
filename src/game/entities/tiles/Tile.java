package game.entities.tiles;

import java.awt.Graphics2D;

import game.entities.Entity;
import game.level.Map;
import gfx.tiles.TileSetManager;

public class Tile extends Entity{
	
	public static int TILESIZE = 32;
	
	protected char id;
	protected int width = 1, height = 1;
	protected int imgIndex, setIndex;
	protected boolean obstical = false, marked = false;

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
		//change constructor in game.entities.tiles.TileLibary instead
		return obstical;
	}
	
	public boolean isBuyable() {
		return false;
	}
	
	public int getSetIndex() {
		return setIndex;
	}
	
	public int getImgIndex() {
		return imgIndex;
	}
	
	public int getPrice() {
		return 0;
	}
	
	public void highlight() {}
	
	public void buy(Tile backgroundTile, Map map) {}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void render(Graphics2D g2) {
		g2.drawImage(
				TileSetManager.getTileset(setIndex).getTile(imgIndex).getImage(), 
				(int)(getX() * TILESIZE), 
				(int)(getY() * TILESIZE), 
				null);
	}

	public void setMarked(boolean mark) {
		marked = mark;
	}

}
