package game.level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Map {
	
	private int width, height;
	private String[] mapData;
	private ArrayList<int[]> spawnPoints = new ArrayList<int[]>();
	
	private static final String SPAWNTILE = "X"; 
	private static final String HORIZONTALPATH = "-";
	private static final String SPLITPATH = "+";
	
	public Map(String fileName) {
		//get BufferedReader
		loadMap(fileName);		
		loadSpawnPoints();		
	}

	private void loadMap(String fileName) {
		InputStream stream = this.getClass().getResourceAsStream("/map/" + fileName + ".map");
		InputStreamReader streamreader = new InputStreamReader(stream);
		BufferedReader reader = new BufferedReader(streamreader);
		
		try {
			//get first line
			String line = reader.readLine().trim();
			
			//set width height
			width = Character.getNumericValue(line.charAt(0));
			height = Character.getNumericValue(line.charAt(2));
			
			String data = "";
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				data += line + " ";
			}
			mapData = data.split(" ");
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void loadSpawnPoints() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (getTile(x, y) == SPAWNTILE) {
					spawnPoints.add(new int[] { x, y});
				}
			}
		}
	}
	
	public String getTile(int x, int y) {
		return mapData[x + y * width];
	}
}
