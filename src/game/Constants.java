package game;

import javafx.scene.image.Image;
import javafx.scene.media.Media;

public final class Constants {
    private Constants() {}

    public static final String TITLE = "Space Invaders";

    public static final double SCREEN_WIDTH = 800;
    public static final double SCREEN_HEIGHT = 600;

    public static final double BORDER = 32;
    public static final double LEFT_BORDER = BORDER;
    public static final double RIGHT_BORDER = SCREEN_WIDTH - BORDER;
    public static final double TOP_BORDER = BORDER;

    public static final int LIVES = 3;

    public static final Image BG_IMAGE = new Image("file:assets/background.png");

    private String bgMusicFile = "StayTheNight.mp3";     // For example
//    Media sound = new Media(new File(musicFile).toURI().toString());
//    public static final Media BG_SOUND = new Media("file:assets/sounds/amb_doomdrones_mood_repeater_04.wav");
}
