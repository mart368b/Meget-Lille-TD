package game.entities;

import java.awt.Graphics2D;

import gfx.sprites.Animation;
import gfx.sprites.Sprite;

/**
 * 
 * Base class for all all entities.
 *
 * @param health The health of the entity
 */
public class Entity {
	
	private Animation animation;
	
	private int health;
	private int speed;
	private int x, y;
	
	private boolean alive;
	
	public Entity(Sprite sprite, int health, int speed, int animationSpeed){
		this.animation = new Animation(sprite.getFrames(), animationSpeed);
		this.health = health;
		this.speed = speed;
		this.alive = true;
		this.x = 0;
		this.y = 0;
	}
	
	public void tick(){
		animation.tick();
	}
	
	public void render(Graphics2D g){
		animation.render(g, x, y);
	}
	
	public void hit(int damage){
		if((health -= damage) <= 0){
			alive = false;
		}
	}
}
