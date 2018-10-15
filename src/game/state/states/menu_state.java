package game.state.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import game.state.GameState;
import game.state.StateManager;
import game.state.StateTypes;
import game.state.states.player.interactibles.Button;
import gfx.HUD;
import gfx.particles.ParticleSystem;
import gfx.particles.ParticleSystemLibary;
import gfx.particles.ParticleSystemSettings;

public class menu_state extends GameState {
	
	private ParticleSystem[] particleSystems = new ParticleSystem[] {
			new ParticleSystem(200, 200, ParticleSystemLibary.SHOT.getValue(), true),
			new ParticleSystem(250, 200, ParticleSystemLibary.FIRE.getValue(), true)
	};
	
	private StateTypes[] levels = new StateTypes[] {
		StateTypes.LEVEL1_0
	};
	
	private ArrayList<Button> buttons = new ArrayList<Button>();
	
	public menu_state(StateManager sm) {
		this.sm = sm;
		for (int i = 0; i < levels.length; i++) {
			buttons.add(new Button((i % 3) * 100 + 100, (i / 3) * 100 + 400, Integer.toString(i)));
		}
	}
	
	@Override
	public void init() {
		
	}

	@Override
	public void tick() {
		for (int i = 0; i < buttons.size(); i++) {
			Button b = buttons.get(i);
			if(b.hasBeenPressed()) {
				sm.setState(levels[i]);
			}
		}
		
		for (ParticleSystem p: particleSystems) {
			p.tick();
		}
	}

	@Override
	public void render(Graphics2D g2) {
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, 1280, 960);
		for(Button b: buttons) {
			b.render(g2);
		}
		g2.setColor(Color.BLACK);
		g2.drawString("Paticle test", 150, 110);
		g2.drawRect(150, 125, 200, 100);
		for (ParticleSystem p: particleSystems) {
			p.render(g2);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for(Button b: buttons) {
			b.mousePressed(e);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}
