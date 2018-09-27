package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Alien extends Pane {
    private static final String FILE = "file:assets/enemy.png";
    private static final double RELOAD_TIME_SEC = 2;

    private double reloadingTime;
    private boolean alive = true;

    public Alien() {
        Image image = new Image(FILE);
        ImageView imageView = new ImageView(image);
        getChildren().add(imageView);
    }

    public void die() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

//    public Shot fire() {
//        Shot bullet = new Plasma();
//        bullet.setPosition(getX() + getImage().getWidth() / 2, getY());
//
//        return bullet;
//    }

    private void reload(double time) {
        reloadingTime += time;
    }

    public boolean canFire() {
        if(reloadingTime >= RELOAD_TIME_SEC) {
            reloadingTime = 0;
            return true;
        }

        return false;
    }

//    @Override
//    public void update(double time) {
//        super.update(time);
//        reload(time);
//    }
}
