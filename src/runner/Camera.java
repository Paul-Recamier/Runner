package runner;

public class Camera {
	
	private double x;
	private double y;
	private double vx = 0;
	private double ax;
	
	private double f = 3;
	private double k = 10;

	public	Camera(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	public double getVx() {
		return vx;
	}
	
	@Override
	public String toString() {
		return "x=" + x + ", y=" + y +  ", vx=" + vx + ", ax=" + ax;
	}
	
	public void update(double time, double xhero, Boolean deathAnimation) {
		if(time > 1) time = 0;

		if(deathAnimation) {
			ax = k * (-200 + xhero - x) - f * vx;
			vx += ax * time;
			x += vx * time;
		}
		else {
			ax = k * (xhero - x) - f * vx;
			vx += ax * time;
			x += vx * time;
			//System.out.println(this + ", " + (xhero - x));
		}
	}
}
