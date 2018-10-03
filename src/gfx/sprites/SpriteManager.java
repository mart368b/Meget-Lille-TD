package gfx.sprites;

import java.util.HashMap;

public class SpriteManager {

	private static HashMap<Integer, Sprite> sprites = new HashMap<Integer, Sprite>();
	
	/**
	 * This SpriteManager keeps track of all the different sprites.
	 * If a new spritesheet is added, it will need to be added here as well.
	 * 
	 * The new spritesheet needs an id and then the sprite.
	 */
	
	static {
		sprites.put(0, new Sprite("/sprites/enemies/SheepSheet.png", 32));
	}
	
	public static Sprite getSprite(int id){
		return sprites.get(id);
	}
	
	
}
