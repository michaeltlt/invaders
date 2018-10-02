package game;

import static game.Constants.BULLET_IMAGE;

public class Bullet extends Shot {
    private static final double VELOCITY = -350.0;

    public Bullet() {
        super(BULLET_IMAGE, VELOCITY);
    }
}
