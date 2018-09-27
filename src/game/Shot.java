package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Shot extends Pane {
    private boolean alive = true;
    private double velocity;

    public Shot(String fileName, double velocity) {
        this.velocity = velocity;

        Image image = new Image(fileName);
        ImageView imageView = new ImageView(image);
        getChildren().add(imageView);
    }

    public void explode() {
        alive = false;
    }

    public void setPosition(double x, double y) {
        setTranslateX(x);
        setTranslateY(y);
    }

    public void update(double time) {
        setTranslateY(getTranslateY() + velocity * time);
    }
}
