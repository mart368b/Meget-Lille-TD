package game.level;

import java.io.IOException;
import java.util.ArrayList;

import gfx.tiles.Tile;
import gfx.tiles.TileSetManager;

public class Map {
	
	public static final char SPAWNTILE = 'X';
	// to get symbols either look at map files or use https://codepoints.net/U+XXXX
	// where XXXX is the number/letters
	// vertical line
	public static final char VERTICALPATH = '|';
	// horizontal line
	public static final char HORIZONTALPATH = '\u2500';
	public static final char JUNCTION = '+';
	/*
	 * using outwards direction the top left corner of a cube
	 * would be called right - down
	 * but people walking along the lines would enter in the opposite direction
	 * so either go left or go up
	 */
	// left - down
	public static final char CORNER1 = '\u2510';
	// right - down
	public static final char CORNER2 = '\u250C';
	// left - up
	public static final char CORNER3 = '\u2518';
	// right - up
	public static final char CORNER4 = '\u2514';
	public static final char SPLITTER = 'V';
	
	private int width, height;
	private char[] mapData;
	private ArrayList<int[]> spawnPoints = new ArrayList<int[]>();
	private PathFinder pathFinder;
	
	public Map(String fileName) {
		// create map
		loadMap(fileName);
		// generate enemy spawn and pathing
		loadSpawnPoints();	
	}

	private void loadMap(String fileName) {
		
		try {
			MapReader reader = new MapReader(fileName);
			
			width = reader.getWidth();
			height = reader.getHeight();
			
			mapData = reader.getMapData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadSpawnPoints() {
		// find spawn points
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (getTile(x, y) == SPAWNTILE) {
					spawnPoints.add(new int[] { x, y});
				}
			}
		}
		
		// find all defined paths through the map
		pathFinder = new PathFinder(spawnPoints.get(0)[0], spawnPoints.get(0)[1], this);
		
		// use pathFinder.nextPath() to get the next path (it just cycles through them)
	}
	
	public char getTile(int x, int y) {
		return mapData[x + y * width];
	}
	
	
	
	
	//IDK if this is the correct way to get data from this class .-.
	
	public Tile getTexture(char c, int tilesetID){
		//TODO
		return null;
	}
	
	public char[] getMapData() {
		return mapData;
	}

	public ArrayList<int[]> getSpawnPoints() {
		return spawnPoints;
	}

	public PathFinder getPathFinder() {
		return pathFinder;
	}
}
