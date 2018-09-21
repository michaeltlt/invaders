package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Explosion {
    private static final Image IMAGE = new Image("file:assets/explosion.png");

    private static final int FRAMES    =  16;
    private static final int WIDTH    = 32;
    private static final int HEIGHT   = 32;

    private double positionX;
    private double positionY;

    private int frame;
    private boolean inProgress = true;

    public Explosion(double x, double y) {
        positionX = x;
        positionY = y;
    }

    public void render(GraphicsContext gc)
    {
        if(frame < FRAMES) {
            double spriteX = frame * WIDTH;
            gc.drawImage(IMAGE, spriteX, 0, WIDTH, HEIGHT, positionX, positionY, WIDTH, HEIGHT);
            frame++;
        }
        else {
            inProgress = false;
        }
    }

    public boolean isInProgress() {
        return inProgress;
    }
}
