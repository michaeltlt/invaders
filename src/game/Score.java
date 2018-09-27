package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import static game.Constants.LEFT_BORDER;
import static game.Constants.TOP_BORDER;

public class Score implements Panel {
    private static final String MSG = "SCORE: ";

    private int value;
    private GraphicsContext gc;

    public Score(GraphicsContext gc) {
        this.gc = gc;

        gc.setFill(Color.WHITE);
        Font font = Font.font("Courier New", FontWeight.BOLD, 24);
        gc.setFont(font);
    }

    public void increase(int value) {
        this.value += value;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.fillText(MSG + value, LEFT_BORDER, TOP_BORDER);
    }

    @Override
    public void update() {

    }
}
