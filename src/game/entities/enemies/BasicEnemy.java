package game.entities.enemies;

import java.awt.geom.Point2D;

import game.Game;
import game.level.WalkingPath;
import gfx.sprites.SpriteManager;

public class BasicEnemy extends Enemy{

	public BasicEnemy(double x, double y) {
		super(
				SpriteManager.getSprite(0), 
				x,	y, 
				Game.config.getInt("classic.animationspeed"), 
				Game.config.getInt("classic.health"), 
				Game.config.getInt("classic.speed")
				);
	}
	
	public BasicEnemy(Point2D p0) {
		super(
				SpriteManager.getSprite(0), 
				p0.getX(),	p0.getY(), 
				Game.config.getInt("classic.animationspeed"), 
				Game.config.getInt("classic.health"), 
				Game.config.getInt("classic.speed")
				);
	}
	
	public BasicEnemy(WalkingPath path) {
		super(
				SpriteManager.getSprite(0), 
				path, 
				Game.config.getInt("classic.animationspeed"), 
				Game.config.getInt("classic.health"), 
				Game.config.getInt("classic.speed")
				);
	}
}
