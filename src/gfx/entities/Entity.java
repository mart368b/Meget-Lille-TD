package gfx.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Entity {

	private BufferedImage image;
	
	private int health;
	private int x, y;
	
	private double movespeed;
	
	private boolean alive;
	
	/**
	 * 
	 * Entity class for all entities.
	 * 
	 * @param filePath The image's filepath in the 'res' folder. Could be /entities/ass.png
	 * @param health The health of the entity
	 */
	public Entity(String filePath, int health){
		try{
			image = ImageIO.read(getClass().getResourceAsStream(filePath));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		this.health = health;
		this.alive = true;
	}
	
	public void update(){
		if(!alive){
			return;
		}
		//TODO
	}
	
	public void render(Graphics2D g){
		if(!alive){
			return;
		}
		g.drawImage(image, x, y, null);
	}
	
	public void move(){
		
	}
	
	public void hit(int damage){
		if((health - damage) <= 0){
			alive = false;
		}else{
			health -= damage;
		}
	}
}
