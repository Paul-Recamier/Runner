package runner;

import javafx.geometry.Rectangle2D;

public class EnergyBall extends AnimatedThing{

	private double index = 0;
	private double frameDuration = 0;

	public EnergyBall(double posx, double posy, double x, double y, double width, double height, String filename, double vx) {
		super(posx, posy, x, y, width, height, filename, vx);
	}
	
	public void update(double time) {
		if(time > 1) time = 0;
		if(index < 32 * 6) {
			x += vx * time;
			sprite.setX(x);
		}

		frameDuration += 1;
		if(frameDuration == 6) {
			index+=32;
			sprite.setViewport(new Rectangle2D(index, 256, 32, 32));
			frameDuration = 0;
		}

		this.setHitbox(new Rectangle2D(x, y, width, height));
	}

	public double getIndex() {
		return index;
	}

}
