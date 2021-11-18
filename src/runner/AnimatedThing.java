package runner;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class AnimatedThing {
	
	protected double x;
	protected double vx = 1;
	protected double ax = 0;
	protected double y;
	protected double vy = 0;
	protected double ay = 0;

	protected double width;
	protected double height;
	protected ImageView sprite;
	protected Rectangle2D hitbox;

	protected Boolean deathAnimation = false;

	protected double frameDuration;
	protected double index;
	protected double maximumIndex;
	protected double deathIndex;
	protected double deathMaximumIndex;
	
	public AnimatedThing(double posx, double posy, double x, double y, double width, double height, String filename, double vx){
		this.x = posx;
		this.y = posy;
		this.vx = vx;
		this.width = width;
		this.height = height;
		hitbox = new Rectangle2D(posx, posy, width, height);
		
		Image spriteSheet = new Image(filename);
        sprite = new ImageView(spriteSheet);
        sprite.setViewport(new Rectangle2D(x,y,width,height));
        sprite.setX(posx);
        sprite.setY(posy);
	}

	public ImageView getSprite() {
		return sprite;
	}

	public void setAy(double ay) {
		this.ay = ay;
	}

	public double getX() {
		return x;
	}

	public Rectangle2D getHitbox() {
		return hitbox;
	}

	public void setHitbox(Rectangle2D hitbox) {
		this.hitbox = hitbox;
	}

	public void setDeathAnimation(Boolean deathAnimation) {
		this.deathAnimation = deathAnimation;
	}

	public Boolean getDeathAnimation() {
		return deathAnimation;
	}

}
