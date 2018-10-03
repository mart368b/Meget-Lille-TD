package gfx.tiles;

import java.util.HashMap;

public class TileSetManager {

	private static HashMap<Integer, TileSet> sets = new HashMap<Integer, TileSet>();
	
	/**
	 * This TileSetManager keeps track of all the different tilesets.
	 * If a new tilesheet is added, it will need to be added here as well.
	 * 
	 * The new tilesheet needs an id and then the tilesheet.
	 */
	
	static {
		sets.put(0, new TileSet("/tilesets/level1_0_tilesheet.png", 32));
	}
	
	public static TileSet getTileset(int id){
		return sets.get(id);
	}
	
}
