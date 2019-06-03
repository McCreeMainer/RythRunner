import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class Runner extends Pane {

    public int qte = 0;
    public int highJump = 0;
    public boolean superJump = false;
    public StringBuilder qteCombo = null;

    public static boolean ignorePlatform = false;
    public static boolean canJump = true;

    public static int size = Main.block * 3;
    public Point2D position = new Point2D(Main.speed, Main.fall * 2);
    Label label = new Label();
    Image run = new Image(getClass().getResourceAsStream("map1/run.png"), 0, size, true, false);
    ImageView imageView = new ImageView(run);

    Runner() {
        label.setFont(new Font(Main.block / 3));
        label.setAlignment(Pos.TOP_CENTER);
        label.setTextFill(Color.WHITE);
        label.setTranslateX(Runner.size);
        label.setMinHeight(Main.block / 2);
        label.setBackground(
                new Background(new BackgroundImage(
                        new Image(getClass().getResourceAsStream("map1/qte.png"), 0, Main.block / 2, true, false),
                        BackgroundRepeat.REPEAT,
                        BackgroundRepeat.REPEAT,
                        BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT
                ))
        );
        new Running(imageView);
        getChildren().addAll(imageView, label);
    }

    public void moveX(int value) {
        setTranslateX(getTranslateX() + value);
    }

    public void moveY(int value) {
        boolean moveDown = value > 0;
        for (int i = 0; i < Math.abs(value); i++) {
            for (Platform platform : Main.platforms) {
                if (
                        moveDown &&
                        this.getBoundsInParent().intersects(platform.getBoundsInParent()) &&
                        this.getTranslateY() + size == platform.getTranslateY() &&
                        this.getTranslateX() + Main.block * 1.4 < platform.getTranslateX() + platform.count * Main.block &&
                        !platform.failed
                ) {

                    if (!platform.active) {
                        platform.activate();
                        if (platform.quickTime != null) {
                            qteCombo = new StringBuilder(platform.quickTime);
                            qte = qteCombo.length();
                            label.setText(qteCombo.toString());
                            label.setMinWidth(Main.block / 2 * qteCombo.toString().length());
                        }
                        else {
                            qteCombo = null;
                            qte = 0;
                            label.setText("");
                            label.setMinWidth(0);
                        }
                        superJump = platform.superJump;
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
            if (getTranslateY() >= Main.resolution.height) {
                setTranslateY(-size);
                for (Platform platform : Main.platforms) platform.rollback();
            }
        }
    }

    public void jump() {
        if (canJump) {
            int a = 0;
            if (superJump) {
                a = highJump;
                highJump = 0;
            }
            position = position.add(0, -position.getY() - fallSpeed(a));
            canJump = false;
        }
    }

    public void fall() {
        if (canJump) {
            ignorePlatform = true;
            canJump = false;
        }
    }

    public void quick() {
        qteCombo.deleteCharAt(0);
        qte--;
        highJump++;
        label.setText(qteCombo.toString());
        label.setMinWidth(Main.block / 2 * qteCombo.toString().length());
        if (qte == 0) {
            qteCombo = null;
            label.setText("");
        }
    }

    public int fallSpeed(int x) {
        return (int) (Math.sqrt(Main.block * 8 * (2.5 + x) + 1) - 1) / 2;
    }
}
