package game.entities.tiles;

import game.entities.tiles.Tile;

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
	GRASS_LEFT		(new Obstical('\u25C0', ImageIndex.GRASS_TO_PATH_LEFT, 	0)),
	GRASS_RIGHT		(new Obstical('\u25B6', ImageIndex.GRASS_TO_PATH_RIGHT, 0)),
	GRASS_DOWN		(new Obstical('\u25BC', ImageIndex.GRASS_TO_PATH_DOWN,	0)),
	GRASS_UP		(new Obstical('\u25B2', ImageIndex.GRASS_TO_PATH_UP, 	0)),
	WATER_UP		(new Obstical('\u25B3', ImageIndex.GRASS_TO_WATER_UP, 	0)),
	WATER_DOWN		(new Obstical('\u25BD', ImageIndex.GRASS_TO_WATER_DOWN, 0)),
	WATER_LEFT		(new Obstical('\u25C1', ImageIndex.GRASS_TO_WATER_LEFT, 0)),
	WATER_RIGHT		(new Obstical('\u25B7', ImageIndex.GRASS_TO_WATER_RIGHT,0)),
	GRASS_SE		(new Obstical('\u2B08', ImageIndex.GRASS_TO_PATH_SE, 	0)),
	GRASS_NE		(new Obstical('\u2B0A', ImageIndex.GRASS_TO_PATH_NE, 	0)),
	GRASS_SW		(new Obstical('\u2B09', ImageIndex.GRASS_TO_PATH_SW, 	0)),
	GRASS_NW		(new Obstical('\u2B0B', ImageIndex.GRASS_TO_PATH_NW, 	0)),
	WATER_SE		(new Obstical('\u25F9', ImageIndex.GRASS_TO_WATER_SE, 	0)),
	WATER_NW		(new Obstical('\u25FA', ImageIndex.GRASS_TO_WATER_NW, 	0)),
	WATER_SW		(new Obstical('\u25F8', ImageIndex.GRASS_TO_WATER_SW, 	0)),
	WATER_NE		(new Obstical('\u25FF', ImageIndex.GRASS_TO_WATER_NE, 	0)),
	GRASS_SE2		(new Obstical('\u2B00', ImageIndex.GRASS_TO_PATH_SE2, 	0)),
	GRASS_SW2		(new Obstical('\u2B01', ImageIndex.GRASS_TO_PATH_SW2, 	0)),
	GRASS_NE2		(new Obstical('\u2B02', ImageIndex.GRASS_TO_PATH_NE2, 	0)),
	GRASS_NW2		(new Obstical('\u2B03', ImageIndex.GRASS_TO_PATH_NW2, 	0)),
	DOUBLE_STONE	(new Obstical('\u29DD', ImageIndex.DOUBLE_STONE, 		0)),
	NORMAL_STONE	(new Obstical('\u25CB', ImageIndex.STONE, 				0)),
	BIG_STONE_TOP	(new Obstical('\u25E0', ImageIndex.BIG_STONE_TOP, 		0)),
	BIG_STONE_BUTTOM(new Obstical('\u25E1', ImageIndex.BIG_STONE_BUTTOM, 	0)),
	SMALL_TREE		(new Obstical('\u273D', ImageIndex.TREE, 				0)),
	BIG_TREE_TOP	(new Obstical('\u2724', ImageIndex.BIG_TREE_TOP, 		0)),
	BIG_TREE_BUTTOM	(new Obstical('\u2723', ImageIndex.BIG_TREE_BUTTOM, 	0)),
	TREE_STUP		(new Obstical('\u271B', ImageIndex.TREE_STUP, 			0)),
	SPLITTER		(new Obstical('V', ImageIndex.SPLITTER, 				0)),
	JUNCTION		(new Obstical('+', ImageIndex.JUNCTION, 				0)),
	
	//default tiles
	GRASS			(new Tile(    '\u25A1', ImageIndex.GRASS, 				0));
	
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
