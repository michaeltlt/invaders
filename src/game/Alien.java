package game;

public class Alien extends Sprite {
    private static final String FILE = "file:assets/enemy.png";

    private boolean alive = true;

    public Alien() {
        setImage(FILE);
    }

    public void die() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }
}
