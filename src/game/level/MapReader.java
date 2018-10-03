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
	private String[] mapData;
	
	public MapReader(String fileName) throws IOException {
		URL u = this.getClass().getResource("/map/" + fileName + ".map");
		FileInputStream stream = new FileInputStream(new File(u.getPath()));
		InputStreamReader streamreader = new InputStreamReader(stream, "UTF-8");
		BufferedReader reader = new BufferedReader(streamreader);
		
		//get first line
		String infos = reader.readLine();
		String[] info = infos.trim().split(" ");
		
		//set width height
		width = Integer.parseInt(info[0]);
		height = Integer.parseInt(info[1]);
		
		//get string array
		String data = "";
		String line;
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			data += line + " ";
		}
		String[] stringData = data.split(" ");
		
		//convert to char array
		mapData = new String[stringData.length];
		for (int i = 0; i < mapData.length; i++) {
			mapData[i] = stringData[i];
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
	
	public String[] getMapData() {
		return mapData;
	}

}
