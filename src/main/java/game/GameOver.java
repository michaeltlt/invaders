package game;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static game.Constants.BG_IMAGE;
import static game.Constants.SCREEN_HEIGHT;
import static game.Constants.SCREEN_WIDTH;

public class GameOver {
    private Scene scene;
    private Pane root;
    private Label scoreLabel;
    private Set<KeyCode> input;

    private Status status = Status.STAY;

    public GameOver() {
        root = new Pane();
        root.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        scene = new Scene(root);
        init();

        input = new HashSet<>();
        Set<KeyCode> validCodes = new HashSet<>();
        Collections.addAll(validCodes, KeyCode.Q, KeyCode.SPACE);

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();

            if(validCodes.contains(code) && !input.contains(code)) {
                input.add(code);
            }
        });

        scene.setOnKeyReleased(event -> input.remove(event.getCode()));
    }

    public void reset() {
        status = Status.STAY;
        input.clear();
    }

    public void setScore(int score) {
        scoreLabel.setText("Your score is " + score);
    }

    public Scene getScene() {
        return scene;
    }

    private void init() {
        Label gameOverLabel = new Label();
        gameOverLabel.setTextFill(Color.WHITE);
        gameOverLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 54));
        gameOverLabel.setText("GAME OVER");
        gameOverLabel.setTranslateX(SCREEN_WIDTH / 2 - 150);
        gameOverLabel.setTranslateY(SCREEN_HEIGHT / 2 - 90);

        scoreLabel = new Label();
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 24));
        scoreLabel.setTranslateX(SCREEN_WIDTH / 2 - 120);
        scoreLabel.setTranslateY(SCREEN_HEIGHT / 2);

        Label infoLabel = new Label();
        infoLabel.setTextFill(Color.WHITE);
        infoLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 16));
        infoLabel.setText("press SPACE for restart or Q for quit");
        infoLabel.setTranslateX(SCREEN_WIDTH / 2 - 180);
        infoLabel.setTranslateY(SCREEN_HEIGHT / 2 + 60);

        setBackground();
        root.getChildren().addAll(gameOverLabel, scoreLabel, infoLabel);
    }

    private void setBackground() {
        ImageView imageView = new ImageView(BG_IMAGE);
        root.getChildren().add(imageView);
    }

    public void update(double elapsedTime) {
        handleKeys();
    }

    private void handleKeys() {
        if(input.contains(KeyCode.Q)) {
            status = Status.DONE;
        }
        else if(input.contains(KeyCode.SPACE)) {
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
