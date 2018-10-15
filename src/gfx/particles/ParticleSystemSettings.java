package gfx.particles;

import java.util.Random;

public class ParticleSystemSettings {
	
	public static double ANGLE_TO_RAD = Math.PI/180;
	
	public int particleMaxLifeTimer, spawnDuration, lifeDeviation;
	public boolean loop = false;
	//ppt = particle per tick
	public double initialParticleSize, particleVariationSize, startAngle, deviation, velocity, gravity, ppt;
	public ColorPalet palet;
	public Random rand = new Random();
	
	public ParticleSystemSettings(int partiticleMaxLifeTime, int lifeDeviation, double ppt, double initialParticleSize, double endParticleSize, double startAngle, double deviation, double velocity, double gravity, ColorPalet palet) {
		this.particleMaxLifeTimer = partiticleMaxLifeTime;
		this.lifeDeviation = lifeDeviation;
		this.ppt = ppt;
		loop = true;
		this.initialParticleSize = endParticleSize;
		this.particleVariationSize = initialParticleSize - endParticleSize;
		this.velocity = velocity;
		this.startAngle = startAngle * ANGLE_TO_RAD;
		this.deviation = deviation * ANGLE_TO_RAD;
		this.gravity = gravity;
		this.palet = palet;
	}
	
	public ParticleSystemSettings(int partiticleMaxLifeTime, int lifeDeviation, int spawnDuration, int particleCount, double initialParticleSize, double endParticleSize, double startAngle, double deviation, double velocity, double gravity, ColorPalet palet) {
		this.particleMaxLifeTimer = partiticleMaxLifeTime;
		this.lifeDeviation = lifeDeviation;
		this.spawnDuration = spawnDuration;
		this.ppt = (particleCount + 0.)/spawnDuration;
		this.initialParticleSize = endParticleSize;
		this.particleVariationSize = initialParticleSize - endParticleSize;
		this.velocity = velocity;
		this.startAngle = startAngle * ANGLE_TO_RAD;
		this.deviation = deviation * ANGLE_TO_RAD;
		this.gravity = gravity;
		this.palet = palet;
	}
}
