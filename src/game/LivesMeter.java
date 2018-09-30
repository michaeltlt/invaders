package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import static game.Constants.*;

public class LivesMeter extends HBox {
    private static final String FILE = "file:assets/life.png";

    private Image image;
    private int lives = LIVES;

    public LivesMeter() {
        image = new Image(FILE);
        reset();
    }

    private void reset() {
        for (int i = 0; i < LIVES; i++) {
            ImageView imageView = new ImageView(image);
            getChildren().add(imageView);
        }
    }

    public void down() {
        lives--;
        getChildren().remove(lives);
    }
}
