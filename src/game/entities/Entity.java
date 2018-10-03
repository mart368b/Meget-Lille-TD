package game.entities;

import java.awt.Graphics2D;

import game.math.Point2D;
import gfx.sprites.Animation;
import gfx.sprites.Sprite;

/**
 * 
 * Base class for all all entities.
 *
 * @param health The health of the entity
 */
public class Entity extends Point2D{
	
	protected Animation animation;
	
	protected boolean active = true;
	
	public Entity(double x, double y, Sprite sprite, int animationSpeed){
		super(x, y);
		this.animation = new Animation(sprite.getFrames(), animationSpeed);
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void tick(){
		animation.tick();
	}
	
	public void render(Graphics2D g2){
		animation.render(g2, getX(), getY());
	}
	public void spawn(int[] location){
		moveTo(location[0], location[1]);
	}
}
