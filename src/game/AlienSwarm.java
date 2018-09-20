package game;

import javafx.scene.canvas.GraphicsContext;

public class AlienSwarm {
    private static final int ROWS = 5;
    private static final int COLS = 11;

    private Alien[][] aliens;

    private double velocity = 100;
    private double positionX = 154.0;
    private double positionY = 100.0;
    private double gap = 14.0;

    private double width;

    public AlienSwarm() {
        aliens = new Alien[COLS][ROWS];
        createAliens();

        width = COLS * (gap + 32);
//        height = ROWS * (gap + 26);
    }

    private void createAliens() {
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                Alien alien = new Alien();
                alien.setPosition(positionX + i * (32 + gap), positionY + j * (26 + gap));
                aliens[i][j] = alien;
            }
        }
    }

    public void update(double time) {
        positionX += velocity * time;

        if(positionX + width > 790) {
            velocity = -100;
            positionY += gap;
        }
        else if(positionX < 32) {
            velocity = 100;
            positionY += gap;
        }


        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                Alien alien = aliens[i][j];

                if(alien != null) {
                    double x = positionX + i * (32 + gap);
                    double y = positionY + j * (26 + gap);
                    alien.setPosition(x, y);

//                alien.setPosition(positionX + i * (32 + gap), positionY + j * (26 + gap));
                    alien.update(time);
                }
            }
        }
    }

    public void render(GraphicsContext gc) {
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                if(aliens[i][j] != null) aliens[i][j].render(gc);
            }
        }
    }

    public boolean intersects(Bullet bullet) {
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                Alien alien = aliens[i][j];

                if(alien != null && alien.intersects(bullet)) {
                    aliens[i][j] = null;
                    return true;
                }
            }
        }

        return false;
    }
}
