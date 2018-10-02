package config;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Configuration {

	private String fileName;
	private InputStream file;
	private InputStreamReader isr;
	private HashMap<String, String> paths;
	
	public Configuration(String name){
		fileName = name;
		file = this.getClass().getResourceAsStream("/" + fileName + ".txt");
		isr = new InputStreamReader(file);
		paths = new HashMap<String, String>();
		init();
	}
	
	public Configuration(){
		fileName = "config";
		String filePath = "/" + fileName + ".txt";
		file = this.getClass().getResourceAsStream(filePath);
		isr = new InputStreamReader(file);
		paths = new HashMap<String, String>();
		init();
	}

	private void init(){
		boolean running = true;
		String path = "";
		BufferedReader br = new BufferedReader(isr);
		while(running){
			try {
				String line = br.readLine();
				if(line == null){
					running = false;
					break;
				}else if(line.equals("")){
					running = false;
					break;
				}
				if(line.startsWith("//")){
					continue;
				}
				if(!line.startsWith(" ") && !line.startsWith("	")){
					path = "";
				}
				if(line.contains(":")){
					if(line.endsWith(":")){
						path += line.replace(":", ".");
					}else{
						String[] elements = line.split(":");
						String temp = path;
						path += elements[0].replaceAll("\\s", "");
						paths.put(path, elements[1].replaceAll("\\s", ""));
						path = temp;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getInt(String path){
		try{
			return Integer.parseInt(paths.get(path));
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	
	public double getDouble(String path){
		try{
			return Double.parseDouble(paths.get(path));
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0D;
	}
	
	public float getFloat(String path){
		try{
			return Float.parseFloat(paths.get(path));
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0F;
	}
	
	public boolean getBoolean(String path){
		try{
			return Boolean.parseBoolean(paths.get(path));
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public String getString(String path){
		try{
			return paths.get(path);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	} 
}
