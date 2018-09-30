package game;

import javafx.application.Application;
import javafx.stage.Stage;

import static game.Constants.*;

public class Main extends Application {




    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle(TITLE);

        Level level = new Level();
        primaryStage.setScene(level.getScene());

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
