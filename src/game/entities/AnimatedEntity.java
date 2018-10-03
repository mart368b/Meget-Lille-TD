package game.entities;

import java.awt.Graphics2D;

import gfx.sprites.Animation;
import gfx.sprites.Sprite;

public class AnimatedEntity extends Entity {
	
	protected Animation animation;

	public AnimatedEntity(double x, double y, Sprite sprite, int animationSpeed){
		super(x, y);
		this.animation = new Animation(sprite.getFrames(0), animationSpeed);
	}
	
	public void tick(){
		animation.tick();
	}
	
	public void render(Graphics2D g2){
		animation.render(g2, getX(), getY());
	}
}
