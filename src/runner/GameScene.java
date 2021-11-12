package runner;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;

public class GameScene extends Scene {

	private Camera camera;
	private StaticThing left;
	private StaticThing right;
	private Hero hero;
	private Foe cactus;
	private ArrayList<Foe> foes = new ArrayList();
	private ArrayList<EnergyBall>  energyBalls = new ArrayList();
	private String cd = System.getProperty("user.dir");
	private Integer numberOfLives = 3;
	private double x = 500;
	
	public Hero getHero() {
		return hero;
	}
	
	public Camera getCam() {
		return camera;
	}

	public ArrayList<EnergyBall> getEnergyBalls() {
		return energyBalls;
	}

	public ArrayList<Foe> getFoes() {
		return foes;
	}
	
	public GameScene(Group root, int height, int width) {
		super(root, height, width);
		
		hero = new Hero(0, 240, 0, 0, 85, 100, cd + "\\src\\heros.png", 200);
		//energyBall = new EnergyBall(800, 0, 536, 366, 29, 29, cd + "\\src\\heros.png", 400);
		//cactus = new Foe(800, 290, 0, 0, 240, 305, cd + "\\src\\AnimationCactus.png", 400);
		for(int i=0; i < 100; i++) {
			Foe foe = new Foe(Math.random()*40000, 290, 0, 0, 240, 305, cd + "\\src\\AnimationCactus.png", 400);
			foes.add(foe);
		}
		camera = new Camera(0, 0);
        left = new StaticThing(0, 0, 500, 0, 300, 400, cd + "\\src\\desert.png");
        right = new StaticThing(300, 0, 0, 0, 800, 400, cd + "\\src\\desert.png");
        
        root.getChildren().add(left.getSprite());
        root.getChildren().add(right.getSprite());
        root.getChildren().add(hero.getSprite());
        for(Foe foe : foes) root.getChildren().add(foe.getSprite());

		displaylife(root);

		this.setOnKeyPressed( event -> {
			if(event.getCode().equals(KeyCode.SPACE) && (hero.getAttitude().equals("running") || hero.getAttitude().equals("running & shoot"))) {
				System.out.println("Jump");
				if(hero.getAttitude().equals("running")) hero.setAttitude("jumping up");
				if(hero.getAttitude().equals("running & shoot")) hero.setAttitude("jumping up & shoot");
				hero.setAy(3000);
			}
			if(event.getCode().equals(KeyCode.D)) {
				hero.setAccelerate(true);
			}
		} );

		this.setOnKeyReleased( event -> {
			if(event.getCode().equals(KeyCode.D)) {
				hero.setAccelerate(false);
			}
		});
		
		this.setOnMouseClicked(event -> {
			System.out.println("Shoot");
			if(hero.getAttitude().equals("running")) hero.setAttitude("running & shoot");
			if(hero.getAttitude().equals("jumping up")) hero.setAttitude("jumping up & shoot");
			if(hero.getAttitude().equals("jumping down")) hero.setAttitude("jumping down & shoot");
			EnergyBall energyBall = new EnergyBall(800, 0, 536, 366, 29, 29, cd + "\\src\\heros.png", 400);
			energyBalls.add(energyBall);
			root.getChildren().add(energyBall.getSprite());
			energyBall.getSprite().setX(hero.getX() - camera.getX() + 85);
			energyBall.getSprite().setY(hero.getY() + 38);
			energyBall.setX(hero.getX() - camera.getX() + 85);
			energyBall.setY(hero.getY() + 38);
		});
	}

	public void displaylife(Group root){
		for(int i = 0; i<numberOfLives; i++) {
			StaticThing heart = new StaticThing(16 + i*40, 12, 0, 0, 32, 32, cd + "\\src\\hearth.png");
			root.getChildren().add(heart.getSprite());
		}
		for(int j = 2; j>=numberOfLives; j--) {
			StaticThing emptyheart = new StaticThing(16 + j*40, 12, 0, 0, 32, 32, cd + "\\src\\emptyhearth.png");
			root.getChildren().add(emptyheart.getSprite());
		}
	}

	public void update(double time, Group root) {
		if(time > 1) time = 0;
		x = (x + camera.getVx() * time) % 800;
		left.getSprite().setViewport(new Rectangle2D(x, 0, 800 - x, 400));
		right.getSprite().setX(800 - x);

		for(Foe foe : foes) {
			if (!hero.isInvicible() && hero.getHitbox().intersects(foe.getHitbox())) {
				System.out.println("Collision");
				hero.setInvincibility(2.5);
				numberOfLives -= 1;
				displaylife(root);
			}
		}

		ArrayList<Integer> ballsToRemove = new ArrayList<>();
		for(EnergyBall energyBall : energyBalls) {
			for(Foe foe : foes) {
				if (energyBall.getHitbox().intersects(foe.getHitbox())) {
					System.out.println("Touched");
					foe.setAlive(false);
					foe.setHitbox(null);
					ballsToRemove.add(energyBalls.indexOf(energyBall));
					energyBall.getSprite().setViewport(new Rectangle2D(0, 0, 1, 1));
				}
			}
			if (energyBall.getX() > 900) ballsToRemove.add(energyBalls.indexOf(energyBall));
		}
		for(Integer i : ballsToRemove) {
			energyBalls.get(i).getSprite().setViewport(new Rectangle2D(0,0,1,1));
			energyBalls.remove((int) i);
		}
	}
}
