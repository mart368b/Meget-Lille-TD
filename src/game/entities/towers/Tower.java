package game.entities.towers;

import java.awt.Graphics2D;

import game.entities.Entity;
import game.state.states.EnemyHandler;
import gfx.HUD;

public class Tower extends Entity {

	protected int lvl = 0;
	private final int maxlvl = 2;
	
	protected int[] damage;
	protected int[] speed; 
	private int[] cost;
	private String[] lore;
	
	private EnemyHandler handler;
	private boolean marked;
	
	public Tower(int[] damage, int[] cost, int[] speed, String[] lore){
		super(0, 0);
		this.speed = speed;
		this.damage = damage;
		this.cost = cost;
		this.lore = lore;
	}
	
	public Tower(int[] damage, int[] cost, int[] speed, String[] lore, double x, double y){
		super(x, y);
		this.speed = speed;
		this.damage = damage;
		this.cost = cost;
		this.lore = lore;
	}
	
	public Tower clone(double x, double y) {
		return new Tower(damage, cost, speed, lore, x, y);
	}
	
	public void render(Graphics2D g2){
		if (marked) {
			HUD.drawCenteredText(g2, Integer.toString(getCost()), (int) getX(), (int) getY() - 6, 1);
		}
	}
	
	public void lvlUp(){
		if((lvl+1) > maxlvl){
			return;
		}
		lvl++;
	}
	
	public int getLvl(){
		return lvl;
	}

	public int getDamage() {
		return damage[lvl];
	}

	public int getCost() {
		return cost[lvl];
	}
	
	public int getSpeed() {
		return speed[lvl];
	}

	public String[] getLore() {
		return lore;
	}

	public void setMark(boolean b) {
		marked = true;		
	}
}
