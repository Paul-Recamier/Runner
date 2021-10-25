package runner;

public class Foe extends AnimatedThing{

	public Foe(double posx, double posy, double x, double y, double width, double height, String filename, double vx) {
		super(posx, posy, x, y, width, height, filename, vx);
	}
	
	
	public void update(double time, double vxCam) {
		x -= vxCam * time;
		this.getSprite().setX(x);
	}
}
