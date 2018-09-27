package game;

import javafx.scene.layout.HBox;

import static game.Constants.*;

public class TopPanel extends HBox {
    private Score score;
    private LivesMeter meter;

    public TopPanel() {
        score = new Score();
        meter = new LivesMeter();

        getChildren().addAll(score, meter);

        setTranslateX(LEFT_BORDER);
        setTranslateY(BORDER);
    }

    public void updateScore(int value) {
        score.update(value);
    }
}
