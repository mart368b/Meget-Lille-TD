package game;

import java.awt.Graphics2D;
import java.util.ArrayList;

import config.Configuration;
import game.entities.Entity;
import game.entities.enemies.Classic;
import game.level.Map;
import gfx.Window;
import gfx.sprites.SpriteManager;

public class Game{
	
	public static Configuration config = new Configuration("config");
	
	public int tps = config.getInt("graphics.tps");
	
    public final int ONE_SECOND = 1000000000;
    private int outputRate = 20;
	private Window window;
	
	private ArrayList<Entity> entities = new ArrayList<Entity>();

	public static void main(String[] args) {
		new Game();
	}
	
	public Game() {
		initDisplay();
		loadResources();
		initMap();		
		run();
	}
	
	public void initDisplay() {
		window = new Window(config.getInt("graphics.width"),
				config.getInt("graphics.height"));
	}
	
	public void loadResources() {
		
		Map m = new Map("testmap2");
		
		new SpriteManager();
	}
	
	// add initiate map
	public void initMap() {
		entities.add(new Classic());
	}
	
	// main game loop
	public void run()
    {
        long lt = System.nanoTime();
        long t;
        double dt;
        int ticks = 0;
        int renders = 0;
        int timer = 0;
        int middleTimer = 0;
        while (true) {
            t = System.nanoTime();
            dt = (t - lt);
            for (int i = 1; i <= Math.floor(dt/(ONE_SECOND/tps)); i++ ) {
                tick(dt/1000000);
                ticks++;
            }
            lt = t;

            timer += dt;
            
            if (timer >= ONE_SECOND) {
            	middleTimer++;
            	if (middleTimer >= outputRate) {
            		System.out.println("tps: " + Integer.toString(ticks/outputRate) + " fps: " + Integer.toString(renders/outputRate));
                    ticks = 0;
                    renders = 0;
                    middleTimer = 0;
            	}
            	timer = timer - ONE_SECOND;
	        }
            
            render();
            renders++;
            
            long waitUntil = lt + ONE_SECOND/tps;
            while(waitUntil > System.nanoTime()){
                ;
            }
        }
    }

	public void tick(double dt) {
		// do movement here
		// called once every 60 second
		for(Entity ent : entities){
			ent.tick();
		}
	}
	
	public void render() {
			Graphics2D g = (Graphics2D) window.getDrawGraphics();
			g.translate(window.getMenuBarWidth()/2, window.getMenuBarHeight() - window.getMenuBarWidth()/2);
			
			// draw here
			for(Entity ent : entities){
				ent.render(g);
			}
			// (0,0) in top left corner
			
			g.dispose();
			window.showStrategy();
		
		
		// called as often as possible
		
	}
}
