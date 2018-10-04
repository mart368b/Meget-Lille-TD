package game.entities;

import java.awt.Graphics2D;

public class Tower {

	private int lvl = 0;
	private final int maxlvl = 2;
	
	private int[] damage;
	private int[] speed; 
	private int[] cost;
	private String[] lore;
	
	private int x, y;
	
	public Tower(int[] damage, int[] cost, int[] speed, String[] lore, int x, int y){
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.damage = damage;
		this.cost = cost;
		this.lore = lore;
	}
	
	public void render(Graphics2D g){}
	
	public void lvlUp(){
		if((lvl+1) > maxlvl){
			return;
		}
		lvl++;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getLvl(){
		return lvl;
	}

	public int[] getDamage() {
		return damage;
	}

	public int[] getCost() {
		return cost;
	}
	
	public int[] getSpeed() {
		return speed;
	}

	public String[] getLore() {
		return lore;
	}
}
