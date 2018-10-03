package game;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class GameOver {
    private Scene scene;
    private Pane root;
    private GameOverController controller;
    private KeysHandler keysHandler;

    private Status status = Status.STAY;

    public GameOver() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameover.fxml"));

        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        scene = new Scene(root);
        controller = loader.getController();
        keysHandler = new KeysHandler(KeyCode.Q, KeyCode.SPACE);

        scene.setOnKeyPressed(keysHandler.pressedHandler());
        scene.setOnKeyReleased(keysHandler.releasedHandler());
    }

    public void reset() {
        status = Status.STAY;
        keysHandler.reset();
    }

    public void setScore(int score) {
        controller.setScore(score);
    }

    public Scene getScene() {
        return scene;
    }

    public void update(double elapsedTime) {
        handleKeys();
    }

    private void handleKeys() {
        if(keysHandler.isPressed(KeyCode.Q)) {
            status = Status.DONE;
        }
        else if(keysHandler.isPressed(KeyCode.SPACE)) {
            status = Status.TRY_AGAIN;
        }
    }

    public Status getStatus() {
        return status;
    }

    static enum Status {
        STAY,
        DONE,
        TRY_AGAIN
    }
}
