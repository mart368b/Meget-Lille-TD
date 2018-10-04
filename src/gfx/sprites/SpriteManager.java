package gfx.sprites;

import java.util.HashMap;

import game.entities.towers.BasicTower;

public class SpriteManager {

	private static HashMap<Integer, Sprite> sprites = new HashMap<Integer, Sprite>();
	
	/**
	 * This SpriteManager keeps track of all the different sprites.
	 * If a new spritesheet is added, it will need to be added here as well.
	 * 
	 * The new spritesheet needs an id and then the sprite.
	 */
	
	static {
		String path = "/sprites/enemies/";
		//enemies 0 - 19
		sprites.put(0, new Sprite(path + "SheepSheet.png", 32, 32));
		
		path = "/sprites/towers/";
		//towers 20 - ??
		sprites.put(20, new Sprite(path + "BasicTower.png", 64, 64));
		sprites.put(21, new Sprite(path + "HomeTower.png", 64, 64));
	}
	
	public static Sprite getSprite(int id){
		return sprites.get(id);
	}
	
	
}
