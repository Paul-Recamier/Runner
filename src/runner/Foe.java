package runner;

import javafx.geometry.Rectangle2D;

public class Foe extends AnimatedThing{

	private Boolean alive = true;
	private double index = 0;
	private double duration = 0;

	public Foe(double posx, double posy, double x, double y, double width, double height, String filename, double vx) {
		super(posx, posy, x, y, width, height, filename, vx);
		this.getSprite().setPreserveRatio(true);
		this.getSprite().setFitHeight(60);
	}

	
	public void update(double time, double vxCam) {
		if(x > -100) {
			x -= vxCam * time;
			this.getSprite().setX(x);
		}

		if(alive) this.setHitbox(new Rectangle2D(x, y, 60 * width / height, 60));
		else {
			duration += 1;
			if(duration == 5) {
				duration = 0;
				if(index < 5) index += 1;
				this.getSprite().setViewport(new Rectangle2D(240*index,0,240,305));
			}

		}
	}

	public void setAlive(Boolean alive) {
		this.alive = alive;
	}
}
