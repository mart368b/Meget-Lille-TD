package game.level;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import game.entities.tiles.Tile;
import libaries.TileLibrary;

public class Map {	
	
	private int width, height;
	private Tile[] mapData;
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
				if (getTile(x, y).equals(TileLibrary.SPAWNTILE.getValue())) {
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
	
	public Tile getTile(int x, int y) {
		return mapData[x + y * width];
	}
	
	public void render(Graphics2D g2) {
		for(Tile t: mapData) {
			t.render(g2);
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}	
	
	//IDK if this is the correct way to get data from this class .-.

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
