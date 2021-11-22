package runner;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class GameScene extends Scene {

	private Camera camera;
	private StaticThing skyLeft, skyRight, bgDecorLeft, bgDecorRight, mdLeft, mdRight, fgLeft, fgRight, groundLeft, groundRight;
	private ArrayList<StaticThing> background = new ArrayList();
	private Hero hero;
	private ArrayList<AnimatedThing> foes = new ArrayList();
	private ArrayList<EnergyBall>  energyBalls = new ArrayList();
	private ArrayList<StaticThing> hearts = new ArrayList();
	private StaticThing manaVide, manaPlein;
	private Integer numberOfLives = 3;
	private ArrayList<Integer> x = new ArrayList(Arrays.asList(500, 500, 500, 500));
	private String screen; // main, end
	private Boolean nextScreen = false;
	private double mana = 100;
	private Integer score = 0;
	private Text scoreDisplay;
	private Text scoreOutline;
	private double pFoe = 0.1;
	private double pFlyingFoe = 0.1;

	public GameScene(Group root, int width, int height) {
		super(root, width, height);

		hero = new Hero(0, 250, 0, 0, 128, 128, "file:img/Mage.png", 400);
		for(int i=0; i < 0; i++) {
			Foe foe = new Foe(Math.random()*0 + 1000, 250, 2432, 384, 128, 128, "file:img/Foe.png", 400);
			foes.add(foe);
		}
		camera = new Camera(0, 0);

		skyLeft = new StaticThing(0, 0, 500, 0, 211, 400, "file:img/Sky.png"); background.add(skyLeft);
		skyRight = new StaticThing(0, 0, 0, 0, 711, 400, "file:img/Sky.png"); background.add(skyRight);
		bgDecorLeft = new StaticThing(0, 0, 500, 0, 211, 400, "file:img/BG_Decor.png"); background.add(bgDecorLeft);
		bgDecorRight = new StaticThing(0, 0, 0, 0, 711, 400, "file:img/BG_Decor.png"); background.add(bgDecorRight);
		mdLeft = new StaticThing(0, 0, 500, 0, 211, 400, "file:img/Middle_Decor.png"); background.add(mdLeft);
		mdRight = new StaticThing(0, 0, 0, 0, 711, 400, "file:img/Middle_Decor.png"); background.add(mdRight);
		fgLeft = new StaticThing(0, 0, 500, 0, 211, 400, "file:img/Foreground.png"); background.add(fgLeft);
		fgRight = new StaticThing(0, 0, 0, 0, 711, 400, "file:img/Foreground.png"); background.add(fgRight);
		groundLeft = new StaticThing(0, 0, 500, 0, 211, 400, "file:img/Ground.png");
		groundRight = new StaticThing(0, 0, 0, 0, 711, 400, "file:img/Ground.png");

		for(int i = 0; i<numberOfLives; i++) {
			StaticThing heart = new StaticThing(16 + i*40, 12, 0, 0, 32, 32, "file:img/hearth.png");
			hearts.add(heart);
		}

		manaVide = new StaticThing(16,52, 0,24,112,18, "file:img/mana.png");
		manaPlein = new StaticThing(16,54, 0,3,112,18, "file:img/mana.png");

		scoreDisplay = new Text(225,54,"SCORE : 0");
		scoreDisplay.setFont(Font.loadFont("file:font/Pixel LCD-7.ttf",18));
		scoreOutline = new Text(227,56,"SCORE : 0");
		scoreOutline.setFont(Font.loadFont("file:font/Pixel LCD-7.ttf",18));
		scoreDisplay.setFill(Color.web("0xF2CB05"));

		for(StaticThing bg : background) root.getChildren().add(bg.getSprite());
        root.getChildren().add(hero.getSprite());
		for(StaticThing heart : hearts) root.getChildren().add(heart.getSprite());
		root.getChildren().add(manaVide.getSprite());
		root.getChildren().add(manaPlein.getSprite());
		root.getChildren().add(scoreOutline);
		root.getChildren().add(scoreDisplay);
		root.getChildren().add(groundLeft.getSprite());
		root.getChildren().add(groundRight.getSprite());

		this.setOnKeyPressed( event -> {
			if(event.getCode().equals(KeyCode.SPACE) && (hero.getAttitude().equals("running") || hero.getAttitude().equals("running & shoot"))) {
				System.out.println("Jump");
				hero.setAttitude("jumping up");
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
			if(screen.equals("main") && mana > 20 && hero.getAttitude().equals("running")) {
				mana -= 20;
				System.out.println("Shoot");
				hero.setAttitude("running & shoot");
			}
			if(screen.equals("end")) {
				nextScreen = true;
			}
		});
	}

	public void update(double time, Group root) {
		if(time > 1) time = 0;
		for(int i = 0; i < 4; i++) x.set(i, (int) (x.get(i) + camera.getVx() * time / (4 - i)) % 711);
		for(int i = 0; i < 8; i += 2) {
			background.get(i).getSprite().setViewport(new Rectangle2D(x.get(i/2), 0, 711 - x.get(i/2), 400));
			background.get(i+1).getSprite().setX(711 - x.get(i/2));
		}
		groundLeft.getSprite().setViewport(new Rectangle2D(x.get(3),0,711 - x.get(3), 400));
		groundRight.getSprite().setX(711 - x.get(3));

		if(Math.random() < pFoe / 100) {
			pFoe = 0.1;
			Foe foe = new Foe(hero.getX() - camera.getX() + 1000, 250, 2432, 384, 128, 128, "file:img/Foe.png", 400);
			foes.add(foe);
			root.getChildren().add(foe.getSprite());
		}
		else pFoe += 0.01;

		if(Math.random() < pFlyingFoe / 100) {
			pFlyingFoe = 0.1;
			FlyingFoe foe = new FlyingFoe(hero.getX() - camera.getX() + 1000, 120, 0, 0, 128, 64, "file:img/Bat.png", 400);
			foes.add(foe);
			root.getChildren().add(foe.getSprite());
		}
		else pFlyingFoe += 0.01;

		for(AnimatedThing foe : foes) {
			if (!hero.isInvisible() && hero.getHitbox().intersects(foe.getHitbox()) && numberOfLives > 0) {
				System.out.println("Collision");
				hero.setInvincibility(2.5);
				numberOfLives -= 1;
				hearts.get(numberOfLives).getSprite().setViewport(new Rectangle2D(32, 0 , 32, 32));
				if(numberOfLives == 0) hero.setDeathAnimation(true);
			}
		}

		if(mana < 100) mana += 0.1;
		manaPlein.getSprite().setViewport(new Rectangle2D(0,3,112*mana/100,18));

		score = (int) hero.getX();
		scoreDisplay.setText("SCORE : " + score);
		scoreOutline.setText("SCORE : " + score);

		ArrayList<Integer> ballsToRemove = new ArrayList<>();
		ArrayList<Integer> foesToRemove = new ArrayList<>();

		for(EnergyBall energyBall : energyBalls) {
			for(AnimatedThing foe : foes) {
				if (energyBall.getHitbox().intersects(foe.getHitbox())) {
					System.out.println("Touched");
					ballsToRemove.add(energyBalls.indexOf(energyBall));
					energyBall.getSprite().setViewport(new Rectangle2D(0, 0, 1, 1));
					foe.setDeathAnimation(true);
				}
			}
		}
		for(EnergyBall energyBall : energyBalls) if (energyBall.getIndex() == 320) ballsToRemove.add(energyBalls.indexOf(energyBall));
		for(AnimatedThing foe : foes) {
			if (foe.getToRemove()) {
				System.out.println("detruit");
				foesToRemove.add(foes.indexOf(foe));
			}
		}
		for(Integer i : ballsToRemove){
			energyBalls.get(i).getSprite().setVisible(false);
			energyBalls.remove((int) i);
		}
		for(Integer i : foesToRemove){
			foes.get(i).getSprite().setVisible(false);
			foes.remove((int) i);
		}
	}

	public Hero getHero() {
		return hero;
	}
	public Camera getCam() {
		return camera;
	}
	public ArrayList<EnergyBall> getEnergyBalls() {
		return energyBalls;
	}
	public ArrayList<AnimatedThing> getFoes() {
		return foes;
	}
	public void setScreen(String screen) {
		this.screen = screen;
	}
	public Boolean getNextScreen() {
		return nextScreen;
	}
}
