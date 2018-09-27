package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import static game.Constants.*;

public class LivesMeter extends HBox {
    private static final double POS_X = SCREEN_WIDTH - 120;
    private static final String FILE = "file:assets/life.png";

    private Image image;

    private double imageWidth;
    private double imageHeight;
    private double destWidth;
    private double destHeight;
    private double positionX;
    private double positionY;
    private int lives;

    public LivesMeter() {
        Image image = new Image(FILE);

        reset();


//        this.image = new Image(FILE);
//        this.imageWidth = image.getWidth();
//        this.imageHeight = image.getHeight();
//        this.positionX = imageWidth;
//        this.positionY = imageHeight - 25;
    }

    private void reset() {
        for (int i = 0; i < LIVES; i++) {
            ImageView imageView = new ImageView(image);
            getChildren().add(imageView);
        }
    }

    public void render(GraphicsContext gc) {
        for (int i = 0; i < lives; i++) {
            gc.drawImage(image, POS_X + i * positionX, positionY);
        }
    }

    public void decrease() {
        lives--;
        getChildren().remove(lives);
    }
}
