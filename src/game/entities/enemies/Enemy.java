package game.entities.enemies;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import game.Game;
import game.entities.AnimatedEntity;
import game.entities.tiles.Tile;
import game.level.WalkingPath;
import game.math.Point2D;
import gfx.HUD;
import gfx.sprites.Sprite;

public class Enemy extends AnimatedEntity {
	
	private int health, maxHealth, speed;
	private Sprite sprite;
	private WalkingPath path;
	private int gold;
	
	protected boolean alive = true, end = false;
	protected double progress = 0;

	/**
	 * Create a stationary Enemy
	 */
	public Enemy(Sprite sprite, double x, double y, int animationSpeed, int health, int speed, int gold) {
		super(x, y, sprite, animationSpeed);
		this.health = health;
		this.maxHealth = health;
		this.speed = speed;
		this.sprite = sprite; 
		this.gold = gold;
	}
	
	/**
	 * Create a Enemy that moves along the path
	 */
	public Enemy(Sprite sprite, WalkingPath path, int animationSpeed, int health, int speed, int gold) {
		super(path.getStartingX(), path.getStartingY(), sprite, animationSpeed);
		this.health = health;
		this.maxHealth = health;
		this.speed = speed;
		this.path = path;
		this.sprite = sprite;
		this.gold = gold;
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
	
	public Point2D getCenter() {
		Point2D p = new Point2D(this);
		p.translate(Tile.TILESIZE/2, Tile.TILESIZE/2);
		return p;
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
		if (progress >= path.getLength() - 1) {
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
	
	public void setRoundScaling(int round) {
		maxHealth *= 1 + (round + 0.)/4;
		health = maxHealth;
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
		
		g2.setColor(HUD.healthColors.getColor(((health + 0.)/maxHealth) * (HUD.healthColors.size()-1) ));
		g2.fill(new Rectangle2D.Double(getX() + 3, getY() - 7, (Tile.TILESIZE - 6) * ((health + 0.)/maxHealth), 8));
	}
	
	public Enemy clone() {
		return new Enemy(sprite, path, animation.getAnimationSpeed(), health, speed, gold);
	}
	
	public Enemy clone(WalkingPath path) {
		return new Enemy(sprite, path, animation.getAnimationSpeed(), health, speed, gold);
	}
	
	public double getProgress() {
		return progress;
	}
	
	public int getGold() {
		return gold;
	}
}
