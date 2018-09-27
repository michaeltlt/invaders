package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static game.Constants.*;

public class LivesMeter {
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
        this.image = new Image(FILE);
        this.imageWidth = image.getWidth();
        this.imageHeight = image.getHeight();
//        this.destWidth = imageWidth / 1.5;
//        this.destHeight = imageHeight / 1.5;
        this.positionX = imageWidth;
        this.positionY = imageHeight - 25;
    }

    public void render(GraphicsContext gc) {
        for (int i = 0; i < lives; i++) {
            gc.drawImage(image, POS_X + i * positionX, positionY);
        }
    }

    public void update(int lives) {
        this.lives = lives;
    }
}
