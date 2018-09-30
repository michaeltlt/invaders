package game;

import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.*;

import static game.Constants.SCREEN_HEIGHT;
import static game.Constants.SCREEN_WIDTH;

public class Level {
    private Scene scene;
    private Ship ship;
    private LivesMeter meter;
    private AlienSwarm swarm;
    private TopPanel topPanel;
    private List<Shot> shipShots;
    private List<Shot> swarmShots;
    private List<Explosion> explosions;
    private Set<KeyCode> input;
    private ObservableList<Node> nodes;

    public Level() {
        Pane root = new Pane();
        prepareLevel(root);

        scene = new Scene(root);

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

        new AnimationTimer() {
            long lastNanoTime = System.nanoTime();

            @Override
            public void handle(long currentNanoTime) {
                // Calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime) / 1_000_000_000.0;
                lastNanoTime = currentNanoTime;

                handleKeys(root);

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

                    printGameOver(root);
                    stop();
                }
            }
        }.start();
    }

    public Scene getScene() {
        return scene;
    }

    private void prepareLevel(Pane root) {
        nodes = root.getChildren();
        nodes.clear();
        root.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        ship = new Ship();
        meter = new LivesMeter();
        swarm = new AlienSwarm();
        swarm.locate(ship);
        topPanel = new TopPanel();
        shipShots = new LinkedList<>();

        setBackground(root);

        nodes.addAll(ship, swarm, topPanel);
    }

    private void setBackground(Pane root) {
        Image bgImage = new Image("file:assets/background.png");
        ImageView imageView = new ImageView(bgImage);
        root.getChildren().add(imageView);
    }

    private void handleKeys(Pane root) {
        if(input.contains(KeyCode.LEFT) && input.contains(KeyCode.SPACE)) {
            ship.left();
            if(ship.canFire()) shipShots.add(fire(root, ship));
        }
        else if(input.contains(KeyCode.RIGHT) && input.contains(KeyCode.SPACE)) {
            ship.right();
            if(ship.canFire()) shipShots.add(fire(root, ship));
        }
        else if(input.contains(KeyCode.LEFT)) {
            ship.left();
        }
        else if(input.contains(KeyCode.RIGHT)) {
            ship.right();
        }
        else if(input.contains(KeyCode.SPACE)) {
            if(ship.canFire()) shipShots.add(fire(root, ship));
        }
    }

    private Shot fire(Pane root, Ship ship) {
        Shot bullet = ship.shoot();
        root.getChildren().add(bullet);

        return bullet;
    }

    private void printGameOver(Pane root) {
        Label label = new Label();
        label.setTextFill(Color.WHITE);
        Font font = Font.font("Courier New", FontWeight.BOLD, 54);
        label.setFont(font);
        label.setText("GAME OVER");
        label.setTranslateX(SCREEN_WIDTH / 2 - 150);
        label.setTranslateY(SCREEN_HEIGHT / 2 - 40);

        root.getChildren().add(label);
    }
}
