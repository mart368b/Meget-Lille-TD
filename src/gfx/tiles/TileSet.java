package gfx.tiles;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class TileSet {
	
	public static enum TileType{PATH, BUILD, UNBUILDABLE};
	
	private int tileSize;
	private BufferedImage tileSheet;
	private int width,
				height;
	
	private ArrayList<Tile> tiles = new ArrayList<Tile>();
	
	public TileSet(String filePath, int tileSize){
		this.tileSize = tileSize;
		try{
			tileSheet = ImageIO.read(getClass().getResourceAsStream(filePath));
		}catch(Exception e){
			e.printStackTrace();
		}
		this.width = tileSheet.getWidth();
		this.height = tileSheet.getHeight();
		
		int id = 0;
		
		for(int row = 0; row < height/tileSize; row++){
			for(int col = 0; col < width/tileSize; col++){
				BufferedImage subImage = tileSheet.getSubimage(tileSize * col, 
						tileSize * row, tileSize, tileSize);
				if(row == 0){
					tiles.add(new Tile(id, subImage, TileType.PATH));
				}else if(row < 3){
					tiles.add(new Tile(id, subImage, TileType.UNBUILDABLE));
				}else{
					tiles.add(new Tile(id, subImage, TileType.BUILD));
				}
				id++;
			}
		}
	}
	
	public Tile getTile(int id){
		for(Tile tile : tiles){
			if(tile.getId() == id){
				return tile;
			}
		}
		return null;
	}
	
	public int getTileSize(){
		return tileSize;
	}
}
