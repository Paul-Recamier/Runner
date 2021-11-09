package runner;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import javax.xml.stream.events.EntityReference;
import java.util.ArrayList;

public class GameScene extends Scene {

	private Camera camera;
	private StaticThing left;
	private StaticThing right;
	private Hero hero;
	private Foe cactus;
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


	public Foe getCactus() {
		return cactus;
	}
	
	public GameScene(Group root, int height, int width) {
		super(root, height, width);
		
		hero = new Hero(0, 240, 0, 0, 85, 100, cd + "\\src\\heros.png", 200);
		//energyBall = new EnergyBall(800, 0, 536, 366, 29, 29, cd + "\\src\\heros.png", 400);
		cactus = new Foe(800, 290, 0, 0, 240, 305, cd + "\\src\\AnimationCactus.png", 400);
		camera = new Camera(0, 0);
        left = new StaticThing(0, 0, 500, 0, 300, 400, cd + "\\src\\desert.png");
        right = new StaticThing(300, 0, 0, 0, 800, 400, cd + "\\src\\desert.png");
        
        root.getChildren().add(left.getSprite());
        root.getChildren().add(right.getSprite());
        root.getChildren().add(hero.getSprite());
        root.getChildren().add(cactus.getSprite());

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

		if (!hero.isInvicible() && hero.getHitbox().intersects(cactus.getHitbox())) {
			System.out.println("Collision");
			hero.setInvincibility(2.5);
			numberOfLives -= 1;
			displaylife(root);
		}

		for(EnergyBall energyBall : energyBalls) {
			if (energyBall.getHitbox().intersects(cactus.getHitbox())) {
				System.out.println("Touched");
				cactus.setAlive(false);
				cactus.setHitbox(null);
				energyBall.getSprite().setViewport(new Rectangle2D(0,0,1,1));
			}
		}

	}

}
