package runner;

import javafx.geometry.Rectangle2D;

import java.util.ArrayList;

public class Foe extends AnimatedThing{

	private Boolean toRemove = false;

	public Foe(double posx, double posy, double x, double y, double width, double height, String filename, double vx) {
		super(posx, posy, x, y, width, height, filename, vx);

		frameDuration = 0;
		index = 0;
		maximumIndex = 128 * 6;
		deathIndex = 0;
		deathMaximumIndex = 256 * 10;
	}

	
	public void update(double time, double vxCam, double xhero) {
		x -= vxCam * time;
		if(deathAnimation){
			frameDuration += 1;
			hitbox = null;
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
				if((x - xhero) < 100 && (x - xhero) > -100) sprite.setViewport(new Rectangle2D(2432 - index, 0, 128, 128));
				else sprite.setViewport(new Rectangle2D(2432 - index, 384, 128, 128));
			}

			hitbox = new Rectangle2D(x + 72,y + 62,43,50);

			if(x < -100) toRemove = true;
		}
	}

	@Override
	public Boolean getToRemove() {
		return toRemove;
	}

}
