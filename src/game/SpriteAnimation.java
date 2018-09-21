package game;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {

    private final GraphicsContext gc;
    private final Image image;
    private final int count;
    private final int columns;
    private final int offsetX;
    private final int offsetY;
    private final int width;
    private final int height;
    private double dstX;
    private double dstY;

    private int lastIndex;

    public SpriteAnimation(
            GraphicsContext gc,
            Image image,
            Duration duration,
            int count,   int columns,
            int offsetX, int offsetY,
            int width,   int height,
            double dstX,
            double dstY) {
        this.gc = gc;
        this.image = image;
        this.count     = count;
        this.columns   = columns;
        this.offsetX   = offsetX;
        this.offsetY   = offsetY;
        this.width     = width;
        this.height    = height;
        this.dstX = dstX;
        this.dstY = dstY;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    protected void interpolate(double k) {
        final int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex) {
            final int spriteX = (index % columns) * width  + offsetX;
            final int spriteY = (index / columns) * height + offsetY;
//            imageView.setViewport(new Rectangle2D(x, y, width, height));
            gc.drawImage(image, spriteX, spriteY, width, height, dstX, dstY, width, height);
            lastIndex = index;
        }
    }
}