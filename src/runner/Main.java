package runner;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Main extends Application{
	
	private double past;
	
    public void start(Stage primaryStage){
        primaryStage.setTitle("Runner");
        Group root = new Group();
        GameScene gameScene = new GameScene(root, 600, 400);
        primaryStage.setScene(gameScene);
        primaryStage.show();
        
        AnimationTimer timer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				double time = (now - past) * Math.pow(10,  -9);
				gameScene.getHero().update(time, gameScene.getCam().getX());
				gameScene.getEnergyBall().update(time);
				gameScene.getCactus().update(time, gameScene.getCam().getVx());
				gameScene.getCam().update(time, gameScene.getHero().getX());
				gameScene.update(time);
				past = now;
			}
        };
        
        timer.start();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}