package game.state;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import game.state.states.menu_state;
import game.state.states.input.BasicMouseInput;
import game.state.states.player.levelPlayer;


public class StateManager implements BasicMouseInput {

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
			gamestates[state.getValue()] = new levelPlayer(this);
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

	@Override
	public void mousePressed(MouseEvent e) {
		gamestates[currentstate.getValue()].mousePressed(e);;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		gamestates[currentstate.getValue()].mouseMoved(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		gamestates[currentstate.getValue()].mouseExited(e);;
	}
}
