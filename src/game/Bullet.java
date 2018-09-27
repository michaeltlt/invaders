package game;

public class Bullet extends Shot {
    private static final String FILE = "file:assets/bullet.png";
    private static final double VELOCITY = -350.0;

    public Bullet() {
        super(FILE, VELOCITY);
    }
}
