package gfx.sprites;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Animation {

	private BufferedImage[] frames;
	
	private int currentFrame;
	private int speed;
	
	private int tick;
	
	/**
	 * The animation class is a simply class that goes through an array of images.
	 * The image is changed after a set period.
	 * 
	 * @param frames The images of which the animation to iterate over
	 * @param speed The speed of the iteration. 60 being 1 second.
	 */
	
	public Animation(BufferedImage[] frames, int speed){
		this.currentFrame = 0;
		this.frames = frames;
		this.speed = speed;
	}
	
	public void tick(){
		if(tick >= speed){
			tick = 0;
			nextFrame();
		}else{
			tick++;
		}
	}
	
	public void nextFrame(){
		if((currentFrame + 1) > (frames.length - 1)){
			currentFrame = 0;
		}else{
			currentFrame++;
		}
	}
	
	public void render(Graphics2D g, int x, int y){
		g.drawImage(frames[currentFrame], x, y, null);
	}
}
