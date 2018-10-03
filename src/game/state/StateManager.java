package game.state;

import java.awt.Graphics2D;

import game.state.states.level1_0;
import game.state.states.menu_state;


public class StateManager {

	private GameState gamestates[];
	private int currentstate;
	
	public static int totalstates = 2;
	public static int MENU = 0, 
					LEVEL1_0 = 1;
	
	public StateManager(){
		gamestates = new GameState[totalstates];
		currentstate = LEVEL1_0; //normally MENU, but changed for testing;
		loadstate(currentstate);
	}
	
	private void loadstate(int state){
		if(state == MENU){
			gamestates[state] = new menu_state(this);
		}else if(state == LEVEL1_0){
			gamestates[state] = new level1_0(this);
		}
		//New states will get added here
	}
	
	private void unloadstate(int state){
		gamestates[state] = null;
	}
	
	public void setState(int state){
		unloadstate(currentstate);
		currentstate = state;
		loadstate(currentstate);
	}
	
	public void tick(){
		if(gamestates[currentstate] == null){
			return;
		}
		gamestates[currentstate].tick();
	}
	
	public void render(Graphics2D g){

		if(gamestates[currentstate] == null){
			return;
		}
		gamestates[currentstate].render(g);
	}
	
	public void KeyPressed(int pressed){
		gamestates[currentstate].keyPressed(pressed);
	}
	
	public void KeyReleased(int pressed){
		gamestates[currentstate].keyReleased(pressed);
	}
}
