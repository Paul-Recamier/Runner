package runner;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application{
	
	private double past;
	
    public void start(Stage primaryStage){
        primaryStage.setTitle("Runner");
        welcomeScreen(primaryStage);
    }

    public void welcomeScreen(Stage primaryStage) {
        Group root = new Group();
        WelcomeScene welcomeScene = new WelcomeScene(root, 600, 400);
        primaryStage.setScene(welcomeScene);
        primaryStage.show();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(welcomeScene.getNextScreen()) {
                    stop();
                    mainScreen(primaryStage);
                }
                if(welcomeScene.getClose()) {
                    stop();
                    primaryStage.close();
                }
            }
        };
        timer.start();
    }

    public void mainScreen(Stage primaryStage){
        Group root = new Group();
        GameScene gameScene = new GameScene(root, 600, 400);
        gameScene.setScreen("main");
        primaryStage.setScene(gameScene);
        primaryStage.show();
        
        AnimationTimer timer = new AnimationTimer() {

			@Override
			public void handle(long now) {
                double time = (now - past) * Math.pow(10,  -9);
                ArrayList<EnergyBall> energyBalls = gameScene.getEnergyBalls();
				gameScene.getHero().update(time, gameScene.getCam().getX(), energyBalls, root);
				for(EnergyBall energyBall : energyBalls) energyBall.update(time);
                ArrayList<AnimatedThing> foes = gameScene.getFoes();
				for(AnimatedThing foe : foes) foe.update(time, gameScene.getCam().getVx(), gameScene.getHero().getX() - gameScene.getCam().getX());
				gameScene.getCam().update(time, gameScene.getHero().getX(), gameScene.getHero().getDeathAnimation());
				gameScene.update(time, root);
				past = now;

                if(gameScene.getHero().getEnd()){
                    stop();
                    root.getChildren().add(new StaticThing(100,100,0,0,400,206, "file:img/Game_Over.png").getSprite());
                    gameScene.setScreen("end");
                    endScreen(gameScene, primaryStage);
                }

            }
        };
        
        timer.start();
    }

    private void endScreen(GameScene gameScene, Stage primaryStage) {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(gameScene.getNextScreen()) {
                    stop();
                    welcomeScreen(primaryStage);
                }
            }
        };
        timer.start();

    }

    public static void main(String[] args) {
        launch(args);
    }
}