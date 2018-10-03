package gfx.tiles;

import java.util.HashMap;

public class TileSetManager {

	private static HashMap<Integer, ImageTileSet> sets = new HashMap<Integer, ImageTileSet>();
	
	/**
	 * This TileSetManager keeps track of all the different tilesets.
	 * If a new tilesheet is added, it will need to be added here as well.
	 * 
	 * The new tilesheet needs an id and then the tilesheet.
	 */
	
	static {
		sets.put(0, new ImageTileSet("/tilesets/level1_0_tilesheet.png", 32));
	}
	
	public static ImageTileSet getTileset(int id){
		return sets.get(id);
	}
	
}
