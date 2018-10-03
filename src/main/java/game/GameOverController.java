package game;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class GameOverController {
    @FXML
    private Label score;

    public void setScore(int value) {
        score.setText(String.valueOf(value));
    }
}
