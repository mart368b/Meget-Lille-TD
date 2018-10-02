package game.level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

public class MapReader {
	
	private int width, height;
	private char[] mapData;
	
	public MapReader(String fileName) throws IOException {
		URL u = this.getClass().getResource("/map/" + fileName + ".map");
		FileInputStream stream = new FileInputStream(new File(u.getPath()));
		InputStreamReader streamreader = new InputStreamReader(stream, "UTF8");
		BufferedReader reader = new BufferedReader(streamreader);
		
		//get first line
		String line = reader.readLine().trim();
		
		//set width height
		width = Character.getNumericValue(line.charAt(1));
		height = Character.getNumericValue(line.charAt(3));
		
		//get string array
		String data = "";
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			data += line + " ";
		}
		String[] stringData = data.split(" ");
		
		//convert to char array
		mapData = new char[stringData.length];
		for (int i = 0; i < mapData.length; i++) {
			mapData[i] = stringData[i].charAt(0);
		}
		
		reader.close();
		stream.close();
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public char[] getMapData() {
		return mapData;
	}

}
