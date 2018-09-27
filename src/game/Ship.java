package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


import static game.Constants.*;

public class Ship extends Pane {
    private static final String FILE = "file:assets/ship.png";
    private static final double VELOCITY = 10.0;
    private static final double RELOAD_TIME_SEC = 0.5;

    private double reloadingTime;
    private int lives = LIVES;

    public Ship() {
        Image image = new Image(FILE);
        ImageView imageView = new ImageView(image);
        getChildren().add(imageView);
        reset();
    }

    public void reset() {
        setTranslateX(SCREEN_WIDTH / 2 - 24);
        setTranslateY(SCREEN_HEIGHT - 60);
    }

    public void left() {
        double x = getTranslateX() - VELOCITY;

        if(x < LEFT_BORDER) x = LEFT_BORDER;

        setTranslateX(x);
    }

    public void right() {
        double x = getTranslateX() + VELOCITY;

        if(x + getWidth() > RIGHT_BORDER) x = RIGHT_BORDER - getWidth();

        setTranslateX(x);
    }

    public Shot fire() {
        Shot bullet = new Bullet();
        bullet.setPosition(getTranslateX() + 19, getTranslateY());

        return bullet;
    }

    public void reload(double time) {
        reloadingTime += time;
    }

    public boolean canFire() {
        if(reloadingTime >= RELOAD_TIME_SEC) {
            reloadingTime = 0;
            return true;
        }

        return false;
    }

    public void die() {
        lives--;
        reset();
    }

    public int getLives() {
        return lives;
    }
}
