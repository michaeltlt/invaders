package game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Canvas Example");

        Group root = new Group();
        Scene theScene = new Scene( root );
        primaryStage.setScene( theScene );

        Canvas canvas = new Canvas(800, 600);
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image earth = new Image("file:assets/background.png");

        Ship ship = new Ship();
        ship.setPosition(canvas.getWidth() / 2 - 24, canvas.getHeight() - 60);

        AlienSwarm swarm = new AlienSwarm();
        List<Bullet> bullets = new LinkedList<>();


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
                // calculate time since last update.
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
                bullets.stream().forEach(bullet -> {
                    bullet.update(elapsedTime);
                    bullet.render(gc);
                });

                // collision detection
//                bullets.stream().forEach(bullet -> {
//                    if(bullet.getY() <= 30) {
//                        bullets.remove(bullet);
//                    }
//                });

                Iterator<Bullet> bulletIter = bullets.iterator();

                while(bulletIter.hasNext()) {
                    Bullet bullet = bulletIter.next();

                    if(bullet.getY() <= 30) {
                        bulletIter.remove();
                    }

                    if(swarm.intersects(bullet)) {
                        bullet.explode();
                        System.out.println("Gotcha!");
                        bulletIter.remove();
                    }
                }


                ship.render(gc);
                swarm.render(gc);
            }
        }.start();


        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
