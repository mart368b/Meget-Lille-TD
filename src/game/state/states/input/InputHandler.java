package game.state.states.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements MouseListener, MouseMotionListener{
	
	private BasicMouseInput target;
	
	public InputHandler() {	}
	
	public void setTarget(BasicMouseInput tagret) {
		this.target = tagret;
	}
	
	public void removeTarget() {
		target = null;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (target != null) {
			target.mousePressed(e);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (target != null) {
			target.mouseMoved(e);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}


}
