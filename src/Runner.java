import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.geometry.Point2D;


public class Runner extends Pane {
    private static int size = Main.getBlockSize() * 3;
    private static boolean canJump = true;

    private int highJump = 0;
    private boolean superJump = false;
    private boolean ignorePlatform = false;

    private Point2D position = new Point2D(Main.getSpeed(), fallSpeed(0) * 2);
    private QTEBlock qte = null;
    private Image run = new Image(getClass().getResourceAsStream("0/run.png"), 0, size, true, false);
    private ImageView imageView = new ImageView(run);

    Runner() {
        new Running(imageView);
        getChildren().add(imageView);
    }

    public void moveX(int value) {
        setTranslateX(getTranslateX() + value);
    }

    public void moveY(int value) {
        boolean moveDown = value > 0;
        for (int i = 0; i < Math.abs(value); i++) {
            for (Platforma platforma : Main.getPlatforms()) {
                if (
                        moveDown &&
                        this.getBoundsInParent().intersects(platforma.getBoundsInParent()) &&
                        this.getTranslateY() + size == platforma.getTranslateY() &&
                        this.getTranslateX() + Main.getBlockSize() * 1.4 < platforma.getTranslateX() + platforma.getLength() * Main.getBlockSize() &&
                        !platforma.isFailed()
                ) {

                    if (!platforma.isActive()) {
                        platforma.activate();
                        if (platforma.getQTE() != null) {
                            qte = new QTEBlock(new StringBuilder(platforma.getQTE()));
                            getChildren().add(qte);
                        }
                        else {
                            if (qte != null) {
                                getChildren().remove(qte);
                                qte = null;
                            }
                        }
                        superJump = platforma.isSuperJump();
                    }
                    if (ignorePlatform) {
                        ignorePlatform = false;
                        platforma.fail();
                    }
                    if (platforma.isActive()){
                        this.setTranslateY(this.getTranslateY() - 1);
                        position = new Point2D(Main.getSpeed(), fallSpeed(0) * 2);
                        canJump = true;
                        Main.scor();
                    }
                    return;
                }
                else canJump = false;
            }
            this.setTranslateY(this.getTranslateY() + (moveDown ? 1 : -1));
            if (getTranslateY() >= Main.getResolution().height) {
                setTranslateY(-size);
                for (Platforma platforma : Main.getPlatforms()) platforma.rollback();
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

    public void quick(String key) {
        if (qte != null) {
            if (key.charAt(0) == qte.getQte().charAt(0)) {
                qte.check();
            }
            highJump++;
            if (qte.getQte().length() == 0) {
                getChildren().remove(qte);
                qte = null;
            }
        }
    }

    public Point2D getPosition() {
        return position;
    }

    public void addPosition() {
        position = position.add(0, 1);
    }

    public static int getSize() {
        return size;
    }

    public static boolean getJump() {
        return canJump;
    }

    public int fallSpeed(int x) {
        return (int) (Math.sqrt(Main.getBlockSize() * 8 * (2.5 + x) + 1) - 1) / 2;
    }
}
