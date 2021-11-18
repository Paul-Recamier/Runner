package runner;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

import java.util.ArrayList;

public class Hero extends AnimatedThing{

	private String attitude = "running"; // running, jumping up, jumping down, running & shoot
	private String nextAttitude = "running";
	private Boolean accelerate = false;
	
	private double index = 0;
	private double maximumIndex = 128 * 8;
	private double jumpIndex = 0;
	private double maximumJumpIndex = 128 * 11;
	private double offset = 128;
	private int frameDuration = 0;

	private double invincibility = 0;
	private double flashing = 0;

	public Hero(double posx, double posy, double x, double y, double width, double height, String filename, double vx) {
		super(posx, posy, x, y, width, height, filename, vx);
	}
	
	public void update(double time, double xcam, ArrayList<EnergyBall> energyBalls, Group root) {
		if(time > 1) time = 0;

		if(accelerate) ax = 1000 - vx;
		else if(vx > 400) ax = - 0.5 * vx;
		else ax = 0 ;
		vx += ax * time;
		x += vx * time;
		//System.out.println(ax + ", " + vx + ", " + (x-xcam));
		
		frameDuration += 1;

		if( frameDuration == 4) {
			if(attitude.equals("running") || attitude.equals("running & shoot")) index = (index + offset) % maximumIndex;
			if((attitude.equals("jumping up") || attitude.equals("jumping down")) && jumpIndex < maximumJumpIndex) jumpIndex += offset;
			if(index == 0 && (attitude.equals("running") || attitude.equals("running & shoot"))) attitude = nextAttitude;
			frameDuration = 0;
			if (attitude.equals("running")) sprite.setViewport(new Rectangle2D(index, 416, 128, 128));
			if (attitude.equals("jumping up") || attitude.equals("jumping down")) sprite.setViewport(new Rectangle2D(jumpIndex, 288, 128, 128));
			if (attitude.equals("running & shoot")) sprite.setViewport(new Rectangle2D(index, 544, 128, 128));
		}
		sprite.setX(x - xcam);
		
		if(attitude.equals("jumping up") && vy < 0) attitude = "jumping down";

		if(attitude.equals("jumping up") || attitude.equals("jumping down")) {
			ay -= 12000 * time;
			vy += ay * time;
			y -= vy * time;
		}
		if(y > 250) {
			y = 250;
			vy = 0;
			ay = 0;
			sprite.setViewport(new Rectangle2D(128 * 11, 288, 128, 128));
			index = 0;
			jumpIndex = 0;
			if( attitude.equals("jumping down")) attitude = nextAttitude;
		}
		
		sprite.setY(y);
		
		if(attitude.equals("running & shoot")) {
			nextAttitude = "running";
			if(frameDuration == 0 && (index / offset) == 4) {
				EnergyBall energyBall = new EnergyBall(x - xcam + 85, y + 60, 0, 256, 32, 32, System.getProperty("user.dir") + "\\src\\Mage.png", 200);
				energyBalls.add(energyBall);
				root.getChildren().add(energyBall.getSprite());
			}
		}

		this.setHitbox(new Rectangle2D(x - xcam + 27, y + 55, 35, 50));

		if(invincibility>0) {
			invincibility -= time;
			flashing = (flashing + 1) % 3;
			//if(flashing == 0) sprite.setViewport(new Rectangle2D(0,0,1,1));
		}
	}

	public Boolean isInvisible(){
		return invincibility>0;
	}
	public void setInvincibility(double invincibility) {
		this.invincibility = invincibility;
	}
	public void setAttitude(String attitude) {
		this.attitude = attitude;
	}
	public void setNextAttitude(String nextAttitude) {
		this.nextAttitude = nextAttitude;
	}
	public String getAttitude() {
		return attitude;
	}
	public void setAccelerate(Boolean accelerate) {
		this.accelerate = accelerate;
	}
}
