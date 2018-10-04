package game.entities.tiles;

import java.awt.Graphics2D;

import game.level.Map;
import gfx.HUD;

public class BuyableTile extends Obstical{
	
	private int price;
	private boolean sold = false;
	
	public BuyableTile(char id, int imgIndex, int setIndex, int price) {
		super(id, imgIndex, setIndex);
		this.price = price;
	}
	
	public BuyableTile(double x, double y, char id, int imgIndex, int setIndex, int price) {
		super(x, y, id, imgIndex, setIndex);
		this.price = price;
	}
	
	@Override
	public Tile createCopy(double x, double y) {
		return new BuyableTile(x, y, id, imgIndex, setIndex, price);
	}
	
	@Override
	public boolean isBuyable() {
		return !sold;
	}
	
	@Override
	public void buy(Tile backgroundTile, Map map) {
		obstical = false;
		sold = true;
		imgIndex = backgroundTile.getImgIndex();
		setIndex = backgroundTile.getSetIndex();
	}
	
	@Override
	public void render(Graphics2D g2) {
		super.render(g2);
		if (marked && !sold) {
			HUD.drawText(g2, Integer.toString(price), (int)(getX() * TILESIZE), (int)(getY() * TILESIZE));
		}
	}
	
	@Override
	public int getPrice() {
		return price;
	}
	
}
