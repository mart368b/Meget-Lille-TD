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
	
	protected boolean active = true;
	
	public Entity(double x, double y){
		super(x, y);
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Entity clone() {
		return new Entity(getX(), getY());
	}
	
	public void tick(){}
	
	public void render(Graphics2D g2){}
}
