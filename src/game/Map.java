package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Map {
	
	private int width, height;
	private int[] mapData;
	
	public Map(String name) {
		//get BufferedReader
		InputStream stream = this.getClass().getResourceAsStream("/map/" + name + ".map");
		InputStreamReader streamreader = new InputStreamReader(stream);
		BufferedReader reader = new BufferedReader(streamreader);
		
		try {
			//get first line
			String line = reader.readLine().trim();
			
			//set width height
			width = Character.getNumericValue(line.charAt(0));
			height = Character.getNumericValue(line.charAt(2));
			
			mapData = new int[width * height];
			
			for (int y = 0; y < height; y++) {
				line = reader.readLine();
				if (line == null) {
					throw new IOException("Given map height is not the same as given height");
				}
				for (int x = 0; x < width; x++) {
					if (x > line.length()) {
						throw new IOException("Given map widthh is not the same as given width");
					}
					mapData[x + y * width] = Character.getNumericValue(line.charAt( x * 2));
				}
			}			
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
