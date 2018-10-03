package game.state;

import java.awt.Graphics2D;

public abstract class GameState {
	
	protected StateManager sm;
	
	public abstract void init();
	public abstract void tick();
	public abstract void render(Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
	
}
