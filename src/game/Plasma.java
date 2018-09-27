package game;

public class Plasma extends Shot {
    private static final String FILE = "file:assets/bullet.png";
    private static final double VELOCITY = 350.0;

    public Plasma() {
        super(FILE, VELOCITY);
    }
}
