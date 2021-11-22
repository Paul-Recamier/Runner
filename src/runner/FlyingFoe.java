package runner;

import javafx.geometry.Rectangle2D;

public class FlyingFoe extends AnimatedThing{

    private Boolean toRemove = false;

    public FlyingFoe(double posx, double posy, double x, double y, double width, double height, String filename, double vx) {
        super(posx, posy, x, y, width, height, filename, vx);

        frameDuration = 0;
        index = 0;
        maximumIndex = 128 * 4;
    }

    @Override
    public void update(double time, double vxCam, double xhero) {
        x -= vxCam * time;
        sprite.setX(x);
        frameDuration += 1;
        if(frameDuration == 10) {
            index = (index + 128) % maximumIndex;
            frameDuration = 0;
            sprite.setViewport(new Rectangle2D(index, 0, 128, 128));
        }

        hitbox = new Rectangle2D(x + 32,y + 14,29,26);

        if(x < -100) toRemove = true;
    }

    @Override
    public Boolean getToRemove() {
        return toRemove;
    }
}

