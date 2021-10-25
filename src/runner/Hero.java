package runner;

import javafx.geometry.Rectangle2D;

public class Hero extends AnimatedThing{
	
	private String attitude = "running"; // running, jumping up, jumping down, running & shoot, jumping up & shoot, jumping down & shoot
	
	private int index = 0;
	private int maximumIndex = 85 * 6;
	private double offset = 85;
	private int duration = 0;
	private int durationShoot = 0;

	public Hero(double posx, double posy, double x, double y, double width, double height, String filename, double vx) {
		super(posx, posy, x, y, width, height, filename, vx);
	}
	
	public void update(double time, double xcam) {
		if(time > 1) time = 0;
		
		if(ax > 0 && vx > 200) ax -= 0.02*vx ;
		vx += ax * time;
		x += vx * time;
		
		System.out.println(ax + ", " + vx + ", " + x);
		
		duration += 1;
		
		if( duration == 5) {
			index += offset;
			duration = 0;
			if(attitude.equals("running")) sprite.setViewport(new Rectangle2D(index % maximumIndex, 0, offset, 100));
			if(attitude.equals("jumping up")) sprite.setViewport(new Rectangle2D(0, 160, 85, 100));
			if(attitude.equals("jumping down")) sprite.setViewport(new Rectangle2D(85, 160, 85, 100));
			if(attitude.equals("running & shoot")) sprite.setViewport(new Rectangle2D(index % maximumIndex, 326, offset, 100));
			if(attitude.equals("jumping up & shoot")) sprite.setViewport(new Rectangle2D(0, 490, 85, 100));
			if(attitude.equals("jumping down & shoot")) sprite.setViewport(new Rectangle2D(85, 490, 85, 100));
		}
		sprite.setX(x - xcam);
		
		if(attitude.equals("jumping up") && vy < 0) attitude = "jumping down";
		if(attitude.equals("jumping up & shoot") && vy < 0) attitude = "jumping down & shoot";
		
		
		if(attitude.equals("jumping up") || attitude.equals("jumping down") || attitude.equals("jumping up & shoot") || attitude.equals("jumping down & shoot")) {
			ay += -9.81 * 1200 * time;
			vy += ay * time;
			y -= vy * time;
		}
		if(y > 240) {
			y = 240;
			vy = 0;
			ay = 0;
			if( attitude.equals("jumping down")) attitude = "running"; 
			if( attitude.equals("jumping down & shoot")) attitude = "running & shoot"; 
		}
		
		sprite.setY(y);
		
		if(attitude.equals("running & shoot") || attitude.equals("jumping up & shoot") || attitude.equals("jumping down & shoot")) {
			durationShoot += 1;
			System.out.println(durationShoot + ", " + attitude);
			if(durationShoot == 15) {
				durationShoot = 0;
				if(attitude.equals("running & shoot")) attitude = "running";
				if(attitude.equals("jumping up & shoot")) attitude = "jumping up";
				if(attitude.equals("jumping down & shoot")) attitude = "jumping down";
			}
		}
	}

	public void setAttitude(String attitude) {
		this.attitude = attitude;
	}
	
	public String getAttitude() {
		return attitude;
	}
}
