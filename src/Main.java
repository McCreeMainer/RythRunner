import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//import javafx.fxml.FXMLLoader;

public class Main extends Application {
    public static Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
    public static int blockH = Mario.blockH;
    public static int block = resolution.height / blockH;
    public static int speed = Mario.speed;
    public static Platform[] platforms = Mario.platforms;

    public static int fall = (int) (Math.sqrt(block * 20 + 1) - 1) / 2;

    private int space = 0;
    public static int score = 0;
    public Label scoreLabel = new Label("Score: " + score);
    public static Pane appRoot = new Pane();
    public static Pane gameRoot = new Pane();


    Runner runner = new Runner();

    public Parent content() {
        gameRoot.setPrefSize(resolution.width, resolution.height);
        for (Platform platform : platforms) {
            platform.setTranslateX(space * block);
            gameRoot.getChildren().add(platform);
            space += platform.count;
        }
        space -= platforms[platforms.length - 1].count;
        runner.setTranslateY((platforms[0].height - 3) * block);
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
        scoreLabel.setText("Score: " + score);

        runner.translateXProperty().addListener((ovs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > block * 13 && offset < block * space - runner.size) gameRoot.setLayoutX(block * 13 - offset);
        });
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
