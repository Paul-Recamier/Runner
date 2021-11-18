package runner;

import javafx.geometry.Rectangle2D;

import java.util.ArrayList;

public class Foe extends AnimatedThing{

	private double index = 0;
	private double frameDuration = 0;
	private double maximumIndex = 128 * 6;
	private Boolean deathAnimation = false;
	private double deathIndex = 0;
	private double deathMaximumIndex = 256 * 10;
	private Boolean toRemove = false;

	public Foe(double posx, double posy, double x, double y, double width, double height, String filename, double vx) {
		super(posx, posy, x, y, width, height, filename, vx);
	}

	
	public void update(double time, double vxCam, double xhero) {
		x -= vxCam * time;
		if(deathAnimation){
			frameDuration += 1;
			if(frameDuration == 5){
				frameDuration = 0;
				if(deathIndex == deathMaximumIndex) toRemove = true;
				deathIndex += 256;
				sprite.setViewport(new Rectangle2D(2560 - deathIndex, 128, 256,256));
			}
			sprite.setX(x - 46);
			sprite.setY(y - 45);
		}
		else{
			sprite.setX(x);
			frameDuration += 1;
			if(frameDuration == 5) {
				index = (index + 128) % maximumIndex;
				frameDuration = 0;
				System.out.println(x - xhero);
				if((x - xhero) < 100 && (x - xhero) > -100) sprite.setViewport(new Rectangle2D(2432 - index, 0, 128, 128));
				else sprite.setViewport(new Rectangle2D(2432 - index, 384, 128, 128));
			}

			hitbox = new Rectangle2D(x + 72,y + 62,43,50);

			System.out.println(x);
			if(x < -100) toRemove = true;
		}
	}

	public void setDeathAnimation(Boolean deathAnimation) {
		this.deathAnimation = deathAnimation;
	}

	public Boolean getToRemove() {
		return toRemove;
	}

}
