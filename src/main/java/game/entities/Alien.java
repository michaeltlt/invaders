package game.entities;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import static game.Constants.ALIEN_IMAGE;

public class Alien extends Pane {
    private static final double RELOAD_TIME_SEC = 2;

    private double reloadingTime;

    public Alien() {
        ImageView imageView = new ImageView(ALIEN_IMAGE);
        getChildren().add(imageView);
    }

    public Shot shoot() {
        Shot bullet = new Plasma();
        bullet.setPosition(getParent().getTranslateX() + getTranslateX() + getWidth() / 2,
                getParent().getTranslateY() + getTranslateY());

        return bullet;
    }

    public void reload(double time) {
        reloadingTime += time;
    }

    public boolean readyToShoot() {
        if(reloadingTime >= RELOAD_TIME_SEC) {
            reloadingTime = 0;
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "Alien{" +
                "x = " + getTranslateX() +
                ", y = " + getTranslateY() +
                '}';
    }
}
