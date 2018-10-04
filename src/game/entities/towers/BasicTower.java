package game.entities.towers;

import java.awt.Graphics2D;

import gfx.sprites.SpriteManager;

public class BasicTower extends Tower {
	
	private final int spriteNR = 20;
	private final int line = 0;

	public BasicTower(int[] damage, int[] cost, int[] speed, String[] lore, int x, int y) {
		super(damage, cost, speed, lore, x, y);
	}
	
	public void tick(){}
	
	public void render(Graphics2D g2){
		super.render(g2);
		g2.drawImage(SpriteManager.getSprite(spriteNR).getFrames(line)[getLvl()],(int) getX(),(int) getY(), null);
	}
}
