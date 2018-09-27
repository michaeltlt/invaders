package game;

public class Shot extends Sprite {
    private boolean alive = true;

    public Shot(String fileName, double velocity) {
        setImage(fileName);
        setVelocity(0.0, velocity);
    }

    public void explode() {
        alive = false;
    }
}
