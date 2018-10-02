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
    private Set<KeyCode> input;

    private boolean done;
    private int score;

    public GameOver(int score) {
        this.score = score;

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

        Label scoreLabel = new Label();
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 24));
        scoreLabel.setText("Your score is " + score);
        scoreLabel.setTranslateX(SCREEN_WIDTH / 2 - 120);
        scoreLabel.setTranslateY(SCREEN_HEIGHT / 2);

        Label infoLabel = new Label();
        infoLabel.setTextFill(Color.WHITE);
        infoLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 16));
        infoLabel.setText("press Q for quit");
        infoLabel.setTranslateX(SCREEN_WIDTH / 2 - 75);
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
            done = true;
        }
        else if(input.contains(KeyCode.SPACE)) {

        }
    }

    public boolean isDone() {
        return done;
    }
}
