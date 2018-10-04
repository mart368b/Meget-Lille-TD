package gfx.sprites;

import java.awt.Color;

public class ColorRamp {
	
	private Color[] colors;
	
	public ColorRamp(Color[] c) {
		colors = c;
	}
	
	public Color getColor(double t) {
		int i = (int) Math.floor(t);
		if (i < 0) {
			return colors[0];
		}
		if (i >= colors.length - 1) {
			return colors[colors.length - 1];
		}
		Color c2 = colors[i];
		Color c1 = colors[i + 1];
		
		double progress = t - i;
		int r = (int) Math.floor(c1.getRed() * progress + c2.getRed() * (1 - progress));
		int g = (int) Math.floor(c1.getGreen() * progress + c2.getGreen() * (1 - progress));
		int b = (int) Math.floor(c1.getBlue() * progress + c2.getBlue() * (1 - progress));
		return new Color(r, g, b);
	}
	
	public int size() {
		return colors.length;
	}

}
