package game;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import static game.Constants.*;

public class LivesMeter extends HBox {

    private int lives = LIVES;

    public LivesMeter() {
        reset();
    }

    private void reset() {
        for (int i = 0; i < LIVES; i++) {
            ImageView imageView = new ImageView(LIFE_IMAGE);
            getChildren().add(imageView);
        }
    }

    public void down() {
        lives--;
        getChildren().remove(lives);
    }
}
