package game.entities.projectile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import game.entities.enemies.Enemy;
import game.math.Point2D;

public class BubbleProjectile extends FlyingProjectile{

	public BubbleProjectile(Point2D p0, Enemy target, double speed, double maxRange, int damage) {
		super(p0, target, 1, maxRange, damage);
	}
	
	@Override
	public void render(Graphics2D g2) {
		g2.setColor(Color.WHITE);
		g2.draw(new Ellipse2D.Double(getX() - 10, getY() - 10, 20, 20));
	}

}
