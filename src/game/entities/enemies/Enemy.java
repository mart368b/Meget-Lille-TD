package game.entities.enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.Game;
import game.entities.AnimatedEntity;
import game.level.WalkingPath;
import gfx.sprites.Sprite;

public class Enemy extends AnimatedEntity {
	
	private int health;
	private int speed;
	private Sprite sprite;
	private WalkingPath path;
	
	protected boolean alive = true, end = false;
	protected double progress = 0;

	/**
	 * Create a stationary Enemy
	 */
	public Enemy(Sprite sprite, double x, double y, int animationSpeed, int health, int speed) {
		super(x, y, sprite, animationSpeed);
		this.health = health;
		this.speed = speed;
		this.sprite = sprite; 
	}
	
	/**
	 * Create a Enemy that moves along the path
	 */
	public Enemy(Sprite sprite, WalkingPath path, int animationSpeed, int health, int speed) {
		super(path.getStartingX(), path.getStartingY(), sprite, animationSpeed);
		this.health = health;
		this.speed = speed;
		this.path = path;
		this.sprite = sprite;
	}
	
	/**
	 * hit the enemy
	 */
	public void hit(int damage){
		if((health -= damage) <= 0){
			alive = false;
		}
	}
	
	/**
	 * do movement if possible
	 */
	@Override
	public void tick() {
		super.tick();
		if (active && alive && path != null && !reachedEnd()) {
			move();			
		}
	}
	
	/**
	 * move Enemy
	 */
	public void move() {
		progress += (speed + 0.)/Game.tps;
		moveTo(path.getPosition(progress));
		
		int dic = path.getDirection();
		BufferedImage[] frames = sprite.getFrames(dic);
		animation.setFrames(frames);
		
		if (progress > path.getLength()) {
			end = true;
		}
	}
	
	/**
	 * test if end if line i reached
	 * @return return false if no path is present
	 */
	public boolean reachedEnd() {
		return end;
	}
	
	public boolean isDead() {
		return !alive;
	}
	
	public void kill() {
		alive = false;
	}
	
	public boolean hasPath() {
		return path != null;
	}
	
	@Override
	public void render(Graphics2D g2) {
		super.render(g2);
	}
	
	public Enemy clone() {
		return new Enemy(sprite, path, animation.getAnimationSpeed(), health, speed);
	}
	
	public Enemy clone(WalkingPath path) {
		return new Enemy(sprite, path, animation.getAnimationSpeed(), health, speed);
	}
}
