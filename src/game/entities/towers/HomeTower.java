package game.entities.towers;

import java.awt.Graphics2D;

import game.state.states.EnemyHandler;

public class HomeTower extends Tower {

	public HomeTower(int[] damage, int[] cost, int[] speed, String[] lore, int x, int y) {
		super(damage, cost, speed, lore, x, y, 21, 0);
	}
	
	public HomeTower(int[] damage, int[] cost, int[] speed, String[] lore, int x, int y, boolean placed) {
		super(damage, cost, speed, lore, x, y, 21, 0, true);
	}
	
	public HomeTower clone(double x, double y) {
		return new HomeTower(damage, cost, speed, lore,(int) x,(int) y, true);
	}
	
	@Override
	public void tick(EnemyHandler enemyHandler){
		
	}
	
	public void render(Graphics2D g2){
		super.render(g2);
	}
}
