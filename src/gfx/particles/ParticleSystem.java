package gfx.particles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class ParticleSystem {
	
	public static double ANGLE_TO_RAD = Math.PI/180;
	
	protected int t = 0, spawnDuration = 0;
	protected ArrayList<Particle> particles = new ArrayList<Particle>();
	protected double y0, x0, missingParticles;
	protected ParticleSystemSettings settings;
	protected boolean debug;
	
	public ParticleSystem(double x0, double y0, ParticleSystemSettings settings) {
		this.x0 = x0;
		this.y0 = y0;
		this.settings = settings;
	}
	
	public ParticleSystem(double x0, double y0, ParticleSystemSettings settings, boolean debug) {
		this.x0 = x0;
		this.y0 = y0;
		this.settings = settings;
		this.debug = true;
	}
	
	public void tick() {
		
		if (settings.loop) {
			spawnBatch();
		}else {
			if (spawnDuration-- > 0) {
				spawnBatch();
			}else {
				if (debug && t-- < -10) {
					t = 100;
					play();
				}
			}
		}
		
		for (int i = 0; i < particles.size(); i++) {
			Particle p = particles.get(i);
			if (p.age(settings.particleMaxLifeTimer)) {
				particles.remove(i);
				i--;
			}
		}
	}
	
	public double getXVelocity(double deviation) {
		return Math.cos(deviation - settings.deviation/2 - settings.startAngle) * settings.velocity;
	}
	
	public double getYVelocity(double deviation) {
		return Math.sin(deviation - settings.deviation/2 - settings.startAngle) * settings.velocity;
	}
	
	public void spawnBatch() {
		missingParticles += settings.ppt;
		int newParticleCount = (int) Math.floor(missingParticles);
		for (int i = 0; i < newParticleCount; i++) {
			particles.add(getNewParticle());
		}
		missingParticles -= newParticleCount;
	}
	
	public Particle getNewParticle() {
		int c = settings.rand.nextInt(settings.palet.colors.length);
		double angleDeviation = settings.rand.nextDouble()*settings.deviation;
		return new Particle(getXVelocity(angleDeviation), getYVelocity(angleDeviation), settings.rand.nextInt(settings.lifeDeviation*2) - settings.lifeDeviation, c);
	}
	
	public void play() {
		spawnDuration = settings.spawnDuration;
		missingParticles = 0;
	}
	
	public void render(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		for (Particle p: particles) {
			p.render(g2, x0, y0, settings);
		}
	}
}
