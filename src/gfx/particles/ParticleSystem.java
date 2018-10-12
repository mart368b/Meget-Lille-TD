package gfx.particles;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class ParticleSystem {

	private ArrayList<Particle> particles = new ArrayList<Particle>();
	
	public ParticleSystem() {}
	
	public void tick() {
		for (int i = 0; i < particles.size(); i++) {
			Particle p = particles.get(i);
			if (p.isDead()) {
				particles.remove(i);
				i--;
			}else {
				p.tick();
			}
		}
	}
	
	public void play() {}
	
	public void addParticle(Particle p) {
		particles.add(p);
	}
	
	public void render(Graphics2D g2) {
		for (Particle p: particles) {
			p.render(g2);
		}
	}
}
