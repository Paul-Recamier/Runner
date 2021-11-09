package runner;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class AnimatedThing {
	
	protected double x;

	public void setVx(double vx) {
		this.vx = vx;
	}

	protected double vx = 1;
	protected double ax = 0;
	protected double y;
	protected double vy = 0;
	protected double ay = 0;

	protected double width;
	protected double height;
	protected ImageView sprite;
	protected Rectangle2D hitbox;
	
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

	public double getAx() {
		return ax;
	}

	public void setAx(double ax) {
		this.ax = ax;
	}

	public void setAy(double ay) {
		this.ay = ay;
	}

	public double getVx() {
		return vx;
	}

	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}

	public Rectangle2D getHitbox() {
		return hitbox;
	}

	public void setHitbox(Rectangle2D hitbox) {
		this.hitbox = hitbox;
	}

	public void setSprite(ImageView sprite) {
		this.sprite = sprite;
	}


}
