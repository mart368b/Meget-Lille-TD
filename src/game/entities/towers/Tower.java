package game.entities.towers;

import java.awt.Color;
import java.awt.Graphics2D;

import game.entities.Entity;
import game.entities.tiles.Tile;
import game.math.Point2D;
import game.state.states.EnemyHandler;
import game.state.states.player.TowerHandler;
import gfx.HUD;
import gfx.sprites.SpriteManager;

public class Tower extends Entity {

	protected int lvl = 0;
	private final int maxlvl = 2;
	
	protected int[] damage;
	protected int[] speed; 
	protected int[] cost;
	protected String[] lore;
	protected int spriteNR, line;
	
	private Point2D initalPos;
	
	private EnemyHandler handler;
	protected boolean marked;
	protected boolean placed = false;
	protected boolean held = false;
	private boolean toExpensive;
	
	public Tower(int[] damage, int[] cost, int[] speed, String[] lore){
		super(0, 0);
		this.speed = speed;
		this.damage = damage;
		this.cost = cost;
		this.lore = lore;
	}
	
	public Tower(int[] damage, int[] cost, int[] speed, String[] lore, double x, double y, int spriteNR, int line){
		super(x, y);
		this.speed = speed;
		this.damage = damage;
		this.cost = cost;
		this.lore = lore;
		this.spriteNR = spriteNR;
		this.line = line;
		initalPos = ((Point2D) this).clone();
	}
	
	public Tower(int[] damage, int[] cost, int[] speed, String[] lore, double x, double y, int spriteNR, int line, boolean placed){
		super(x, y);
		this.speed = speed;
		this.damage = damage;
		this.cost = cost;
		this.lore = lore;
		this.spriteNR = spriteNR;
		this.line = line;
		this.placed = placed;
	}
	
	public Tower clone(double x, double y, TowerHandler th) {
		return new Tower(damage, cost, speed, lore, x, y, spriteNR, line, true);
	}
	
	public void render(Graphics2D g2){
		g2.drawImage(SpriteManager.getSprite(spriteNR).getFrames(line)[lvl],(int) getX(),(int) getY(), null);
		if (marked || !placed || held) {
			String text = getCost() == -1? "MAX": Integer.toString(getCost());
			int yOffset = 0;
			int xOffset = 0;
			if (!placed && held == false) {
				yOffset = Tile.TILESIZE*2 + 35;
				xOffset = 6;
			}
			if (toExpensive) {
				g2.setColor(Color.RED);
			}else {
				g2.setColor(Color.WHITE);
			}
			HUD.drawCenteredText(g2, text, (int) getX() + xOffset, (int) getY() - 6 + yOffset, 2);
		}
	}
	
	public void isToExpensive(int gold) {
		toExpensive = getCost() > gold;
	}
	
	public void lvlUp(){
		lvl++;
	}
	
	public int getLvl(){
		return lvl;
	}

	public int getDamage() {
		return damage[lvl];
	}
	
	public void reset() {
		this.moveTo(initalPos);
	}

	public int getCost() {
		if (placed) {
			if (lvl + 1 > maxlvl) {
				return -1;
			}else {
				return cost[lvl + 1];
			}
		}
		return cost[lvl];
	}
	
	public int getSpeed() {
		return speed[lvl];
	}
	
	public int getWidth() {
		return 2;
	}
	
	public int getHeight() {
		return 2;
	}

	public String[] getLore() {
		return lore;
	}
	
	public int getGold() {
		return 0;
	}
	
	public Point2D getCenter() {
		Point2D p = new Point2D(this);
		p.translate(Tile.TILESIZE, Tile.TILESIZE);
		return p;
	}

	public void setMark(boolean b) {
		marked = b;		
	}
	
	public void tick(EnemyHandler enemyHandler) {}

	public void setHeld(boolean held) {
		this.held = held;
		
	}
}
