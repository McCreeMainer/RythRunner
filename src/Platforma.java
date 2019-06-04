import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Platforma extends Pane {
    public boolean active = false;
    public boolean failed = false;
    public boolean superJump;
    public final int length;
    public final int level;
    public final int distance;
    public String quickTime = null;
    public ImageView platform;

    private Color[] colors = new Color[] {Color.ORANGERED, Color.ORANGE};

    Platforma(
            String length,
            String level,
            String distance,
            String useJump
    ) {
        this.length = Integer.parseInt(length);
        this.level = Integer.parseInt(level);
        this.distance = Integer.parseInt(distance);
        this.superJump = Boolean.parseBoolean(useJump);
        paint();
    }

    Platforma(
            String length,
            String level,
            String distance,
            String useJump,
            String quickTime
    ) {
        this.length = Integer.parseInt(length);
        this.level = Integer.parseInt(level);
        this.distance = Integer.parseInt(distance);
        this.superJump = Boolean.parseBoolean(useJump);
        this.quickTime = quickTime;
        paint();
    }

    private void paint() {
        platform = new ImageView();
        platform.setFitWidth(Main.getBlockSize() * length);
        platform.setFitHeight(Main.getBlockSize());
        Color bg = colors[Main.getColorSwitch() % colors.length];
        Main.switchColor();
        setBackground(
                new Background(
                        new BackgroundFill[] {new BackgroundFill(bg, CornerRadii.EMPTY, Insets.EMPTY)},
                        new BackgroundImage[] {new BackgroundImage(
                                new Image(getClass().getResourceAsStream("0/platform.png"), 0, Main.getBlockSize(), true, false),
                                BackgroundRepeat.REPEAT,
                                BackgroundRepeat.REPEAT,
                                BackgroundPosition.DEFAULT,
                                BackgroundSize.DEFAULT
                        )}
                )
        );
        setTranslateY(level * Main.getBlockSize());

        getChildren().add(platform);
    }

    public void activate() {
        this.active = true;
    }

    public void fail() {
        this.failed = true;
    }

    public void rollback() {
        this.failed = false;
    }

    public int getLength() {
        return length;
    }

    public int getDistance() {
        return distance;
    }

    public int getLevel() {
        return level;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isFailed() {
        return failed;
    }

    public String getQTE() {
        return quickTime;
    }

    public boolean isSuperJump() {
        return superJump;
    }
}
