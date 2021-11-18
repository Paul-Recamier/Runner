package runner;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Arrays;

public class GameScene extends Scene {

	private Camera camera;
	private StaticThing skyLeft, skyRight, bgDecorLeft, bgDecorRight, mdLeft, mdRight, fgLeft, fgRight, groundLeft, groundRight;
	private ArrayList<StaticThing> background = new ArrayList();
	private Hero hero;
	private ArrayList<Foe> foes = new ArrayList();
	private ArrayList<EnergyBall>  energyBalls = new ArrayList();
	private ArrayList<StaticThing> hearts = new ArrayList();
	private String cd = System.getProperty("user.dir");
	private Integer numberOfLives = 3;
	private ArrayList<Integer> x = new ArrayList(Arrays.asList(500, 500, 500, 500));
	private Boolean end = false;
	
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
	public Boolean getEnd() {
		return end;
	}
	
	public GameScene(Group root, int width, int height) {
		super(root, width, height);

		hero = new Hero(0, 250, 0, 0, 128, 128, cd + "\\img\\Mage.png", 400);
		for(int i=0; i < 1; i++) {
			Foe foe = new Foe(Math.random()*0 + 1000, 250, 2432, 384, 128, 128, cd + "\\img\\Foe.png", 400);
			foes.add(foe);
		}
		camera = new Camera(0, 0);

		skyLeft = new StaticThing(0, 0, 500, 0, 211, 400, cd + "\\img\\Sky.png"); background.add(skyLeft);
		skyRight = new StaticThing(0, 0, 0, 0, 711, 400, cd + "\\img\\Sky.png"); background.add(skyRight);
		bgDecorLeft = new StaticThing(0, 0, 500, 0, 211, 400, cd + "\\img\\BG_Decor.png"); background.add(bgDecorLeft);
		bgDecorRight = new StaticThing(0, 0, 0, 0, 711, 400, cd + "\\img\\BG_Decor.png"); background.add(bgDecorRight);
		mdLeft = new StaticThing(0, 0, 500, 0, 211, 400, cd + "\\img\\Middle_Decor.png"); background.add(mdLeft);
		mdRight = new StaticThing(0, 0, 0, 0, 711, 400, cd + "\\img\\Middle_Decor.png"); background.add(mdRight);
		fgLeft = new StaticThing(0, 0, 500, 0, 211, 400, cd + "\\img\\Foreground.png"); background.add(fgLeft);
		fgRight = new StaticThing(0, 0, 0, 0, 711, 400, cd + "\\img\\Foreground.png"); background.add(fgRight);
		groundLeft = new StaticThing(0, 0, 500, 0, 211, 400, cd + "\\img\\Ground.png");
		groundRight = new StaticThing(0, 0, 0, 0, 711, 400, cd + "\\img\\Ground.png");


		for(int i = 0; i<numberOfLives; i++) {
			StaticThing heart = new StaticThing(16 + i*40, 12, 0, 0, 32, 32, cd + "\\src\\hearth.png");
			hearts.add(heart);
		}

		for(StaticThing bg : background) root.getChildren().add(bg.getSprite());
        root.getChildren().add(hero.getSprite());
        for(Foe foe : foes) root.getChildren().add(foe.getSprite());
		for(StaticThing heart : hearts) root.getChildren().add(heart.getSprite());
		root.getChildren().add(groundLeft.getSprite());
		root.getChildren().add(groundRight.getSprite());

		this.setOnKeyPressed( event -> {
			if(event.getCode().equals(KeyCode.SPACE) && (hero.getAttitude().equals("running") || hero.getAttitude().equals("running & shoot"))) {
				System.out.println("Jump");
				hero.setAttitude("jumping up");
				hero.setNextAttitude("running");
				hero.setAy(3600);
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
			hero.setNextAttitude("running & shoot");
		});
	}

	public void update(double time) {
		if(time > 1) time = 0;
		for(int i = 0; i < 4; i++) x.set(i, (int) (x.get(i) + camera.getVx() * time / (4 - i)) % 711);
		for(int i = 0; i < 8; i += 2) {
			background.get(i).getSprite().setViewport(new Rectangle2D(x.get(i/2), 0, 711 - x.get(i/2), 400));
			background.get(i+1).getSprite().setX(711 - x.get(i/2));
		}
		groundLeft.getSprite().setViewport(new Rectangle2D(x.get(3),0,711 - x.get(3), 400));
		groundRight.getSprite().setX(711 - x.get(3 ));


		for(Foe foe : foes) {
			if (!hero.isInvisible() && hero.getHitbox().intersects(foe.getHitbox()) && numberOfLives > 0) {
				System.out.println("Collision");
				hero.setInvincibility(2.5);
				numberOfLives -= 1;
				hearts.get(numberOfLives).getSprite().setViewport(new Rectangle2D(32, 0 , 32, 32));
				if(numberOfLives == 0) end = true;
			}
		}

		ArrayList<Integer> ballsToRemove = new ArrayList<>();
		ArrayList<Integer> foesToRemove = new ArrayList<>();

		for(EnergyBall energyBall : energyBalls) {
			for(Foe foe : foes) {
				if (energyBall.getHitbox().intersects(foe.getHitbox())) {
					System.out.println("Touched");
					ballsToRemove.add(energyBalls.indexOf(energyBall));
					energyBall.getSprite().setViewport(new Rectangle2D(0, 0, 1, 1));
					foe.setDeathAnimation(true);
				}
			}
		}
		for(EnergyBall energyBall : energyBalls) if (energyBall.getIndex() == 320) ballsToRemove.add(energyBalls.indexOf(energyBall));
		for(Foe foe : foes) {
			if (foe.getToRemove()) {
				System.out.println("detruit");
				foesToRemove.add(foes.indexOf(foe));
			}
		}
		for(Integer i : ballsToRemove) {
			energyBalls.get(i).getSprite().setVisible(false);
			energyBalls.remove((int) i);
		}
		for(Integer i : foesToRemove){
			foes.get(i).getSprite().setVisible(false);
			foes.remove((int) i);
		}
	}
}
