package game;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;

import static game.Constants.*;

public class TopPanel extends BorderPane {
    private Score score;
    private LivesMeter meter;

    public TopPanel() {
        score = new Score();
        meter = new LivesMeter();

        setPrefWidth(SCREEN_WIDTH - BORDER);
        setPadding(new Insets(0, 40, 0, 20));
        setLeft(score);
        setRight(meter);

        setTranslateX(LEFT_BORDER);
        setTranslateY(BORDER);
    }

    public void updateScore(int value) {
        score.update(value);
    }

    public void decreaseLives() {
        meter.down();
    }
}
