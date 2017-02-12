import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
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

        for (int i = 0; i < g.getS().length; i++) {
            Sub tmp = g.getS()[i];
            gridPane.add(tmp,tmp.getCordX(),tmp.getCordY());
//            root.getChildren().add(g.getS()[i]);
        }
        Scene scene = new Scene(gridPane, 440, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}