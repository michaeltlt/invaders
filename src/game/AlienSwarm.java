package game;

import javafx.scene.canvas.GraphicsContext;

import static game.Constants.*;

import java.util.*;

public class AlienSwarm {
    private static final int ROWS = 5;
    private static final int COLS = 11;

    private Alien[][] aliens;

    private double velocity = 100;
    private double velocityDelta = 2;
    private double positionX = 154.0;
    private double positionY = 100.0;
    private double gap = 14.0;

    private double width;
    private Ship ship;
    private List<Shot> shots;

    public AlienSwarm() {
        aliens = new Alien[COLS][ROWS];
        shots = new LinkedList<>();
        createAliens();

        width = COLS * (gap + 26);
    }

    private void createAliens() {
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                Alien alien = new Alien();
                alien.setPosition(positionX + i * (26 + gap), positionY + j * (20 + gap));
                aliens[i][j] = alien;
            }
        }
    }

    public void update(double time) {
        positionX += velocity * time;

        if(positionX + width > RIGHT_BORDER) {
            velocityDelta++;
            velocity = -100 - velocityDelta;
            positionY += gap;
        }
        else if(positionX < LEFT_BORDER) {
            velocityDelta++;
            velocity = 100 + velocityDelta;
            positionY += gap;
        }


        for (int i = 0; i < COLS; i++) {
            boolean columnAlreadyFire = false;

            // Строки перебираются в обратном порядке для того, чтобы стрелял только последний корабль в строке
            for (int j = ROWS - 1; j >= 0 ; j--) {
                Alien alien = aliens[i][j];

                if(alien != null) {
                    double x = positionX + i * (26 + gap);
                    double y = positionY + j * (20 + gap);
                    alien.setPosition(x, y);

                    alien.update(time);

                    if(!columnAlreadyFire && x > ship.getX() && x < ship.getX() + ship.getWidth()) {
                        if(alien.canFire()) shots.add(alien.fire());
                        columnAlreadyFire = true;
                    }
                }
            }
        }

        shots.stream().forEach(bullet -> {
            bullet.update(time);
        });
    }

    public void render(GraphicsContext gc) {
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                if(aliens[i][j] != null) aliens[i][j].render(gc);
            }
        }

        Iterator<Shot> bulletIter = shots.iterator();

        while(bulletIter.hasNext()) {
            Shot bullet = bulletIter.next();
            bullet.render(gc);

            if (bullet.getY() >= SCREEN_HEIGHT - BORDER) {
                bulletIter.remove();
            }
        }
    }

    public Optional<Alien> intersects(Shot bullet) {
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                Alien alien = aliens[i][j];

                if(alien != null && alien.intersects(bullet)) {
                    aliens[i][j] = null;
                    return Optional.of(alien);
                }
            }
        }

        return Optional.empty();
    }

    public void locate(Ship ship) {
        this.ship = ship;
    }

    public List<Shot> getShots() {
        return shots;
    }
}
