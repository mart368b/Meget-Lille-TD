package game.level;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import game.entities.enemies.BasicEnemy;
import game.entities.enemies.Enemy;
import game.state.states.player.Round;
import gfx.tiles.Tile;
import gfx.tiles.TileSetManager;

public class Map {
	
	public static final int TILESIZE = 32;
	
	public static final char SPAWNTILE = 'X';
	public static final char ENDTILE = 'E';
	// to get symbols either look at map files or use https://codepoints.net/U+XXXX
	// where XXXX is the number/letters
	// vertical line
	public static final char VERTICALPATH = '|';
	// horizontal line
	public static final char HORIZONTALPATH = '\u2500';
	
	public static final char JUNCTION = '+';
	public static final char SPLITTER = 'V';
	
	// left - down
	public static final char CORNER1 = '\u2510';
	// right - down
	public static final char CORNER2 = '\u250C';
	// left - up
	public static final char CORNER3 = '\u2518';
	// right - up
	public static final char CORNER4 = '\u2514';
	
	private int width, height;
	private String[] mapData;
	private ArrayList<int[]> spawnPoints = new ArrayList<int[]>();
	private HashMap<int[], PathFinder[]> pathfinders;
	private PathFinder[] pathFinders;
	
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
			System.out.println(width);
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
				if (getTileC(x, y) == SPAWNTILE) {
					spawnPoints.add(new int[] { x, y});
				}
			}
		}

		pathFinders = new PathFinder[spawnPoints.size()];
		int i = 0;
		// find all defined paths through the map
		for (int[] point: spawnPoints) {
			pathFinders[i++] = new PathFinder(point[0], point[1], this);			
		}
		// use pathFinder.nextPath() to get the next path (it just cycles through them)
	}
	
	public char toChar(String s){
		return s.charAt(1);
	}
	
	public String getTile(int x, int y) {
		return mapData[x + y * width];
	}
	
	public char getTileC(int x, int y) {
		return toChar(mapData[x + y * width]);
	}
	
	public void render(Graphics2D g2) {
		
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}	
	
	//IDK if this is the correct way to get data from this class .-.
	
	public Tile getTexture(String s, int tilesetID){
		
		char c = toChar(s);
		if(c == SPAWNTILE){
			return TileSetManager.getTileset(tilesetID).getTile(2);
		}else if(c == ENDTILE){
			return TileSetManager.getTileset(tilesetID).getTile(3);
		}else if((c == VERTICALPATH) || (c == HORIZONTALPATH) || (c == CORNER1)
				 || (c == CORNER2) || (c == CORNER3) || (c == CORNER4)){
			return TileSetManager.getTileset(tilesetID).getTile(0);
		}else{
			return TileSetManager.getTileset(tilesetID).getTile(Integer.parseInt(s));
		}
	}

	public ArrayList<int[]> getSpawnPoints() {
		return spawnPoints;
	}
	
	public Iterator<int[]> getSpawnPointIterator() {
		return spawnPoints.iterator();
	}
	
	public int[] getSpawnPoint(int i) {
		return spawnPoints.get(i);
	}
	
	public int getSpawnPointCount() {
		return spawnPoints.size();
	}
	
	public PathFinder getPathFinder(int[] spawnPoint) {
		for(int i = 0; i < spawnPoints.size(); i++) {
			if (spawnPoints.get(i).equals(spawnPoint)) {
				return pathFinders[i];
			}
		}
		return null;
	}
}
