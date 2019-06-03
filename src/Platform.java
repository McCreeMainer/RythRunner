import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Platform extends Pane {
    public boolean active = false;
    public boolean failed = false;
    public boolean superJump;
    public int count;
    public int height;
    public int distance;
    public String quickTime = null;
    private ImageView platform;

    private Color[] colors = new Color[] {Color.ORANGERED, Color.ORANGE};

    Platform (
            int count,
            int height,
            int distance,
            boolean useJump
    ) {
        this.count = count;
        this.height = height;
        this.distance = distance;
        this.superJump = useJump;
        paint();
    }

    Platform (
            int count,
            int height,
            int distance,
            boolean useJump,
            String quickTime
    ) {
        this.count = count;
        this.height = height;
        this.distance = distance;
        this.superJump = useJump;
        this.quickTime = quickTime;
        paint();
    }

    private void paint() {
//        Rectangle rect = new Rectangle(Main.block * count, Main.block);
        platform = new ImageView();
        platform.setFitWidth(Main.block * count);
        platform.setFitHeight(Main.block);
        Color bg = colors[Maps.number % colors.length];
        Maps.number++;
        setBackground(
                new Background(
                        new BackgroundFill[] {new BackgroundFill(bg, CornerRadii.EMPTY, Insets.EMPTY)},
                        new BackgroundImage[] {new BackgroundImage(
                                new Image(getClass().getResourceAsStream("map1/platform.png"), 0, Main.block, true, false),
                                BackgroundRepeat.REPEAT,
                                BackgroundRepeat.REPEAT,
                                BackgroundPosition.DEFAULT,
                                BackgroundSize.DEFAULT
                        )}
                )
        );
        setTranslateY(height * Main.block);
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
}
