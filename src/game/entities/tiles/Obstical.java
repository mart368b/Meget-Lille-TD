package game.entities.tiles;

public class Obstical extends Tile{

	public Obstical(char id, int imgIndex, int setIndex) {
		super(id, imgIndex, setIndex, true);
	}
	
	public Obstical(double x, double y, char id, int imgIndex, int setIndex) {
		super(x, y, id, imgIndex, setIndex, true);
	}
	
	@Override
	public Tile createCopy(double x, double y) {
		return new Obstical(x, y, id, imgIndex, setIndex);
	}

}
