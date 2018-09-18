package game;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class AlienSwarm {
    private static final int ROWS = 5;
    private static final int COLS = 11;

    private Alien[][] aliens;

    private double velocity = 100;
    private double positionX = 154.0;
    private double positionY = 100.0;
    private double step = 14.0;

    private double width;
    private double height;

    public AlienSwarm() {
//        aliens = new ArrayList<>(ROWS * COLS);
        aliens = new Alien[COLS][ROWS];
        createAliens();

        width = COLS * (step + 32);
        height = ROWS * (step + 26);
    }

    private void createAliens() {
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                Alien alien = new Alien();
                alien.setPosition(positionX + i * (32 + step), positionY + j * (26 + step));
                aliens[i][j] = alien;
            }
        }
    }

    public void update(double time) {
        positionX += velocity * time;
//        System.out.println(positionX + width);
//        positionY += velocityY * time;

        if(positionX + width > 790) {
            velocity = -100;
            positionY += step;
        }
        else if(positionX < 32) {
            velocity = 100;
            positionY += step;
        }

        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                Alien alien = aliens[i][j];

                alien.setPosition(positionX + i * (32 + step), positionY + j * (26 + step));
//                alien.setVelocity(velocity, 0.0);
                alien.update(time);
            }
        }
    }

    public void render(GraphicsContext gc) {
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                aliens[i][j].render(gc);
            }
        }
    }

    private void setAlienPosition(Alien alien, double x, double y) {

    }
}
