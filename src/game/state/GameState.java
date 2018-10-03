package game.state;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import game.state.states.input.BasicMouseInput;

public abstract class GameState implements BasicMouseInput {
	
	protected StateManager sm;
	
	public abstract void init();
	public abstract void tick();
	public abstract void render(Graphics2D g);
	
}
