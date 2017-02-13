import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;

public class View extends Application {

    private Grid g;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("GridPane Experiment");

//        GridPane gridPane = new GridPane();
        ArrayList<Box> b = new ArrayList<>();
        g = Grid.read("puz");
        GridPane gp = new GridPane();
        Rectangle[] r = new Rectangle[16];
        StackPane[] panes = new StackPane[16];
        for (int j = 0; j < 8; j++) {
            r[j] = new Rectangle(100,50);
            r[j].setFill(Color.WHITE);
            r[j].setStroke(Color.BLACK);
            Text text;
            if (j/4 < 1) {
                text = new Text(g.getS()[0].getBoxItemLeft(j%4,0));
            } else {
                text = new Text(g.getS()[2].getBoxItemLeft(j%4, 0));
            }
            text.setFont(Font.font(9));
            panes[j] = new StackPane(r[j], text);
            panes[j].setMaxSize(100,50);
            panes[j].setAlignment(Pos.CENTER);
            gp.add(panes[j],0,j+1);
        }
        for (int k = 8; k < 16; k++) {
            r[k] = new Rectangle(50, 100);
            r[k].setFill(Color.WHITE);
            r[k].setStroke(Color.BLACK);
            Text text;
            if (k/12 < 1)
                text = new Text(g.getS()[0].getBoxItemAbove( 0, k%4));
            else
                text = new Text(g.getS()[1].getBoxItemAbove(0, k%4));
            text.setRotate(270);
            text.setFont(Font.font(9));
            panes[k] = new StackPane(r[k], text);
            panes[k].setMaxSize(50,100);
            panes[k].setAlignment(Pos.CENTER);
            gp.add(panes[k],(k%8)+1,0);
        }

//        for (int j = 0; j < 8; j++) {
//            r[j] = new Rectangle(100,42);
//            r[j].setFill(Color.WHITE);
//            r[j].setStroke(Color.BLACK);
//            gp.add(r[j],0,j+1);
//        }
//        for (int k = 8; k < 16; k++) {
//            r[k] = new Rectangle(42, 100);
//            r[k].setFill(Color.WHITE);
//            r[k].setStroke(Color.BLACK);
//            gp.add(r[k],(k%8)+1,0);
//        }
//        for (int i = 0; i < g.getS().length; i++) {
//            Sub tmp = g.getS()[i];
//            gridPane.add(tmp,tmp.getCordX(),tmp.getCordY());
//        }
        Button button = new Button("Check Errors");
        button.setOnMouseClicked(event);
        StackPane root = new StackPane(gp,g, button);
//        button.setAlignment(Pos.);
//        FlowPane root = new FlowPane(g, button);
        Scene scene = new Scene(root, 700,700);

//        Scene scene = new Scene(gridPane, 440, 400);
        gp.setAlignment(Pos.CENTER);
        g.setAlignment(Pos.CENTER);
//        gridPane.setAlignment(Pos.CENTER);
        g.setTranslateX(51);
        g.setTranslateY(50);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private EventHandler<MouseEvent> event = mouseEvent -> {
        g.checkError();
    };

}