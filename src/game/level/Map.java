package game.level;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;

public class Map {
	
	public static final char SPAWNTILE = 'X';
	// to get symbols either look at map files or use https://codepoints.net/U+XXXX
	// where XXXX is the number/letters
	// vertical line
	public static final char VERTICALPATH = '|';
	// horizontal line
	public static final char HORIZONTALPATH = '\u2500';
	
	public static final char JUNCTION = '+';
	public static final char SPLITTER = 'V';
	/*
	 * NOTE: the following are using outwards direction e.g a corner represented as the top left corner of a cube
	 * would be called right - down
	 * But take note people walking on the line would enter in the opposit direction
	 * so either go enter going left or upwards
	 */
	// left - down
	public static final char CORNER1 = '\u2510';
	// right - down
	public static final char CORNER2 = '\u250C';
	// left - up
	public static final char CORNER3 = '\u2518';
	// right - up
	public static final char CORNER4 = '\u2514';
	
	private int width, height;
	private char[] mapData;
	private ArrayList<int[]> spawnPoints = new ArrayList<int[]>();
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

		pathFinders = new PathFinder[spawnPoints.size()];
		int i = 0;
		// find all defined paths through the map
		for (int[] point: spawnPoints) {
			pathFinders[i++] = new PathFinder(point[0], point[1], this);			
		}
		// use pathFinder.nextPath() to get the next path (it just cycles through them)
	}
	
	public char getTile(int x, int y) {
		return mapData[x + y * width];
	}
	
	public void render(Graphics2D g2) {
		
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
