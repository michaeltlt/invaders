package game;

import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.util.*;

import static game.Constants.BG_IMAGE;
import static game.Constants.SCREEN_HEIGHT;
import static game.Constants.SCREEN_WIDTH;

public class Level {
    private Scene scene;
    private Ship ship;
    private AlienSwarm swarm;
    private TopPanel topPanel;
    private List<Shot> shipShots;
    private List<Shot> swarmShots;
    private List<Explosion> explosions;
    private Set<KeyCode> input;
    private ObservableList<Node> nodes;
    private Pane root;

    private boolean done;


    public Level() {
        root = new Pane();
        scene = new Scene(root);

        init();

        swarmShots = new LinkedList<>();
        explosions = new LinkedList<>();

        input = new HashSet<>();
        Set<KeyCode> validCodes = new HashSet<>();
        Collections.addAll(validCodes, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE);

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();

            if(validCodes.contains(code) && !input.contains(code)) {
                input.add(code);
            }
        });

        scene.setOnKeyReleased(event -> input.remove(event.getCode()));
    }

    public void update(double elapsedTime) {
        handleKeys();

        ship.reload(elapsedTime);
        swarm.update(elapsedTime);

        Optional<Shot> alienShot = swarm.shoot();

        if(alienShot.isPresent()) {
            nodes.add(alienShot.get());
            swarmShots.add(alienShot.get());
        }

        Iterator<Shot> bulletIter = shipShots.iterator();

        while(bulletIter.hasNext()) {
            Shot bullet = bulletIter.next();
            bullet.update(elapsedTime);

            if(bullet.getTranslateY() <= 30) {
                bulletIter.remove();
                nodes.remove(bullet);
            }

            Optional<Bounds> damagedBounds = swarm.intersects(bullet);

            damagedBounds.ifPresent(bounds -> {
                Explosion explosion = new Explosion();
                explosions.add(explosion);
                nodes.add(explosion);
                explosion.setPosition(bounds.getMinX() - 3, bounds.getMinY() - 6);
//
                bullet.explode();
                bulletIter.remove();
                nodes.remove(bullet);
                topPanel.updateScore(10);
            });
        }

        Iterator<Shot> swarmShotsIter = swarmShots.iterator();

        while(swarmShotsIter.hasNext()) {
            Shot bullet = swarmShotsIter.next();

            bullet.update(elapsedTime);

            if(bullet.getTranslateY() >= SCREEN_HEIGHT) {
                swarmShotsIter.remove();
                nodes.remove(bullet);
            }

            if(ship.getBoundsInParent().intersects(bullet.getBoundsInParent())) {
                ship.die();
                topPanel.decreaseLives();
                Explosion explosion = new Explosion();
                explosion.setPosition(ship.getTranslateX() + 8, ship.getTranslateY() + 9);
                explosions.add(explosion);
                nodes.add(explosion);

                bullet.explode();
                swarmShotsIter.remove();
                nodes.remove(bullet);
            }
        }

        Iterator<Explosion> explIter = explosions.iterator();

        while(explIter.hasNext()) {
            Explosion explosion = explIter.next();

            if(!explosion.isInProgress()) {
                explIter.remove();
                nodes.remove(explosion);
            }
        }

        if(ship.getLives() == 0) {
            nodes.removeAll(ship, swarm);
            nodes.removeAll(shipShots);
            nodes.removeAll(swarmShots);
            done = true;
//                    printGameOver(root);
//            stop();
        }
    }

    public Scene getScene() {
        return scene;
    }

    public boolean isDone() {
        return done;
    }

    private void init() {
        nodes = root.getChildren();
        nodes.clear();
        root.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        ship = new Ship();
        swarm = new AlienSwarm();
        swarm.locate(ship);
        topPanel = new TopPanel();
        shipShots = new LinkedList<>();

        setBackground();

        nodes.addAll(ship, swarm, topPanel);
    }

    private void setBackground() {
        ImageView imageView = new ImageView(BG_IMAGE);
        root.getChildren().add(imageView);
    }

    private void handleKeys() {
        if(input.contains(KeyCode.LEFT) && input.contains(KeyCode.SPACE)) {
            ship.left();
            if(ship.canFire()) shipShots.add(shoot(root, ship));
        }
        else if(input.contains(KeyCode.RIGHT) && input.contains(KeyCode.SPACE)) {
            ship.right();
            if(ship.canFire()) shipShots.add(shoot(root, ship));
        }
        else if(input.contains(KeyCode.LEFT)) {
            ship.left();
        }
        else if(input.contains(KeyCode.RIGHT)) {
            ship.right();
        }
        else if(input.contains(KeyCode.SPACE)) {
            if(ship.canFire()) shipShots.add(shoot(root, ship));
        }
    }

    private Shot shoot(Pane root, Ship ship) {
        Shot bullet = ship.shoot();
        root.getChildren().add(bullet);

        return bullet;
    }

    public int getScore() {
        return topPanel.getScore();
    }
}
