package game.level;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import gfx.tiles.Tile;
import gfx.tiles.TileSetManager;
import gfx.tiles.Unicode;

public class Map {
	
	public static final HashMap<Character, Unicode> tiles = new HashMap<Character, Unicode>();
	
	public static final char SPAWNTILE = '\u27B2';
	public static final char ENDTILE = '\u26D4';
	// to get symbols either look at map files or use https://codepoints.net/U+XXXX
	// where XXXX is the number/letters
	// vertical line
	public static final char VERTICALPATH = '\u2502';
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
	
	//NEW
	public static final char GRASS = '\u25A1';
	public static final char WATER = '\u25A0';
	
	public static final char GRASS_TO_GROUND_LEFT = '\u25C0';
	public static final char GRASS_TO_GROUND_RIGHT = '\u25B6';
	public static final char GRASS_TO_GROUND_DOWN = '\u25BC';
	public static final char GRASS_TO_GROUND_UP = '\u25B2';
	
	public static final char WATER_TO_GROUND_UP = '\u25B3';
	public static final char WATER_TO_GROUND_DOWN = '\u25BD';
	public static final char WATER_TO_GROUND_LEFT = '\u25C1';
	public static final char WATER_TO_GROUND_RIGHT = '\u25B7';
	
	public static final char GRASS_TO_GROUND_SE = '\u2B08';
	public static final char GRASS_TO_GROUND_NE = '\u2B0A';
	public static final char GRASS_TO_GROUND_SW = '\u2B09';
	public static final char GRASS_TO_GROUND_NW = '\u2B0B';

	public static final char WATER_TO_GROUND_SE = '\u25F9';
	public static final char WATER_TO_GROUND_NW = '\u25FA';
	public static final char WATER_TO_GROUND_SW = '\u25F8';
	public static final char WATER_TO_GROUND_NE = '\u25FF';
	
	public static final char GRASS_TO_GROUND_SE2 = '\u2B00';
	public static final char GRASS_TO_GROUND_SW2 = '\u2B01';
	public static final char GRASS_TO_GROUND_NE2 = '\u2B02';
	public static final char GRASS_TO_GROUND_NW2 = '\u2B03';
	
	public static final char DOUBLE_STONE = '\u29DD';
	public static final char NORMAL_STONE = '\u25CB';
	public static final char BIG_STONE_TOP = '\u25E0';
	public static final char BIG_STONE_BUTTOM = '\u25E1';
	
	public static final char SMALL_TREE = '\u273D';
	public static final char BIG_TREE_TOP = '\u2724';
	public static final char BIG_TREE_BUTTOM = '\u2723';
	public static final char TREE_STUP = '\u271B';
	
	private void initUnicodes(){
		tiles.put(SPAWNTILE, Unicode.START);
		tiles.put(ENDTILE, Unicode.END);
		tiles.put(HORIZONTALPATH, Unicode.PATH);
		tiles.put(VERTICALPATH, Unicode.PATH);
		tiles.put(CORNER1, Unicode.PATH);
		tiles.put(CORNER2, Unicode.PATH);
		tiles.put(CORNER3, Unicode.PATH);
		tiles.put(CORNER4, Unicode.PATH);
		tiles.put(GRASS, Unicode.GRASS);
		tiles.put(WATER, Unicode.WATER);
		tiles.put(GRASS_TO_GROUND_LEFT, Unicode.GRASS_TO_PATH_LEFT);
		tiles.put(GRASS_TO_GROUND_RIGHT, Unicode.GRASS_TO_PATH_RIGHT);
		tiles.put(GRASS_TO_GROUND_DOWN, Unicode.GRASS_TO_PATH_DOWN);
		tiles.put(GRASS_TO_GROUND_UP, Unicode.GRASS_TO_PATH_UP);
		tiles.put(WATER_TO_GROUND_UP, Unicode.GRASS_TO_WATER_UP);
		tiles.put(WATER_TO_GROUND_DOWN, Unicode.GRASS_TO_WATER_DOWN);
		tiles.put(WATER_TO_GROUND_LEFT, Unicode.GRASS_TO_WATER_LEFT);
		tiles.put(WATER_TO_GROUND_RIGHT, Unicode.GRASS_TO_WATER_RIGHT);
		tiles.put(GRASS_TO_GROUND_SE, Unicode.GRASS_TO_PATH_SE);
		tiles.put(GRASS_TO_GROUND_SW, Unicode.GRASS_TO_PATH_SW);
		tiles.put(GRASS_TO_GROUND_NE, Unicode.GRASS_TO_PATH_NE);
		tiles.put(GRASS_TO_GROUND_NW, Unicode.GRASS_TO_PATH_NW);
		tiles.put(WATER_TO_GROUND_SE, Unicode.GRASS_TO_WATER_SE);
		tiles.put(WATER_TO_GROUND_NE, Unicode.GRASS_TO_WATER_NE);
		tiles.put(WATER_TO_GROUND_SW, Unicode.GRASS_TO_WATER_SW);
		tiles.put(WATER_TO_GROUND_NW, Unicode.GRASS_TO_WATER_NW);
		tiles.put(GRASS_TO_GROUND_SE2, Unicode.GRASS_TO_PATH_SE2);
		tiles.put(GRASS_TO_GROUND_SW2, Unicode.GRASS_TO_PATH_SW2);
		tiles.put(GRASS_TO_GROUND_NE2, Unicode.GRASS_TO_PATH_NE2);
		tiles.put(GRASS_TO_GROUND_NW2, Unicode.GRASS_TO_PATH_NW2);
		tiles.put(DOUBLE_STONE, Unicode.DOUBLE_STONE);
		tiles.put(NORMAL_STONE, Unicode.STONE);
		tiles.put(BIG_STONE_TOP, Unicode.BIG_STONE_TOP);
		tiles.put(BIG_STONE_BUTTOM, Unicode.BIG_STONE_BUTTOM);
		tiles.put(SMALL_TREE, Unicode.TREE);
		tiles.put(BIG_TREE_TOP, Unicode.BIG_TREE_TOP);
		tiles.put(BIG_TREE_BUTTOM, Unicode.BIG_TREE_BUTTOM);
		tiles.put(TREE_STUP, Unicode.TREE_STUP);
	}
	
	
	private int width, height;
	private String[] mapData;
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
		initUnicodes();
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
		return s.charAt(0);
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
		Unicode code = tiles.get(s.charAt(0));
		
		return TileSetManager.getTileset(tilesetID)
				.getTile(code.getValue());
	}
	
	
	@Deprecated public String[] getMapData() {
		return mapData;
	}

	public ArrayList<int[]> getSpawnPoints() {
		return spawnPoints;
	}
	
	public PathFinder getPath(int i) {
		return pathFinders[i];
	}
}
