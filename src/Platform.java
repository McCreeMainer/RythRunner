import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Platform extends Pane {
    public boolean active = false;
    public boolean failed = false;
    public boolean highJump = false;
    public int count;
    public int height;
    public String quickTime;
    private ImageView imageView;

    Platform (
            int count,
            int height,
            boolean useJump
    ) {
        this.count = count;
        this.height = height;
        paint();
    }

    Platform (
            int count,
            int height,
            boolean useJump,
            String quickTime
    ) {
        this.count = count;
        this.height = height;
        this.quickTime = quickTime;
        paint();
    }

    private void paint() {
        Rectangle rect = new Rectangle(Main.block * count, Main.block);
        setTranslateY(height * Main.block);
        getChildren().add(rect);
    }

    public void activate() {
        this.active = true;
    }

    public void fail() {
        this.failed = true;
    }
}
