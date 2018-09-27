package game;

import javafx.collections.ObservableListBase;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;

import static game.Constants.*;

import java.util.*;

public class AlienSwarm extends GridPane {
    private static final int ROWS = 5;
    private static final int COLS = 11;

    private Alien[][] aliens;

    private double velocity = 100;
    private double velocityDelta = 2;
    private double positionX = 144.0;
    private double positionY = 100.0;
    private double gap = 10.0;
    private double rowHeight = gap + 20;

    private Ship ship;
    private List<Shot> shots;

    public AlienSwarm() {
        setTranslateX(positionX);
        setTranslateY(positionY);

        aliens = new Alien[COLS][ROWS];
        shots = new LinkedList<>();
        createAliens();
    }

    private void createAliens() {
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                Alien alien = new Alien();
//                alien.setTranslateX(positionX + i * (26 + gap));
//                alien.setTranslateY(positionY + j * (20 + gap));
                aliens[i][j] = alien;
                add(alien, i, j);
                setMargin(alien, new Insets(gap, gap, gap, gap));
            }
        }
    }

    public void update(double time) {
        if(positionX + getWidth() > RIGHT_BORDER) {
            velocityDelta += 1.5;
            velocity = -100 - velocityDelta;
            positionY += rowHeight;
        }
        else if(positionX < LEFT_BORDER) {
            velocityDelta += 1.5;
            velocity = 100 + velocityDelta;
            positionY += rowHeight;
        }

        setTranslateX(positionX);
        setTranslateY(positionY);

        positionX += velocity * time;


//        for (int i = 0; i < COLS; i++) {
//            boolean columnAlreadyFire = false;
//
//            // Строки перебираются в обратном порядке для того, чтобы стрелял только последний корабль в строке
//            for (int j = ROWS - 1; j >= 0 ; j--) {
//                Alien alien = aliens[i][j];
//
//                if(alien != null) {
////                    double x = positionX + i * (26 + gap);
////                    double y = positionY + j * (20 + gap);
//                    alien.setTranslateX(positionX + i * (26 + gap));
//                    alien.setTranslateY(positionY + j * (20 + gap));
//
////                    alien.update(time);
//
////                    if(!columnAlreadyFire && x > ship.getX() && x < ship.getX() + ship.getWidth()) {
////                        if(alien.canFire()) shots.add(alien.fire());
////                        columnAlreadyFire = true;
////                    }
//                }
//            }
//        }

//        shots.stream().forEach(bullet -> {
//            bullet.update(time);
//        });
    }

//    public void render(GraphicsContext gc) {
//        for (int i = 0; i < COLS; i++) {
//            for (int j = 0; j < ROWS; j++) {
//                if(aliens[i][j] != null) aliens[i][j].render(gc);
//            }
//        }
//
//        Iterator<Shot> bulletIter = shots.iterator();
//
//        while(bulletIter.hasNext()) {
//            Shot bullet = bulletIter.next();
//            bullet.render(gc);
//
//            if (bullet.getY() >= SCREEN_HEIGHT - BORDER) {
//                bulletIter.remove();
//            }
//        }
//    }

    public Optional<Bounds> intersects(Shot bullet) {
        Iterator<Node> it = getChildren().iterator();

        while(it.hasNext()) {
            Node alien = it.next();

            Bounds bounds = getBoundsInParent();
            Bounds alienBounds = alien.getBoundsInParent();
            Bounds absolutAlienBounds = new BoundingBox(
                    bounds.getMinX() + alienBounds.getMinX(),
                    bounds.getMinY() + alienBounds.getMinY(),
                    alienBounds.getWidth(), alienBounds.getHeight());

            if(absolutAlienBounds.intersects(bullet.getBoundsInParent())) {
                it.remove();
                return Optional.of(absolutAlienBounds);
            }
        }
//        for (int i = 0; i < COLS; i++) {
//            for (int j = 0; j < ROWS; j++) {
//                Alien alien = aliens[i][j];
//
//                if(alien != null && alien.intersects(bullet.getLayoutBounds())) {
//                    aliens[i][j] = null;
////                    getChildren().remove(alien);
//                    System.out.println("gotcha");
//                    return Optional.of(alien);
//                }
//            }
//        }

        return Optional.empty();
    }

    public void locate(Ship ship) {
        this.ship = ship;
    }

    public List<Shot> getShots() {
        return shots;
    }
}
