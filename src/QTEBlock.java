import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;


public class QTEBlock extends HBox {
    public StringBuilder qte;
    public ArrayList<Label> sequence = new ArrayList<>();
    QTEBlock(StringBuilder qte) {
        this.qte = qte;
        box();
    }

    private void box() {
        setPadding(new Insets(0, Main.getBlockSize() / 4, 0, Main.getBlockSize() / 4));
        setSpacing(Main.getBlockSize() / 4);
        for (int i = 0; i < qte.length(); i++) {
            Label label = new Label(("" + qte.charAt(i)).toUpperCase());
            label.setFont(new Font(Main.getBlockSize() * 0.8));
            label.setAlignment(Pos.CENTER);
            label.setTextFill(Color.WHITE);
            label.setTranslateX(Runner.getSize());
            label.setPrefSize(Main.getBlockSize(), Main.getBlockSize());
            label.setBackground(
                    new Background(
                            new BackgroundImage(
                                new Image(getClass().getResourceAsStream("0/qte.png"), 0, Main.getBlockSize(), true, false),
                                BackgroundRepeat.REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.DEFAULT,
                                BackgroundSize.DEFAULT
                        )
                    )
            );
            sequence.add(label);
            getChildren().add(label);
        }
    }

    public void check() {
        qte.deleteCharAt(0);
        sequence.remove(0);
        getChildren().remove(0);
    }

    public StringBuilder getQte() {
        return qte;
    }
}
