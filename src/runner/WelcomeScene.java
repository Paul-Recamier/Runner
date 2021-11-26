package runner;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class WelcomeScene extends Scene {

    private Boolean nextScreen = false;
    private Boolean close = false;
    private int xbutton;

    public WelcomeScene(Group root, int width, int height, MediaPlayer musicplayer) {
        super(root, width, height);
        root.getChildren().add(new StaticThing(0,0,50,0,711,400, "file:img/Sky.png").getSprite());
        root.getChildren().add(new StaticThing(0,0,50,0,711,400, "file:img/BG_Decor.png").getSprite());
        root.getChildren().add(new StaticThing(0,0,50,0,711,400, "file:img/Middle_Decor.png").getSprite());
        root.getChildren().add(new StaticThing(0,0,50,0,711,400, "file:img/Foreground.png").getSprite());
        root.getChildren().add(new StaticThing(0,0,50,0,711,400, "file:img/Ground.png").getSprite());

        root.getChildren().add(new StaticThing(220,200,0,0,163,60,"file:img/Button.png").getSprite());
        root.getChildren().add(new StaticThing(220,280,0,0,163,60,"file:img/Button.png").getSprite());

        Rectangle2D playButton = new Rectangle2D(220,200,163,60);
        Rectangle2D exitButton = new Rectangle2D(220,280,163,60);
        Text play = new Text(244,247,"PLAY");
        play.setFont(new Font("Century Gothic Gras", 50));
        Text exit = new Text(255,327,"EXIT");
        exit.setFont(new Font("Century Gothic Gras", 50));
        Text title = new Text(63,150,"RUNNER");
        title.setFont(new Font("Century Gothic Gras", 120));
        title.setFill(Color.GOLD);
        Text outlineTitle = new Text(67,154,"RUNNER");
        outlineTitle.setFont(new Font("Century Gothic Gras", 120));

        xbutton = 6;
        ImageView soundButton = new StaticThing(530, 340, xbutton, 6, 55, 43, "file:img/Sound.png").getSprite();
        Rectangle2D soundHitbox = new Rectangle2D(530,340,55,43);

        root.getChildren().add(outlineTitle);
        root.getChildren().add(title);
        root.getChildren().add(play);
        root.getChildren().add(exit);
        root.getChildren().add(soundButton);

        this.setOnMouseClicked(event -> {
            System.out.println("Clicked");
            if(playButton.intersects(new Rectangle2D(event.getX(), event.getY(), 1,1))) nextScreen = true;
            if(exitButton.intersects(new Rectangle2D(event.getX(), event.getY(), 1,1))) close = true;
            if(soundHitbox.intersects(new Rectangle2D(event.getX(), event.getY(), 1,1))) {
                xbutton = 76 - xbutton;
                soundButton.setViewport(new Rectangle2D(xbutton,61,55,43));
                if(xbutton==6) musicplayer.play();
                if(xbutton==70) musicplayer.pause();
            }
        });

        this.setOnMouseMoved(event -> {
            if(playButton.intersects(new Rectangle2D(event.getX(), event.getY(), 1,1))) play.setFill(Color.SILVER);
            else play.setFill(Color.BLACK);
            if(exitButton.intersects(new Rectangle2D(event.getX(), event.getY(), 1,1))) exit.setFill(Color.SILVER);
            else exit.setFill(Color.BLACK);
            if(soundHitbox.intersects(new Rectangle2D(event.getX(), event.getY(), 1,1))) soundButton.setViewport(new Rectangle2D(xbutton,61,55,43));
            else soundButton.setViewport(new Rectangle2D(xbutton,6,55,43));
        });
    }
    public Boolean getNextScreen() {
        return nextScreen;
    }

    public boolean getClose() {
        return close;
    }
}
