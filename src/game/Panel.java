package game;

import javafx.scene.canvas.GraphicsContext;

public interface Panel {
    void render(GraphicsContext gc);
    void update();
}
