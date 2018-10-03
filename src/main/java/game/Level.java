package game;

import game.entities.AlienSwarm;
import game.entities.Explosion;
import game.entities.Shot;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;

import java.io.IOException;
import java.util.*;

import static game.Constants.*;

public class Level {
    private Scene scene;

    private Ship ship;
    private AlienSwarm swarm;

    private List<Shot> shipShots;
    private List<Shot> swarmShots;
    private List<Explosion> explosions;

    private ObservableList<Node> nodes;
    private Pane root;

    private AudioClip backgroundMusic;
    private KeysHandler keysHandler;
    private LevelController controller;

    private boolean done;


    public Level() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/level.fxml"));

        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        scene = new Scene(root);
        nodes = root.getChildren();
        controller = loader.getController();

        shipShots = new LinkedList<>();
        swarmShots = new LinkedList<>();
        explosions = new LinkedList<>();

        reset();
    }

    public void reset() {
        done = false;

        controller.reset();

        if(!shipShots.isEmpty() || !swarmShots.isEmpty() || !explosions.isEmpty()) {
            clearChildren();
        }

        if(nodes.contains(ship)) nodes.remove(ship);
        if(nodes.contains(swarm)) nodes.remove(swarm);

        keysHandler = new KeysHandler(KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE);

        scene.setOnKeyPressed(keysHandler.pressedHandler());
        scene.setOnKeyReleased(keysHandler.releasedHandler());

        init();
    }

    private void init() {
        ship = new Ship();
        swarm = new AlienSwarm();
        swarm.locate(ship);

        initSound();

        nodes.addAll(ship, swarm);
    }

    private void initSound() {
        backgroundMusic = new AudioClip("jar:" + LEVEL_BG_SOUND_URL.getPath());
        backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
        backgroundMusic.play(0.7);
    }

    private void clearChildren() {
        nodes.removeAll(swarmShots);
        nodes.removeAll(shipShots);
        nodes.removeAll(explosions);

        swarmShots.clear();
        shipShots.clear();
        explosions.clear();
    }

    public void update(double elapsedTime) {
        handleKeys();

        ship.reload(elapsedTime);
        swarm.update(elapsedTime);

        swarmShoot();

        handleBullets(elapsedTime);
        handleSwarmShots(elapsedTime);
        handleExplosions();

        checkFinished();
    }

    private void swarmShoot() {
        Optional<Shot> alienShot = swarm.shoot();

        if (alienShot.isPresent()) {
            nodes.add(alienShot.get());
            swarmShots.add(alienShot.get());
        }
    }

    private void handleBullets(double elapsedTime) {
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
                explosion.setPosition(bounds.getMinX() - 19, bounds.getMinY() - 23);

                bullet.explode();
                bulletIter.remove();
                nodes.remove(bullet);
                controller.updateScore(10);
            });
        }
    }

    private void handleSwarmShots(double elapsedTime) {
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
                controller.decreaseLives();
                Explosion explosion = new Explosion();
                explosion.setPosition(ship.getTranslateX() - 8, ship.getTranslateY() - 6);
                explosions.add(explosion);
                nodes.add(explosion);

                bullet.explode();
                swarmShotsIter.remove();
                nodes.remove(bullet);
            }
        }
    }

    private void handleExplosions() {
        Iterator<Explosion> explIter = explosions.iterator();

        while(explIter.hasNext()) {
            Explosion explosion = explIter.next();

            if(!explosion.isInProgress()) {
                explIter.remove();
                nodes.remove(explosion);
            }
        }
    }

    private void checkFinished() {
        if(ship.getLives() == 0 || swarm.getSurvivedAliens() == 0) {
            nodes.removeAll(ship, swarm);
            nodes.removeAll(shipShots);
            nodes.removeAll(swarmShots);
            done = true;

            backgroundMusic.stop();
        }
    }

    public Scene getScene() {
        return scene;
    }

    public boolean isDone() {
        return done;
    }

    private void handleKeys() {
        if(keysHandler.isPressed(KeyCode.LEFT) && keysHandler.isPressed(KeyCode.SPACE)) {
            ship.left();
            if(ship.canFire()) shipShots.add(shoot(root, ship));
        }
        else if(keysHandler.isPressed(KeyCode.RIGHT) && keysHandler.isPressed(KeyCode.SPACE)) {
            ship.right();
            if(ship.canFire()) shipShots.add(shoot(root, ship));
        }
        else if(keysHandler.isPressed(KeyCode.LEFT)) {
            ship.left();
        }
        else if(keysHandler.isPressed(KeyCode.RIGHT)) {
            ship.right();
        }
        else if(keysHandler.isPressed(KeyCode.SPACE)) {
            if(ship.canFire()) shipShots.add(shoot(root, ship));
        }
    }

    private Shot shoot(Pane root, Ship ship) {
        Shot bullet = ship.shoot();
        root.getChildren().add(bullet);

        return bullet;
    }

    public int getScore() {
        return controller.getScore();
    }
}
