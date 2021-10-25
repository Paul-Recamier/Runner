package runner;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StaticThing {
	
	private double x;
	private double y;
	private ImageView sprite;
	
	public StaticThing(double posx, double posy, double x, double y, double width, double height, String filename){
		
		Image spriteSheet = new Image(filename);
        sprite = new ImageView(spriteSheet);
        sprite.setViewport(new Rectangle2D(x,y,width,height));
        sprite.setX(posx);
        sprite.setY(posy);
	}

	public ImageView getSprite() {
		return sprite;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}
	
}
