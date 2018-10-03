package game.entities;

import static game.Constants.BULLET_IMAGE;

public class Plasma extends Shot {
    private static final double VELOCITY = 350.0;

    public Plasma() {
        super(BULLET_IMAGE, VELOCITY);
    }
}
