package game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

import static game.Constants.LIFE_IMAGE;
import static game.Constants.LIVES;

public class LevelController implements Initializable {
    @FXML
    private Label scoreLabel;
    @FXML
    private HBox livesMeter;

    private int lives;
    private int score;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reset();
    }

    public void reset() {
        score = 0;
        lives = LIVES;

        scoreLabel.setText("0");
        livesMeter.getChildren().clear();

        for (int i = 0; i < LIVES; i++) {
            ImageView imageView = new ImageView(LIFE_IMAGE);
            livesMeter.getChildren().add(imageView);
        }
    }

    public void updateScore(int value) {
        score += value;
        scoreLabel.setText(String.valueOf(score));
    }

    public int getScore() {
        return score;
    }

    public void decreaseLives() {
        lives--;
        livesMeter.getChildren().remove(lives);
    }
}
