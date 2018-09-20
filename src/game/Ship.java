package game;

public class Ship extends Sprite {
    private static final String FILE = "file:assets/ship.png";
    private static final double VELOCITY = 150.0;
    private static final double RELOAD_TIME_SEC = 0.5;

    private double reloadingTime;

    public Ship() {
        setImage(FILE);
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

    public Bullet fire() {
        Bullet bullet = new Bullet();
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
}
