package runner;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;

import java.util.ArrayList;

public class Hero extends AnimatedThing{

	private String attitude = "running"; // running, jumping up, jumping down, running & shoot
	private Boolean accelerate = false;

	private double jumpIndex = 0;
	private double jumpMaximumIndex = 128 * 11;
	private double shootIndex = 0;
	private double shootMaximumIndex = 128 * 8;

	private double offset = 128;

	private double invincibility = 0;
	private double flashing = 0;

	private Boolean end = false;

	public Hero(double posx, double posy, double x, double y, double width, double height, String filename, double vx) {
		super(posx, posy, x, y, width, height, filename, vx);

		frameDuration = 0;
		index = 0;
		maximumIndex = 128 * 8;
		deathIndex = 0;
		deathMaximumIndex = 256 * 10;
	}

	public void update(double time, double xcam, ArrayList<EnergyBall> energyBalls, Group root) {
		if(time > 1) time = 0;

		if(deathAnimation) {
			vx = 0;
			frameDuration += 1;
			if(frameDuration == 10) {
				frameDuration = 0;
				if(deathIndex == deathMaximumIndex) end = true;
				sprite.setViewport(new Rectangle2D(deathIndex, 0, 256,256));
				deathIndex += 256;
			}

			if(attitude.equals("jumping up") || attitude.equals("jumping down")) {
				ay -= 12000 * time;
				vy += ay * time;
				y -= vy * time;
			}
			if(y > 250) {
				y = 250;
				vy = 0;
				ay = 0;
			}

			if(deathIndex == 0) sprite.setViewport(new Rectangle2D(deathIndex, 0, 256,256));
			sprite.setX(x - xcam - 83);
			sprite.setY(y - 50);
		}
		else {
			if(accelerate) ax = 1000 - vx;
			else if(vx > 400) ax = - 0.5 * vx;
			else ax = 0 ;
			vx += ax * time;
			x += vx * time;
			//System.out.println(ax + ", " + vx + ", " + (x-xcam));

			frameDuration += 1;

			if( frameDuration == 4) {
				switch(attitude) {
					case "running" :
						index = (index + offset) % maximumIndex;
						sprite.setViewport(new Rectangle2D(index, 416, 128, 128));
						break;
					case "running & shoot" :
						shootIndex += offset;
						sprite.setViewport(new Rectangle2D(shootIndex, 544, 128, 128));
						break;
					case "jumping up" :
					case "jumping down" :
						if(jumpIndex < jumpMaximumIndex) {
							jumpIndex += offset;
							sprite.setViewport(new Rectangle2D(jumpIndex, 288, 128, 128));
						}
						break;
				}
				frameDuration = 0;
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
				if( attitude.equals("jumping down")) attitude = "running";
			}

			sprite.setY(y);

			if(attitude.equals("running & shoot")) {
				if(shootIndex == shootMaximumIndex - offset) {
					attitude = "running";
					shootIndex = -offset;
				}
				if(frameDuration == 0 && (shootIndex / offset) == 4) {
					EnergyBall energyBall = new EnergyBall(x - xcam + 85, y + 60, 0, 256, 32, 32, "file:img/Mage.png", 200);
					energyBalls.add(energyBall);
					root.getChildren().add(energyBall.getSprite());
				}
			}
			hitbox = new Rectangle2D(x - xcam + 27, y + 55, 35, 50);

			if(invincibility>0) {
				invincibility -= time;
				flashing = (flashing + 1) % 3;
				sprite.setVisible(flashing == 0);
			}
			else sprite.setVisible(true);
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
	public String getAttitude() {
		return attitude;
	}
	public void setAccelerate(Boolean accelerate) {
		this.accelerate = accelerate;
	}
	public Boolean getEnd() {
		return end;
	}
}
