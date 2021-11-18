package runner;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class WelcomeScene extends Scene {

    private Boolean nextScreen = false;

    public WelcomeScene(Group root, int width, int height) {
        super(root, width, height);
        root.getChildren().add(new StaticThing(0,0,0,0,711,400, System.getProperty("user.dir") + "/img/Sky.png").getSprite());
        root.getChildren().add(new StaticThing(0,0,0,0,711,400, System.getProperty("user.dir") + "/img/BG_Decor.png").getSprite());
        root.getChildren().add(new StaticThing(0,0,0,0,711,400, System.getProperty("user.dir") + "/img/Middle_Decor.png").getSprite());
        root.getChildren().add(new StaticThing(0,0,0,0,711,400, System.getProperty("user.dir") + "/img/Foreground.png").getSprite());
        root.getChildren().add(new StaticThing(0,0,0,0,711,400, System.getProperty("user.dir") + "/img/Ground.png").getSprite());

        root.getChildren().add(new StaticThing(220,200,0,0,163,60,System.getProperty("user.dir") + "/img/Button.png").getSprite());
        root.getChildren().add(new StaticThing(220,280,0,0,163,60,System.getProperty("user.dir") + "/img/Button.png").getSprite());
        Text play = new Text(244,247,"PLAY");
        play.setFont(new Font("Century Gothic Gras", 50));
        Text exit = new Text(255,327,"EXIT");
        exit.setFont(new Font("Century Gothic Gras", 50));
        root.getChildren().add(play);
        root.getChildren().add(exit);

        this.setOnMouseClicked(event -> {
            System.out.println("Clicked");
            nextScreen = true;
        });
    }
    public Boolean getNextScreen() {
        return nextScreen;
    }
}
