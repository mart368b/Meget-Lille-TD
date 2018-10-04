package game.entities.tiles;

import game.level.Map;
import game.math.Point2D;

public class LargeBuyableTile extends BuyableTile{
	private int[] pos;

	public LargeBuyableTile(char id, int imgIndex, int setIndex, int price, int width, int height, int[] position) {
		super(id, imgIndex, setIndex, price);
		this.width = width;
		this.height = height;
		this.pos = position;
	}
	
	
	public LargeBuyableTile(double x, double y, char id, int imgIndex, int setIndex, int price, int width, int height, int[] position) {
		super(x, y, id, imgIndex, setIndex, price);
		this.width = width;
		this.height = height;
		this.pos = position;
	}
	
	@Override
	public Tile createCopy(double x, double y) {
		return new LargeBuyableTile(x, y, id, imgIndex, setIndex, getPrice(), width, height, pos);
	}
	
	@Override
	public void buy(Tile backgroundTile, Map map) {
		if (!isObstical()) {
			return;
		}
		super.buy(backgroundTile, map);
		int x0 = (int) (getX() - pos[0]);
		int y0 = (int) (getY() - pos[1]);
		for (int xOffset = 0; xOffset < width; xOffset++) {
			for (int yOffset = 0; yOffset < height; yOffset++) {
				if (xOffset != pos[0] || yOffset != pos[1]) {
					map.getTile(x0 + xOffset, y0 + yOffset).buy(backgroundTile, map);
				}
			}
		}
	}

	@Override
	public Point2D getOrigin() {
		Point2D p = new Point2D(this);
		p.translate(-pos[0], -pos[1]);
		return p;
	}
}
