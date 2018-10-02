package game.entities.enemies;

import game.Game;
import game.entities.Entity;
import gfx.sprites.SpriteManager;

public class Classic extends Entity {
	
	public Classic() {
		super(SpriteManager.getSprite(0), 
				Game.config.getInt("classic.health"), 
				Game.config.getInt("classic.speed"),
				Game.config.getInt("classic.animationspeed"));
	}
}
