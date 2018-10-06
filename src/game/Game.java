package game;

import java.awt.Graphics2D;
import java.util.ArrayList;

import config.Configuration;
import game.entities.Entity;
import game.entities.enemies.BasicEnemy;
import game.level.Map;
import game.state.StateManager;
import game.state.states.input.InputHandler;
import gfx.HUD;
import gfx.SplashScreenDriver;
import gfx.Window;
import gfx.sprites.SpriteManager;
import gfx.tiles.TileSetManager;

public class Game{
	
	public static Configuration config = new Configuration("config");
	
	public static int tps = config.getInt("graphics.tps");
	
    public static final double ONE_SECOND = 1000000000.;
    private int outputRate = 20;
	private Window window;
	private InputHandler handler = new InputHandler();
	
	private StateManager sm;

	public static void main(String[] args) {
		new Game();
	}
	
	public Game() {
		sm = new StateManager();
		handler.setTarget(sm);
		
		new SplashScreenDriver();
		initDisplay();
		run();
	}
	
	public void initDisplay() {
		window = new Window(config.getInt("graphics.width"),
				config.getInt("graphics.height"));
		
		window.addMouseMotionListener(handler);
		window.addMouseListener(handler);
		
		new HUD().loadImage("HUD");
	}
	
	// main game loop
	public void run()
    {
        long last = System.nanoTime();
        double dt = 0;
        double TICK_LENGTH = tps / ONE_SECOND;
        int ticks = 0;
        int renders = 0;
        long nextDebug = (long) (System.nanoTime() + (ONE_SECOND)*outputRate);
        while (true) {            
            long now = System.nanoTime();
        	long dNs = now - last;
        	last = now;
        	dt += dNs * TICK_LENGTH;
        	int missingTicks = (int) Math.floor(dt);
        	dt -= missingTicks;
        	for (int tick = 0; tick < missingTicks; tick++) { 
        		tick();
        		ticks++;
        	}
        	
        	render();
        	renders++;
        	
        	if(now > nextDebug){
        		System.out.println("tps: " + ticks/outputRate + " fps: " + renders/outputRate);
        		ticks = 0;
        		renders = 0;
        		nextDebug = (long) (now + (ONE_SECOND)*outputRate);
        	}
        	
        	double waitUntil = last + TICK_LENGTH*(1 - dt);
            while(waitUntil > System.nanoTime()){
                ;
            }
        }
    }

	public void tick() {
		// do movement here
		// called once every 60 second
		sm.tick();
	}
	
	public void render() {
			Graphics2D g = (Graphics2D) window.getDrawGraphics();
			g.translate(window.getMenuBarWidth()/2, window.getMenuBarHeight() - window.getMenuBarWidth()/2);
			
			// draw here
			sm.render(g);
			// (0,0) in top left corner
			
			g.dispose();
			window.showStrategy();
			// called as often as possible
	}
}
