package game;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Explosion extends Pane {
    private static final Image IMAGE = new Image("file:assets/explosion.png");

    private static final int FRAMES    =  16;
    private static final int WIDTH    = 32;
    private static final int HEIGHT   = 32;

    private ImageView imageView;
    int offsetX = 0;
    int offsetY = 0;
    int score = 0;
    Rectangle removeRect = null;
    private SpriteAnimation animation;

    public Explosion() {


        imageView = new ImageView(IMAGE);
        imageView.setViewport(new Rectangle2D(offsetX, offsetY, WIDTH, HEIGHT));

        animation = new SpriteAnimation(
                imageView, Duration.millis(200), FRAMES, FRAMES, offsetX, offsetY, WIDTH, HEIGHT, 1);
        getChildren().addAll(imageView);
        animation.playFromStart();
    }

//    public void render(GraphicsContext gc)
//    {
//        if(frame < FRAMES) {
//            double spriteX = frame * WIDTH;
//            gc.drawImage(IMAGE, spriteX, 0, WIDTH, HEIGHT, positionX, positionY, WIDTH, HEIGHT);
//            frame++;
//        }
//        else {
//            inProgress = false;
//        }
//    }

    public boolean isInProgress() {
        Animation.Status status = animation.getStatus();

        return status == Animation.Status.RUNNING;
    }

    public void setPosition(double x, double y) {
        setTranslateX(x);
        setTranslateY(y);
    }
}
