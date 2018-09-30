package game;

import javafx.collections.ObservableList;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.*;

import static game.Constants.*;

import java.util.*;

public class AlienSwarm extends Pane {
    private static final int ROWS = 5;
    private static final int COLS = 11;

    private double velocity = 100;
    private double velocityDelta = 2;
    private double positionX = 144.0;
    private double positionY = 100.0;
    private double gap = 20.0;
    private double rowHeight = gap;

    private Ship ship;
    private ObservableList<Node> children;


    public AlienSwarm() {
        setTranslateX(positionX);
        setTranslateY(positionY);
        setPrefSize(COLS * (26 + gap) - 26, ROWS * (20 + gap) - 20);

        createAliens();
        children = getChildren();
    }

    private void createAliens() {
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                Alien alien = new Alien();
                alien.setTranslateX(i * (26 + gap));
                alien.setTranslateY(j * (20 + gap));
                getChildren().add(alien);
            }
        }
    }

    public void update(double time) {
        if(positionX + getWidth() > RIGHT_BORDER) {
            velocityDelta += 1.8;
            velocity = -100 - velocityDelta;
            positionY += rowHeight;
        }
        else if(positionX < LEFT_BORDER) {
            velocityDelta += 1.8;
            velocity = 100 + velocityDelta;
            positionY += rowHeight;
        }

        setTranslateX(positionX);
        setTranslateY(positionY);

        positionX += velocity * time;

        children.stream().forEach(node -> {
            Alien alien = (Alien)node;
            alien.reload(time);
        });
    }


    public Optional<Shot> shoot() {
        ListIterator<Node> it = children.listIterator(children.size());

        while(it.hasPrevious()) {
            Alien alien = (Alien)it.previous();
            double x = positionX + alien.getTranslateX();

            // Shoot if alient's absolute X position is between min ship X and max ship X coordinates
            // and if alien is ready to shoot
            // and there is no other aliens in the same column which are on the shooting line
            if(x > ship.getTranslateX() && x < ship.getTranslateX() + ship.getWidth() && alien.canShoot()) {
                if(children.stream().noneMatch(anotherAlien ->
                        alien.getTranslateX() == anotherAlien.getTranslateX() &&
                        alien.getTranslateY() < anotherAlien.getTranslateY())) {
                    return Optional.of(alien.shoot());
                }
            }
        }

        return Optional.empty();
    }

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

        return Optional.empty();
    }

    public void locate(Ship ship) {
        this.ship = ship;
    }
}
