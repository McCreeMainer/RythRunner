import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Running extends Transition {
    private final int count = 4;
    private final int columns = 4;
    private final Duration duration = Duration.millis(500);
    private ImageView imageView;

    Running(ImageView imageView) {
        this.imageView = imageView;
        imageView.setFitHeight(Runner.size);
        imageView.setFitWidth(Runner.size);
        setCycleDuration(duration);
        setCycleCount(Animation.INDEFINITE);
        setInterpolator(Interpolator.LINEAR);
        this.imageView.setViewport(new Rectangle2D(0, 0, Runner.size, Runner.size));
        play();
    }
    protected void interpolate(double f) {
        final int index = Math.min((int) Math.floor(f * count), count - 1);
        int x;
        if (!Runner.canJump) {
            x = columns - 1;
        }
        else if (index < columns - 1) x = index;
        else x = columns - index % (columns - 2) - 2;
        imageView.setViewport(new Rectangle2D(x * (int) imageView.getFitWidth(), 0, Runner.size, Runner.size));
    }


}
