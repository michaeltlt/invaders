package game;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Ship extends Sprite {
    private static final double VELOCITY = 150.0;
    private static final String FILE = "file:assets/ship.png";

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

//    @Override
//    public void render(GraphicsContext gc) {
//        super.render(gc);
//
//        bullets.stream().forEach(bullet -> bullet.render(gc));
//    }

    public Bullet fire() {
        Bullet bullet = new Bullet();
        bullet.setPosition(getX() + 19, getY());

        return bullet;
    }
}
