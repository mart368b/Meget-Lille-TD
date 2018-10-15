package gfx.particles;

import java.awt.Color;

public enum ParticleSystemLibary {
	
	SHOT(new ParticleSystemSettings(
			/*partiticleMaxLifeTime =*/ 20, 
			/*life deviation =*/2, 
			/*spawnDuration =*/1, 
			/*particleCount =*/7, 
			/*initialParticleSize*/ 5,
			/*endParticleSize*/ 3, 
			/*startAngle =*/90, 
			/*angle deviation =*/30, 
			/*velocity =*/3, 
			/*gravity =*/0,
			/*color palet*/ new ColorPalet(new Color[] {
					new Color(174, 176, 180, 125),
					new Color(120, 120, 145, 200)
					})
			)),
	
	FIRE(new ParticleSystemSettings(
			/*partiticleMaxLifeTime =*/ 30, 
			/*life deviation =*/4, 
			/*ppt =*/0.25, 
			/*initialParticleSize*/ 8,
			/*endParticleSize*/ 2,
			/*startAngle =*/90, 
			/*angle deviation =*/360, 
			/*velocity =*/0.3, 
			/*gravity =*/-0.02,
			/*color palet*/ new ColorPalet(new Color[] {
					new Color(255, 119, 0, 125),
					new Color(255, 172, 104, 100),
					new Color(255, 90, 40, 175)
					})
			));
	
	private final ParticleSystemSettings setting;
	
	ParticleSystemLibary(final ParticleSystemSettings setting) {
		this.setting = setting;
    }
	
	public ParticleSystemSettings getValue() {
		return setting; 
	}

}
