import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//import javafx.fxml.FXMLLoader;

public class Main extends Application {
    private static Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
    private static int blockSize = resolution.height / Maps.properties[0][0];
    private static int speed = blockSize / Maps.properties[0][1];
    private static final String[][] maps = Maps.maps[0];
    private static final ArrayList<Platforma> platforms = new ArrayList<>();

    private static int colorSwitch = 0;
    private static int score = 0;
    private static final int FALL = (int) (Math.sqrt(blockSize * 20 + 1) - 1) / 2;

    private int space = 0;
    private int end;
    private int last = 0;
    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();

    public Label scoreLabel = new Label("Score: " + score);
    public Runner runner = new Runner();

    public Parent content() {
        Image bg = new Image(getClass().getResourceAsStream("0/bg.png"), 0, resolution.height, true, false);
        gameRoot.prefHeightProperty().bind(appRoot.heightProperty());
        for (String[] input : maps) {
            Platforma platforma;
            if (input.length == 5) {
                platforma = new Platforma(input[0], input[1], input[2], input[3], input[4]);
            }
            else {
                platforma = new Platforma(input[0], input[1], input[2], input[3]);
            }
            platforma.setTranslateX(space * blockSize);
            platforma.setTranslateY(platforma.getLevel() * blockSize);
            if (last == 0) {
                runner.setTranslateY((platforma.getLevel() - 3) * blockSize - 1);
            }
            gameRoot.getChildren().add(platforma);
            platforms.add(platforma);
            last = platforma.getLength() + platforma.getDistance();
            space += platforma.getLength() + platforma.getDistance();
        }
        end = space;

        for (int i = 0; i < Math.ceil((double) end * blockSize / bg.getWidth()); i++) {
            ImageView sas = new ImageView(bg);
            sas.setTranslateX(i * bg.getWidth());
            gameRoot.getChildren().add(0, sas);
        }



        scoreLabel.setFont(new Font(blockSize * 2));
        scoreLabel.setAlignment(Pos.BASELINE_RIGHT);
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setPrefSize(resolution.width, blockSize * 2);
        scoreLabel.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        gameRoot.getChildren().add(runner);
        appRoot.getChildren().addAll(gameRoot, scoreLabel);
        return appRoot;
    }

    public void update() {
        if (runner.getPosition().getY() < FALL * 2) {
            runner.addPosition();
        }
        runner.moveX((int) runner.getPosition().getX());
        runner.moveY((int) runner.getPosition().getY());
        scoreLabel.setText("Score: " + score);

        runner.translateXProperty().addListener((ovs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset >= blockSize * (Maps.properties[0][0] / 2) && offset < blockSize * (space - last - 3)) {
                gameRoot.setLayoutX(blockSize * (Maps.properties[0][0] / 2) - offset);
            }
            if (offset > end * blockSize) Platform.exit();
        });
    }

    public static Dimension getResolution() {
        return resolution;
    }

    public static int getColorSwitch() {
        return colorSwitch;
    }

    public static void switchColor() {
        colorSwitch++;
    }

    public static int getBlockSize() {
        return blockSize;
    }

    public static int getSpeed() {
        return speed;
    }

    public static ArrayList<Platforma> getPlatforms() {
        return platforms;
    }

    public static void scor() {
        score++;
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
                runner.quick(key.getCharacter());
            }
        });
        primaryStage.setTitle("RythRunner");
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
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
