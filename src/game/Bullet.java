package game;

public class Bullet extends Sprite {
    private static final String FILE = "file:assets/bullet.png";
    private static final double VELOCITY = -350.0;

    private boolean alive = true;

    public Bullet() {
        setImage(FILE);
        setVelocity(0.0, VELOCITY);
    }

    public void explode() {
        alive = false;
    }
}
