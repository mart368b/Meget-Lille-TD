package game.level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import game.entities.tiles.Tile;
import game.entities.tiles.TileLibrary;

public class MapReader {
	
	private int width, height;
	private Tile[] mapData;
	
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
		mapData = new Tile[stringData.length - 1];
		for (int x = 0; x <= width - 1; x++) {
			for (int y = 0; y <= height - 1; y++) {
				int i = x + y * width;
				char c = stringData[i].charAt(0);
				Tile t = TileLibrary.getTile(c);
				mapData[i] = t.createCopy(x, y);
			}
		}

		stream.close();
	}
	
	public void loadData(String[] stringData) {
		
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Tile[] getMapData() {
		return mapData;
	}

}
