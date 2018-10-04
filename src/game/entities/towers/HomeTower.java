package game.entities.towers;

import java.awt.Graphics2D;

import game.entities.Tower;
import gfx.sprites.SpriteManager;

public class HomeTower extends Tower {
	
	private final int spriteNR = 21;
	private final int line = 0;

	public HomeTower(int[] damage, int[] cost, int[] speed, String[] lore, int x, int y) {
		super(damage, cost, speed, lore, x, y);
	}
	
	public void tick(){}
	
	public void render(Graphics2D g){
		g.drawImage(SpriteManager.getSprite(spriteNR).getFrames(line)[getLvl()], getX(), getY(), null);
	}
}
