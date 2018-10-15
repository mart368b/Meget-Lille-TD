package game.entities.towers;

import java.awt.Graphics2D;

import game.entities.projectile.BubbleProjectile;
import game.state.states.player.TowerHandler;

public class BubbleTower extends BasicTower{

	public BubbleTower(int[] damage, int[] cost, int[] speed, String[] lore, int[] range, int x, int y) {
		super(damage, cost, speed, lore, range, x, y);
		this.range = range;
		spriteNR = 22; 
		line = 0;
	}
	
	public BubbleTower(int[] damage, int[] cost, int[] speed, String[] lore, int[] range, int x, int y, boolean placed, TowerHandler th) {
		super(damage, cost, speed, lore, range, x, y, placed, th);
		spriteNR = 22; 
		line = 0;
	}
	
	public BubbleTower clone(double x, double y, TowerHandler th) {
		return new BubbleTower(damage, cost, speed, lore, range,(int) x,(int) y, true, th);
	}
	
	@Override
	public boolean hasParticleSystem () {
		return false;
	}
	
	@Override
	protected void shootProjectile() {
		th.addProjectile(new BubbleProjectile(getCenter(), target, 6, getRange(), getDamage()));		
	}
	
	@Override
	public void render(Graphics2D g2) {
		super.render(g2);
	}

}
