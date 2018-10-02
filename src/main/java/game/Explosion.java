package game;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

import static game.Constants.EXPLOSION_IMAGE;
import static game.Constants.EXPLOSION_SOUND_URL;

public class Explosion extends Pane {
    private static final int FRAMES    =  16;
    private static final int WIDTH    = 64;
    private static final int HEIGHT   = 64;

    private ImageView imageView;
    private SpriteAnimation animation;

    private int offsetX = 0;
    private int offsetY = 0;

    public Explosion() {
        AudioClip audioClip = new AudioClip("jar:" + EXPLOSION_SOUND_URL.getPath());
        audioClip.play();

        imageView = new ImageView(EXPLOSION_IMAGE);
        imageView.setViewport(new Rectangle2D(offsetX, offsetY, WIDTH, HEIGHT));

        animation = new SpriteAnimation(
                imageView, Duration.millis(200), FRAMES, FRAMES, offsetX, offsetY, WIDTH, HEIGHT, 1);
        getChildren().addAll(imageView);
        animation.playFromStart();
    }


    public boolean isInProgress() {
        Animation.Status status = animation.getStatus();

        return status == Animation.Status.RUNNING;
    }

    public void setPosition(double x, double y) {
        setTranslateX(x);
        setTranslateY(y);
    }
}
