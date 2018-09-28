package game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.*;

import static game.Constants.*;

public class Main extends Application {
    private Ship ship;
    private Score score;
    private LivesMeter meter;
    private AlienSwarm swarm;
    private TopPanel topPanel;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle(TITLE);

        Pane root = new Pane();
        root.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        Image bgImage = new Image("file:assets/background.png");
        ImageView bgImageView = new ImageView(bgImage);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);


//        Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
//        root.getChildren().add(canvas);
//
//        GraphicsContext gc = canvas.getGraphicsContext2D();


        ship = new Ship();
//        score = new Score(gc);
        meter = new LivesMeter();
        swarm = new AlienSwarm();
        swarm.locate(ship);
        topPanel = new TopPanel();

        root.getChildren().addAll(bgImageView, ship, swarm, topPanel);

        List<Shot> bullets = new LinkedList<>();
        List<Explosion> explosions = new LinkedList<>();


        Set<KeyCode> input = new HashSet<>();
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

                ship.reload(elapsedTime);

                if(input.contains(KeyCode.LEFT) && input.contains(KeyCode.SPACE)) {
                    ship.left();
                    if(ship.canFire()) bullets.add(fire(root, ship));
                }
                else if(input.contains(KeyCode.RIGHT) && input.contains(KeyCode.SPACE)) {
                    ship.right();
                    if(ship.canFire()) bullets.add(fire(root, ship));
                }
                else if(input.contains(KeyCode.LEFT)) {
                    ship.left();
                }
                else if(input.contains(KeyCode.RIGHT)) {
                    ship.right();
                }
                else if(input.contains(KeyCode.SPACE)) {
                    if(ship.canFire()) bullets.add(fire(root, ship));
                }
//
                swarm.update(elapsedTime);
//                meter.update(ship.getLives());

                Iterator<Shot> bulletIter = bullets.iterator();

                while(bulletIter.hasNext()) {
                    Shot bullet = bulletIter.next();
                    bullet.update(elapsedTime);

                    if(bullet.getTranslateY() <= 30) {
                        bulletIter.remove();
                        root.getChildren().remove(bullet);
                    }

                    Optional<Bounds> damagedBounds = swarm.intersects(bullet);

                    damagedBounds.ifPresent(bounds -> {
                        Explosion explosion = new Explosion();
                        explosions.add(explosion);
                        root.getChildren().add(explosion);
                        explosion.setPosition(bounds.getMinX() - 3, bounds.getMinY() - 6);
//
                        bullet.explode();
                        bulletIter.remove();
                        root.getChildren().remove(bullet);
                        topPanel.updateScore(10);
                    });
                }

//                Iterator<Shot> alienShotsIter = swarm.getShots().iterator();
//
//                while(alienShotsIter.hasNext()) {
//                    Shot bullet = alienShotsIter.next();
//
//                    if(bullet.getY() <= 30) {
//                        alienShotsIter.remove();
//                    }
//
//                    if(ship.intersects(bullet)) {
//                        ship.die();
//                        Explosion explosion = new Explosion(ship.getX() + 8, ship.getY() + 9);
//                        explosions.add(explosion);
//
//                        bullet.explode();
//                        alienShotsIter.remove();
//                    }
//                }
//
                Iterator<Explosion> explIter = explosions.iterator();

                while(explIter.hasNext()) {
                    Explosion explosion = explIter.next();

                    if(!explosion.isInProgress()) {
                        explIter.remove();
                        root.getChildren().remove(explosion);
                    }
                }

//                if(ship.getLives() == 0) stop();
            }
        }.start();


        primaryStage.show();
    }

    private Shot fire(Pane root, Ship ship) {
        Shot bullet = ship.fire();
        root.getChildren().add(bullet);

        return bullet;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
