package game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

import static game.Constants.*;

public class Main extends Application {

    private State state = State.LEVEL;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(TITLE);

        Level level = new Level();
        primaryStage.setScene(level.getScene());

        primaryStage.show();

//        final Media BG_SOUND = new Media("file:assets/sounds/amb_doomdrones_mood_repeater_04.wav");
        final Media BG_SOUND = new Media(new File("assets/sounds/amb_doomdrones_mood_repeater_04.wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(BG_SOUND);
        mediaPlayer.play();

        new AnimationTimer() {
            GameOver gameOver = null;

            long lastNanoTime = System.nanoTime();

            @Override
            public void handle(long currentNanoTime) {
                // Calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime) / 1_000_000_000.0;
                lastNanoTime = currentNanoTime;

                switch (state) {
                    case LEVEL:
                        level.update(elapsedTime);
                        if(level.isDone()) {
                            state = State.GAME_OVER;
                            gameOver = new GameOver(level.getScore());
                            primaryStage.setScene(gameOver.getScene());
                        }
                        break;
                    case GAME_OVER:
                        gameOver.update(elapsedTime);
                        if(gameOver.isDone()) state = State.QUIT;
                        break;
                    case QUIT:
                        stop();
                        System.exit(0);
                }
            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
