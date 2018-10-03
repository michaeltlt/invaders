package game;

import game.entities.Bullet;
import game.entities.Shot;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;

import static game.Constants.*;


public class Ship extends Pane {
    private static final double VELOCITY = 10.0;
    private static final double RELOAD_TIME_SEC = 0.5;

    private AudioClip shotAudio;
    private double reloadingTime;
    private int lives = LIVES;

    public Ship() {
        shotAudio = new AudioClip("jar:" + SHIP_SHOT_SOUND_URL.getPath());

        ImageView imageView = new ImageView(SHIP_IMAGE);
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

    public Shot shoot() {
        Shot bullet = new Bullet();
        bullet.setPosition(getTranslateX() + 19, getTranslateY());
        shotAudio.play();

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
