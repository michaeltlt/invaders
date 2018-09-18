package game;

public class Bullet extends Sprite {
    private static final String FILE = "file:assets/bullet.png";
    private static final double VELOCITY = -250.0;

    public Bullet() {
        setImage(FILE);
        setVelocity(0.0, VELOCITY);
    }
}
