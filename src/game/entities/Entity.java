package game.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

/**
 * 
 * Base class for all all entities.
 *
 * @param health The health of the entity
 */
public class Entity {
	
	private int health;
	private int x, y;
	
	private boolean alive;
	
	public Entity(int health){
		
		this.health = health;
		this.alive = true;
	}
	
	public void tick(){}
	
	public void render(Graphics2D g){}
	
	public void hit(int damage){
		if((health -= damage) <= 0){
			alive = false;
		}
	}
}
