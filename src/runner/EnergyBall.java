package runner;

import javafx.geometry.Rectangle2D;

public class EnergyBall extends AnimatedThing{

	public EnergyBall(double posx, double posy, double x, double y, double width, double height, String filename, double vx) {
		super(posx, posy, x, y, width, height, filename, vx);
	}
	
	public void update(double time) {
		if(time > 1) time = 0;
		x += vx * time;
		this.getSprite().setX(x);

		this.setHitbox(new Rectangle2D(x, y, width, height));
	}
	
}
