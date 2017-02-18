import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.*;
import java.util.ArrayList;

public class View extends Application {

    private Grid g;
    private int numItems;
    private Rectangle check, undo, hint, startOver;
    private Scene scene;
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("GridPane Experiment");
        ArrayList<Box> b = new ArrayList<>();
        g = Grid.read("puz");
        numItems = g.numItems();
        GridPane gp = drawLabels();
        GridPane buttons = getButtons();
        StackPane root = new StackPane(gp,g);
        gp.setAlignment(Pos.CENTER);
        gp.setTranslateX(-40);
        gp.setTranslateY(-50);
        g.setAlignment(Pos.CENTER);
//        g.setTranslateX(30);
//        g.setTranslateY(30);
        root.setAlignment(Pos.CENTER);
        BorderPane pane = new BorderPane();
        pane.setCenter(root);
        pane.setBottom(buttons);
        double xOffset = pane.getWidth()/2;
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        scene = new Scene(pane, primaryScreenBounds.getWidth(),primaryScreenBounds.getHeight());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public GridPane getButtons() {
        GridPane buttons = new GridPane();
//        undo = new Button("Undo");
        undo = new Rectangle(53,25);
        undo.setFill(new ImagePattern(new Image("file:undo.png")));
        undo.setOnMouseEntered(event2);
        undo.setOnMouseExited(event3);
        undo.setOnMouseClicked(event);

        hint = new Rectangle(46,25);
        hint.setFill(new ImagePattern(new Image("file:hint.png")));
        hint.setOnMouseEntered(event2);
        hint.setOnMouseExited(event3);
        hint.setOnMouseClicked(event);

        check = new Rectangle(93,25);
        check.setFill(new ImagePattern(new Image("file:clearerrors.png")));
        check.setOnMouseEntered(event2);
        check.setOnMouseExited(event3);
        check.setOnMouseClicked(event);

        startOver = new Rectangle(83,25);
        startOver.setFill(new ImagePattern(new Image("file:startover.png")));
        startOver.setOnMouseEntered(event2);
        startOver.setOnMouseExited(event3);
//        startOver = new Button("Start Over");
        startOver.setOnMouseClicked(event);

        buttons.add(undo, 0, 0);
        buttons.add(hint, 1,0);
        buttons.add(check, 2,0);
        buttons.add(startOver, 3, 0);
        buttons.setAlignment(Pos.TOP_CENTER);
        buttons.setHgap(50);
        buttons.setTranslateY(-100);
        return buttons;
    }


    public GridPane drawLabels() {
        GridPane gp = new GridPane();
        Rectangle[] r = new Rectangle[numItems*numItems];
        StackPane[] panes = new StackPane[numItems*numItems];
        for (int j = 0; j < numItems*2; j++) {
            r[j] = new Rectangle(90,30);
            r[j].setFill(Color.WHITE);
            r[j].setStroke(Color.BLACK);
            Text text;
            if (j/4 < 1) text = new Text(g.getS()[0].getBoxItemLeft(j%4,0));
            else text = new Text(g.getS()[2].getBoxItemLeft(j%4, 0));
            text.setFont(Font.font(9));
            panes[j] = new StackPane(r[j], text);
            panes[j].setMaxSize(90,30);
            panes[j].setAlignment(Pos.CENTER);
            gp.add(panes[j],0,j+1);
        }
        for (int k = numItems*2; k < numItems*numItems; k++) {
            r[k] = new Rectangle(30, 90);
            r[k].setFill(Color.WHITE);
            r[k].setStroke(Color.BLACK);
            Text text;
            if (k/12 < 1) text = new Text(g.getS()[0].getBoxItemAbove(0, k % 4));
            else text = new Text(g.getS()[1].getBoxItemAbove(0, k % 4));
            text.setRotate(270);
            text.setFont(Font.font(9));
            panes[k] = new StackPane(r[k], text);
            panes[k].setMaxSize(30,90);
            panes[k].setAlignment(Pos.CENTER);
            gp.add(panes[k],(k%8)+1,0);
        }
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(0);
        return gp;
    }



    private EventHandler<MouseEvent> event = mouseEvent -> {
        if (mouseEvent.getSource().equals(check))
            g.checkError();
        else if (mouseEvent.getSource().equals(undo))  // No implementation yet
            System.out.println("Undo");
        else if (mouseEvent.getSource().equals(hint))  // No implementation yet
            System.out.println("Hint");
        else if (mouseEvent.getSource().equals(startOver)) {
            g.startOver();
        }
    };

    private EventHandler<MouseEvent> event2 = mouseEvent -> {
        scene.setCursor(Cursor.HAND);
        if (mouseEvent.getSource().equals(check))
            check.setEffect(new ColorAdjust(0,-.8,0,0));
        else if (mouseEvent.getSource().equals(undo))  // No implementation yet
            undo.setEffect(new ColorAdjust(0,-.8,0,0));
        else if (mouseEvent.getSource().equals(hint))  // No implementation yet
            hint.setEffect(new ColorAdjust(0,-.8,0,0));
        else if (mouseEvent.getSource().equals(startOver))
            startOver.setEffect(new ColorAdjust(0,-.8,0,0));

    };

    private EventHandler<MouseEvent> event3 = mouseEvent -> {
        scene.setCursor(Cursor.DEFAULT);
        if (mouseEvent.getSource().equals(check))
            check.setEffect(new ColorAdjust(0,0,0,0));
        else if (mouseEvent.getSource().equals(undo))  // No implementation yet
            undo.setEffect(new ColorAdjust(0,0,0,0));
        else if (mouseEvent.getSource().equals(hint))  // No implementation yet
            hint.setEffect(new ColorAdjust(0,0,0,0));
        else if (mouseEvent.getSource().equals(startOver))
            startOver.setEffect(new ColorAdjust(0,0,0,0));
    };

}