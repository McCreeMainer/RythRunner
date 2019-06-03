import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//import javafx.fxml.FXMLLoader;

public class Main extends Application {
    public static Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
    public static int blockH = 25;
    public static int block = resolution.height / blockH;
    public static int speed = 7;
    public static Platform[] platforms = Maps.MARIO;
    public static int fall = (int) (Math.sqrt(block * 20 + 1) - 1) / 2;
    private int space = 0;
    public int combo = 1;
    public static int score = 0;
    public Label scoreLabel = new Label("Score: " + score * combo);
    public static Pane appRoot = new Pane();
    public static Pane gameRoot = new Pane();

    Runner runner = new Runner();

    public Parent content() {
        gameRoot.setPrefSize(resolution.width, resolution.height);
        for (Platform platform : platforms) {
            platform.setTranslateX(space * block);
            gameRoot.getChildren().add(platform);
            space += platform.count + platform.distance;
        }
        space -= platforms[platforms.length - 1].count;
        runner.setTranslateY((platforms[0].height - 3) * block - 1);
        gameRoot.getChildren().add(runner);
        appRoot.getChildren().add(gameRoot);
        return appRoot;
    }

    public void update() {
        if (runner.position.getY() < Main.fall * 2) {
            runner.position = runner.position.add(0, 1);
        }
        runner.moveX((int) runner.position.getX());
        runner.moveY((int) runner.position.getY());
        scoreLabel.setText("Score: " + score * combo);

        runner.translateXProperty().addListener((ovs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > block * 13 && offset < block * space - runner.size) gameRoot.setLayoutX(block * 13 - offset);
        });
    }

    public void pressToShoon(String key) {
        if (runner.qteCombo != null) {
            if (key.charAt(0) == runner.qteCombo.charAt(0)) {
                runner.quick();
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Scene scene = new Scene(content());
        scene.setOnMouseClicked(event-> {
            if (event.getButton() == MouseButton.PRIMARY) {
                runner.jump();
            } else if (event.getButton() == MouseButton.SECONDARY) {
                runner.fall();
            }
        });
        scene.setOnKeyTyped(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent key) {
                pressToShoon(key.getCharacter());
            }
        });
        primaryStage.setTitle("RythRunner");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setMaximized(true);
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
