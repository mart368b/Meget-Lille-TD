package game.state;

import java.awt.Graphics2D;

import game.state.states.level1_0;
import game.state.states.menu_state;


public class StateManager {

	private GameState gamestates[];
	private StateTypes currentstate;
	
	public StateManager(){
		gamestates = new GameState[StateTypes.getCount()];
		currentstate = StateTypes.LEVEL1_0; //normally MENU, but changed for testing;
		loadstate(currentstate);
	}
	
	private void loadstate(StateTypes state){
		if(state == StateTypes.MENU){
			gamestates[state.getValue()] = new menu_state(this);
		}else if(state == StateTypes.LEVEL1_0){
			gamestates[state.getValue()] = new level1_0(this);
		}
		//New states will get added here
	}
	
	private void unloadstate(StateTypes state){
		gamestates[state.getValue()] = null;
	}
	
	public void setState(StateTypes state){
		unloadstate(currentstate);
		currentstate = state;
		loadstate(currentstate);
	}
	
	public void tick(){
		if(gamestates[currentstate.getValue()] == null){
			return;
		}
		gamestates[currentstate.getValue()].tick();
	}
	
	public void render(Graphics2D g){

		if(gamestates[currentstate.getValue()] == null){
			return;
		}
		gamestates[currentstate.getValue()].render(g);
	}
	
	public void KeyPressed(int pressed){
		gamestates[currentstate.getValue()].keyPressed(pressed);
	}
	
	public void KeyReleased(int pressed){
		gamestates[currentstate.getValue()].keyReleased(pressed);
	}
}
