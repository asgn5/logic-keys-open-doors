import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Test extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("GridPane Experiment");


        GridPane gridPane = new GridPane();
        ArrayList<Box> b = new ArrayList<>();
        Grid g = Grid.read("puzzles");
        GridPane gp = new GridPane();
        Rectangle[] r = new Rectangle[16];
        for (int j = 0; j < 8; j++) {
            r[j] = new Rectangle(100,42);
            r[j].setFill(Color.WHITE);
            r[j].setStroke(Color.BLACK);
            gp.add(r[j],0,j+1);
        }
        for (int k = 8; k < 16; k++) {
            r[k] = new Rectangle(42, 100);
            r[k].setFill(Color.WHITE);
            r[k].setStroke(Color.BLACK);
            gp.add(r[k],(k%8)+1,0);
        }

        for (int i = 0; i < g.getS().length; i++) {
            Sub tmp = g.getS()[i];
            gridPane.add(tmp,tmp.getCordX()+1,tmp.getCordY());
        }

        StackPane root = new StackPane(gp, gridPane);
//        FlowPane root = new FlowPane(gp,gridPane);
        Scene scene = new Scene(root, 440, 400);
        gp.setAlignment(Pos.CENTER);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setTranslateX(51);
        gridPane.setTranslateY(50);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}