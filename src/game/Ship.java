package game;

import static game.Constants.*;

public class Ship extends Sprite {
    private static final String FILE = "file:assets/ship.png";
    private static final double VELOCITY = 150.0;
    private static final double RELOAD_TIME_SEC = 0.5;

    private double reloadingTime;
    private int lives = LIVES;

    public Ship() {
        setImage(FILE);
        reset();
    }

    public void reset() {
        setPosition(SCREEN_WIDTH / 2 - 24, SCREEN_HEIGHT - 60);
    }

    public void left() {
        addVelocity(-VELOCITY, 0.0);
    }

    public void right() {
        addVelocity(VELOCITY, 0.0);
    }

    public void stop() {
        setVelocity(0.0, 0.0);
    }

    public Shot fire() {
        Shot bullet = new Bullet();
        bullet.setPosition(getX() + 19, getY());

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
