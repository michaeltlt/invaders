package game;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.net.URL;

public final class Constants {
    private Constants() {}

    public static final String TITLE = "Space Invaders";

    public static final double SCREEN_WIDTH = 800;
    public static final double SCREEN_HEIGHT = 600;

    public static final double BORDER = 32;
    public static final double LEFT_BORDER = BORDER;
    public static final double RIGHT_BORDER = SCREEN_WIDTH - BORDER;

    public static final int LIVES = 3;

    private static final InputStream BG_IMG_STREAM = Constants.class.getResourceAsStream("/images/background.png");
    private static final InputStream ALIEN_IMG_STREAM = Constants.class.getResourceAsStream("/images/enemy.png");
    private static final InputStream BULLET_IMG_STREAM = Constants.class.getResourceAsStream("/images/bullet.png");
    private static final InputStream LIFE_IMG_STREAM = Constants.class.getResourceAsStream("/images/life.png");
    private static final InputStream SHIP_IMG_STREAM = Constants.class.getResourceAsStream("/images/ship.png");
    private static final InputStream EXPLOSION_IMG_STREAM = Constants.class.getResourceAsStream("/images/explosion.png");

    public static final URL EXPLOSION_SOUND_URL = Constants.class.getResource("/sounds/explosion.mp3");
    public static final URL SHIP_SHOT_SOUND_URL = Constants.class.getResource("/sounds/ship_shot.mp3");
    public static final URL LEVEL_BG_SOUND_URL = Constants.class.getResource("/sounds/level_background.mp3");

    public static final Image BG_IMAGE = new Image(BG_IMG_STREAM);
    public static final Image ALIEN_IMAGE = new Image(ALIEN_IMG_STREAM);
    public static final Image BULLET_IMAGE = new Image(BULLET_IMG_STREAM);
    public static final Image LIFE_IMAGE = new Image(LIFE_IMG_STREAM);
    public static final Image SHIP_IMAGE = new Image(SHIP_IMG_STREAM);
    public static final Image EXPLOSION_IMAGE = new Image(EXPLOSION_IMG_STREAM);
}
