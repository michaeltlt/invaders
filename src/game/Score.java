package game;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Score extends Pane {
    private static final String MSG = "SCORE: ";

    private Label label;
    private int value;

    public Score() {
        label = new Label();
        label.setTextFill(Color.WHITE);
        Font font = Font.font("Courier New", FontWeight.BOLD, 24);
        label.setFont(font);
        label.setText(MSG);

        getChildren().add(label);
    }

    public void update(int value) {
        this.value += value;
        label.setText(MSG + this.value);
    }
}
