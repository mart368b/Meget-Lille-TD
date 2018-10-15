package game.entities.enemies;

import java.awt.geom.Point2D;

import game.Game;
import game.level.WalkingPath;
import gfx.sprites.SpriteManager;

public class BasicEnemy extends Enemy{

	public BasicEnemy(String name, int id) {
		super(
				SpriteManager.getSprite(id), 
				0,	0, 
				Game.config.getInt(name + ".animationspeed"), 
				Game.config.getInt(name + ".health"), 
				Game.config.getInt(name + ".speed"),
				Game.config.getInt(name + ".gold")
				);
	}
	
	public BasicEnemy(double x, double y, String name, int id) {
		super(
				SpriteManager.getSprite(id), 
				x,	y, 
				Game.config.getInt(name + ".animationspeed"), 
				Game.config.getInt(name + ".health"), 
				Game.config.getInt(name + ".speed"),
				Game.config.getInt(name + ".gold")
				);
	}
	
	public BasicEnemy(Point2D p0, String name, int id) {
		super(
				SpriteManager.getSprite(id), 
				p0.getX(),	p0.getY(), 
				Game.config.getInt(name + ".animationspeed"), 
				Game.config.getInt(name + ".health"), 
				Game.config.getInt(name + ".speed"),
				Game.config.getInt(name + ".gold")
				);
	}
	
	public BasicEnemy(WalkingPath path, String name, int id) {
		super(
				SpriteManager.getSprite(id), 
				path, 
				Game.config.getInt(name + ".animationspeed"), 
				Game.config.getInt(name + ".health"), 
				Game.config.getInt(name + ".speed"),
				Game.config.getInt(name + ".gold")
				);
	}
}
