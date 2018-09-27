package game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.*;

import static game.Constants.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle(TITLE);

        Group root = new Group();
        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);

        Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image earth = new Image("file:assets/background.png");

        Ship ship = new Ship();

        Score score = new Score(gc);
        LivesMeter meter = new LivesMeter();

        AlienSwarm swarm = new AlienSwarm();
        swarm.locate(ship);

        List<Shot> bullets = new LinkedList<>();
        List<Explosion> explosions = new LinkedList<>();


        Set<KeyCode> input = new HashSet<>();
        Set<KeyCode> validCodes = new HashSet<>();
        Collections.addAll(validCodes, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE);


        theScene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();

            if(validCodes.contains(code) && !input.contains(code)) {
                input.add(code);
            }
        });

        theScene.setOnKeyReleased(event -> input.remove(event.getCode()));

        new AnimationTimer() {
            long lastNanoTime = System.nanoTime();

            @Override
            public void handle(long currentNanoTime) {
                // Calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime) / 1_000_000_000.0;
                lastNanoTime = currentNanoTime;

                ship.reload(elapsedTime);

                gc.drawImage(earth, 0, 0);

                ship.stop();

                if(input.contains(KeyCode.LEFT) && input.contains(KeyCode.SPACE)) {
                    ship.left();
                    if(ship.canFire()) bullets.add(ship.fire());
                }
                else if(input.contains(KeyCode.RIGHT) && input.contains(KeyCode.SPACE)) {
                    ship.right();
                    if(ship.canFire()) bullets.add(ship.fire());
                }
                else if(input.contains(KeyCode.LEFT)) {
                    ship.left();
                }
                else if(input.contains(KeyCode.RIGHT)) {
                    ship.right();
                }
                else if(input.contains(KeyCode.SPACE)) {
                    if(ship.canFire()) bullets.add(ship.fire());
                }

                ship.update(elapsedTime);
                swarm.update(elapsedTime);
                meter.update(ship.getLives());

                bullets.stream().forEach(bullet -> {
                    bullet.update(elapsedTime);
                    bullet.render(gc);
                });

                Iterator<Shot> bulletIter = bullets.iterator();

                while(bulletIter.hasNext()) {
                    Shot bullet = bulletIter.next();

                    if(bullet.getY() <= 30) {
                        bulletIter.remove();
                    }

                    Optional<Alien> damagedAlien = swarm.intersects(bullet);

                    damagedAlien.ifPresent(alien -> {
                        Explosion explosion = new Explosion(alien.getX() - 3, alien.getY() - 6);
                        explosions.add(explosion);

                        bullet.explode();
                        bulletIter.remove();
                        score.increase(10);
                    });
                }

                Iterator<Shot> alienShotsIter = swarm.getShots().iterator();

                while(alienShotsIter.hasNext()) {
                    Shot bullet = alienShotsIter.next();

                    if(bullet.getY() <= 30) {
                        alienShotsIter.remove();
                    }

                    if(ship.intersects(bullet)) {
                        ship.die();
                        Explosion explosion = new Explosion(ship.getX() + 3, ship.getY() + 6);
                        explosions.add(explosion);

                        bullet.explode();
                        alienShotsIter.remove();
                    }
                }

                ship.render(gc);
                swarm.render(gc);
                score.render(gc);
                meter.render(gc);

                Iterator<Explosion> explIter = explosions.iterator();

                while(explIter.hasNext()) {
                    Explosion explosion = explIter.next();

                    if(explosion.isInProgress()) {
                        explosion.render(gc);
                    }
                    else {
                        explIter.remove();
                    }
                }
            }
        }.start();


        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
