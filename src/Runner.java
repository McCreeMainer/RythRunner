import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.geometry.Point2D;

public class Runner extends Pane {

    public static boolean ignorePlatform = false;
    public static boolean canJump = true;

    public static int size = Main.block * 3;
    public Point2D position = new Point2D(Main.speed, Main.fall * 2);
    Image run = new Image(getClass().getResourceAsStream("run.png"), 0, size, true, false);
    ImageView imageView = new ImageView(run);

    Runner() {
        new Running(imageView);
        getChildren().addAll(imageView);
    }

    public void moveX(int value) {
        setTranslateX(getTranslateX() + value);
    }

    public void moveY(int value) {
        boolean moveDown = value > 0;
        for (int i = 0; i < Math.abs(value); i++) {
            for (Platform platform : Main.platforms) {
                if (
                        this.getBoundsInParent().intersects(platform.getBoundsInParent()) &&
                        this.getTranslateY() + size == platform.getTranslateY() &&
                        this.getTranslateX() + size / 3 < platform.getTranslateX() + platform.count * Main.block &&
                        !platform.failed
                ) {
                    if (!platform.active) {
                        platform.activate();
                        if (canJump) {
                            platform.fail();
                        }
                    }
                    if (ignorePlatform) {
                        ignorePlatform = false;
                        platform.fail();
                    }
                    if (platform.active){
                        this.setTranslateY(this.getTranslateY() - 1);
                        position = new Point2D(Main.speed, Main.fall * 2);
                        canJump = true;
                        Main.score++;
                    }
                    return;
                }
                else canJump = false;
            }
            this.setTranslateY(this.getTranslateY() + (moveDown ? 1 : -1));
        }
    }

    public void jump() {
        if (canJump) {
            position = position.add(0, -position.getY() - Main.fall);
            canJump = false;
        }
    }

    public void fall() {
        if (canJump) {
            ignorePlatform = true;
            canJump = false;
        }
    }

}
