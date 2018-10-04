package game.entities.tiles;

public enum TileLibrary {
	// obsticals
	SPAWNTILE		(new Obstical('\u27B2', ImageIndex.START, 				0)),
	ENDTILE			(new Obstical('\u26D4', ImageIndex.END, 				0)),
	VERTICALPATH	(new Obstical('\u2502', ImageIndex.PATH, 				0)),
	HORIZONTALPATH	(new Obstical('\u2500', ImageIndex.PATH, 				0)),
	CORNER1			(new Obstical('\u2510', ImageIndex.PATH, 				0)),
	CORNER2			(new Obstical('\u250C', ImageIndex.PATH, 				0)),
	CORNER3			(new Obstical('\u2518', ImageIndex.PATH, 				0)),
	CORNER4			(new Obstical('\u2514', ImageIndex.PATH, 				0)),
	WATER			(new Obstical('\u25A0', ImageIndex.WATER, 				0)),
	SPLITTER		(new Obstical('V', ImageIndex.SPLITTER, 				0)),
	JUNCTION		(new Obstical('+', ImageIndex.JUNCTION, 				0)),
	
	//buyable
	NORMAL_STONE	(new BuyableTile('\u25CB', ImageIndex.STONE, 			0,		100)),
	DOUBLE_STONE	(new BuyableTile('\u29DD', ImageIndex.DOUBLE_STONE, 	0,		100)),
	SMALL_TREE		(new BuyableTile('\u273D', ImageIndex.TREE, 			0,		100)),
	TREE_STUP		(new BuyableTile('\u271B', ImageIndex.TREE_STUP, 		0,		100)),
	//large buyable
	BIG_STONE_TOP	(new LargeBuyableTile('\u25E0', ImageIndex.BIG_STONE_TOP, 	0,	300, 1, 2, new int[] { 0, 0})),
	BIG_STONE_BUTTOM(new LargeBuyableTile('\u25E1', ImageIndex.BIG_STONE_BUTTOM,0,	300, 1, 2, new int[] { 0, 1})),
	BIG_TREE_TOP	(new LargeBuyableTile('\u2724', ImageIndex.BIG_TREE_TOP, 	0,	300, 1, 2, new int[] { 0, 0})),
	BIG_TREE_BUTTOM	(new LargeBuyableTile('\u2723', ImageIndex.BIG_TREE_BUTTOM, 0,	300, 1, 2, new int[] { 0, 1})),
	
	//default tiles
	GRASS			(new Tile(    '\u25A1', ImageIndex.GRASS, 				0)),
	GRASS_LEFT		(new Tile(    '\u25C0', ImageIndex.GRASS_TO_PATH_LEFT, 	0)),
	GRASS_RIGHT		(new Tile(    '\u25B6', ImageIndex.GRASS_TO_PATH_RIGHT, 0)),
	GRASS_DOWN		(new Tile(    '\u25BC', ImageIndex.GRASS_TO_PATH_DOWN,	0)),
	GRASS_UP		(new Tile(    '\u25B2', ImageIndex.GRASS_TO_PATH_UP, 	0)),
	WATER_UP		(new Tile(    '\u25B3', ImageIndex.GRASS_TO_WATER_UP, 	0)),
	WATER_DOWN		(new Tile(    '\u25BD', ImageIndex.GRASS_TO_WATER_DOWN, 0)),
	WATER_LEFT		(new Tile(    '\u25C1', ImageIndex.GRASS_TO_WATER_LEFT, 0)),
	WATER_RIGHT		(new Tile(    '\u25B7', ImageIndex.GRASS_TO_WATER_RIGHT,0)),
	GRASS_SE		(new Tile(    '\u2B08', ImageIndex.GRASS_TO_PATH_SE, 	0)),
	GRASS_NE		(new Tile(    '\u2B0A', ImageIndex.GRASS_TO_PATH_NE, 	0)),
	GRASS_SW		(new Tile(    '\u2B09', ImageIndex.GRASS_TO_PATH_SW, 	0)),
	GRASS_NW		(new Tile(    '\u2B0B', ImageIndex.GRASS_TO_PATH_NW, 	0)),
	WATER_SE		(new Tile(    '\u25F9', ImageIndex.GRASS_TO_WATER_SE, 	0)),
	WATER_NW		(new Tile(    '\u25FA', ImageIndex.GRASS_TO_WATER_NW, 	0)),
	WATER_SW		(new Tile(    '\u25F8', ImageIndex.GRASS_TO_WATER_SW, 	0)),
	WATER_NE		(new Tile(    '\u25FF', ImageIndex.GRASS_TO_WATER_NE, 	0)),
	GRASS_SE2		(new Tile(    '\u2B00', ImageIndex.GRASS_TO_PATH_SE2, 	0)),
	GRASS_SW2		(new Tile(    '\u2B01', ImageIndex.GRASS_TO_PATH_SW2, 	0)),
	GRASS_NE2		(new Tile(    '\u2B02', ImageIndex.GRASS_TO_PATH_NE2, 	0)),
	GRASS_NW2		(new Tile(    '\u2B03', ImageIndex.GRASS_TO_PATH_NW2, 	0));
	
	private final Tile tile;
	
	TileLibrary(final Tile tile) {
		this.tile = tile;
    }
	
	public Tile getValue() {
		return tile; 
	}
	
	public static Tile getTile(char c) {
		for (TileLibrary lib: TileLibrary.values()) {
			Tile t = lib.getValue(); 
			if (t.getId() == c) {
				return t;
			}
		}
		return null;
	}
	
	public char getChar(int i) {
		return tile.getId();
	}
}
