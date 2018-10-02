package game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

import static game.Constants.*;

public class Main extends Application {

    private State state = State.LEVEL;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(TITLE);

        Level level = new Level();
        primaryStage.setScene(level.getScene());

        primaryStage.show();

        new AnimationTimer() {
            GameOver gameOver = new GameOver();

            long lastNanoTime = System.nanoTime();

            @Override
            public void handle(long currentNanoTime) {
                // Calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime) / 1_000_000_000.0;
                lastNanoTime = currentNanoTime;

                switch (state) {
                    case START:
                        level.reset();
                        primaryStage.setScene(level.getScene());
                        state = State.LEVEL;
                        break;
                    case LEVEL:
                        level.update(elapsedTime);

                        if(level.isDone()) {
                            state = State.GAME_OVER;
                            gameOver.reset();
                            gameOver.setScore(level.getScore());
                            primaryStage.setScene(gameOver.getScene());
                        }
                        break;
                    case GAME_OVER:
                        gameOver.update(elapsedTime);

                        if(gameOver.getStatus() == GameOver.Status.DONE) {
                            state = State.QUIT;
                        }
                        else if(gameOver.getStatus() == GameOver.Status.TRY_AGAIN) {
                            state = State.START;
                        }
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
