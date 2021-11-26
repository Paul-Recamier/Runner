package runner;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;

public class Main extends Application{
	
	private double past;
	
    public void start(Stage primaryStage){
        primaryStage.setTitle("Runner");
        Media musicMedia = new Media(new File("music/A Misty Sea of Forest.mp3").toURI().toString());
        MediaPlayer musicPlayer = new MediaPlayer(musicMedia);
        musicPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                musicPlayer.seek(Duration.ZERO);
                musicPlayer.play();
            }
        });
        musicPlayer.setVolume(0.1);
        welcomeScreen(primaryStage, musicPlayer);
    }

    public void welcomeScreen(Stage primaryStage, MediaPlayer musicPlayer) {

        musicPlayer.play();
        Group root = new Group();
        WelcomeScene welcomeScene = new WelcomeScene(root, 600, 400, musicPlayer);
        primaryStage.setScene(welcomeScene);
        primaryStage.show();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(welcomeScene.getNextScreen()) {
                    stop();
                    mainScreen(primaryStage, musicPlayer);
                }
                if(welcomeScene.getClose()) {
                    stop();
                    primaryStage.close();
                }
            }
        };
        timer.start();
    }

    public void mainScreen(Stage primaryStage, MediaPlayer musicPlayer){
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
                    endScreen(gameScene, primaryStage, musicPlayer);
                }

            }
        };
        
        timer.start();
    }

    private void endScreen(GameScene gameScene, Stage primaryStage, MediaPlayer musicPlayer) {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(gameScene.getNextScreen()) {
                    stop();
                    welcomeScreen(primaryStage, musicPlayer);
                }
            }
        };
        timer.start();

    }

    public static void main(String[] args) {
        launch(args);
    }
}