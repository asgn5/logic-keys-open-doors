
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The graphical representation of the puzzle game.
 * Includes a few important logical components
 * which are the Grid and the Controller.
 * Otherwise, nearly all other components are graphical objects
 * with little logic needed to represent them graphically.
 */
public class View extends Application {

    /**
     * The main logical component of the puzzle.
     * Contains a specified number of SubGrids
     * that contain a specified number of boxes.
     */
    private Grid g;

    /**
     * Reference to the scene
     */
    private Scene scene;

    /**
     * Reference to the number of items in each SubGrid
     * useful for scaling the program
     */
    private int numItems;

    /**
     * Controls the creation of the Grid and its logical components
     *   including the necessary SubGrids, the boxes therein
     *   as well as the clues and the background story
     */
    private Controller controller;

    /**
     * Rectangles that act as buttons for
     * check -> check for errors
     * undo -> to undo the last move
     * hint -> to get a hint
     * startOver -> to start puzzle over.
     */
    private Rectangle check, undo, hint, startOver, play;

    private BorderPane mainPane;

    /**
     * Initiates the launch command to build the graphical components
     * and display
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * @param primaryStage The stage of the view
     * @throws IOException If the file is not found
     */
    @Override
    public void start(Stage primaryStage) throws IOException{
        primaryStage.setTitle("GridPane Experiment");
        controller = Controller.read("puzzles.txt"); // if error occurs check that the file is in proper location first.
        mainPane = getMainPane();
        System.gc(); // Garbage collects the arraylists no longer needed after reading data
        BorderPane pane = getIntro();
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        scene = new Scene(getIntro(),primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    /**
     * Creates the initial intro page with description of the game.
     * @return
     */
    public BorderPane getIntro() {
        BorderPane pane = new BorderPane();
        GridPane inner = new GridPane();
        for (int i = 0; i < controller.getIntro().length; i++) {
            Text t = new Text(controller.getIntro()[i]);
            t.setWrappingWidth(400);
            inner.add(t,0,i+1);
        }
        play = new Rectangle(85,50);
        play.setFill(new ImagePattern(new Image("file:play.png")));
        play.setOnMouseClicked(event);
        play.setOnMouseEntered(event2);
        play.setOnMouseExited(event3);
        inner.add(play,0,0);
        inner.setAlignment(Pos.CENTER);
        Rectangle example = new Rectangle(357,372);
        example.setFill(new ImagePattern(new Image("file:example.gif")));
        inner.setMaxWidth(400);
        inner.setVgap(40);
//        inner.setTranslateY(200);
        Text text = new Text("What is a Logic Puzzle?");
        text.setFont(Font.font(20));
        text.setFontSmoothingType(FontSmoothingType.LCD);
        GridPane title = new GridPane();
        title.add(text,0,0);
        title.setAlignment(Pos.CENTER);
        title.setTranslateY(100);
        pane.setTop(title);
        pane.setCenter(inner);
        pane.setRight(example);
        example.setTranslateY(200);
        example.setTranslateX(-100);

        return pane;
    }


    /**
     * @return pane The main panel
     * @throws IOException throws an exception if the file is not found
     * usually happens if the specified file is not in the correct directory in
     * relation to the project files.
     * Some IDEs respect relative paths such as "relative" whereas others require that the file
     * have it's absolute path in the file system
     * for windows, C:\path\to\file
     * for linux, /home/../path/to/file
     * for mac, /Users/../path/to/file
     */
    public BorderPane getMainPane() throws IOException {
        g = controller.getGrid();
        numItems = g.numItems();
        GridPane gp = drawLabels();
        GridPane clueAndBackground = getCluesAndBackground();
        StackPane root = new StackPane(gp, g);
//        gp.setAlignment(Pos.CENTER);
        g.setAlignment(Pos.CENTER);
//        root.setAlignment(Pos.CENTER);
//        g.setAlignment(Pos.CENTER);
//        gp.setAlignment(Pos.CENTER);
        gp.setTranslateX(-61);
        gp.setTranslateY(-61);
        GridPane buttons = getButtons();
        BorderPane pane = new BorderPane();
        pane.setCenter(root);
        pane.setBottom(buttons);
        pane.setLeft(new Rectangle(300,100, Color.TRANSPARENT));
        pane.setRight(clueAndBackground);
        return pane;
    }

    /**
     * @return buttons The GridPane with the control buttons
     */
    public GridPane getButtons() {
        GridPane buttons = new GridPane();
        undo = new Rectangle(53, 25);
        hint = new Rectangle(46, 25);
        check = new Rectangle(93, 25);
        startOver = new Rectangle(83, 25);
        undo.setFill(new ImagePattern(new Image("file:undo.png")));
        hint.setFill(new ImagePattern(new Image("file:hint.png")));
        check.setFill(new ImagePattern(new Image("file:clearerrors.png")));
        startOver.setFill(new ImagePattern(new Image("file:startover.png")));
        Rectangle[] rectangles = {undo, hint, check, startOver};
        for (int i = 0; i < 4; i++) {
            rectangles[i].setOnMouseClicked(event);
            rectangles[i].setOnMouseEntered(event2);
            rectangles[i].setOnMouseExited(event3);
            buttons.add(rectangles[i], i, 0);
        }
        buttons.setAlignment(Pos.TOP_CENTER);
        buttons.setHgap(50);
        buttons.setTranslateY(-100);
        return buttons;
    }

    /**
     * Creates the Labels corresponding to each index of a subgrid
     * @return gp
     */
    public GridPane drawLabels() {
        GridPane gp = new GridPane();
        Rectangle[] r = new Rectangle[numItems * numItems];
        StackPane[] panes = new StackPane[numItems * numItems];
        for (int j = 0; j < numItems * numItems; j++) {
            Label text;
            if ( j < (numItems*numItems)/2) {
                r[j] = new Rectangle(120, 30);
                r[j].setFill(Color.WHITE);
                r[j].setStroke(Color.BLACK);
                if (j / 4 < 1) text = new Label(g.getS()[0].getBoxItemLeft(0,j % 4));
                else text = new Label(g.getS()[2].getBoxItemLeft(0, j%4));
                panes[j] = new StackPane(r[j], text);
                panes[j].setMaxSize(120, 30);
                gp.add(panes[j], 0, j + 1);
            } else {
                r[j] = new Rectangle(30, 120);
                r[j].setFill(Color.WHITE);
                r[j].setStroke(Color.BLACK);
                if (j / ((numItems * 2) + numItems) < 1) text = new Label(g.getS()[0].getBoxItemAbove( j % 4,0));
                else text = new Label(g.getS()[1].getBoxItemAbove(j % 4,0));
                text.setRotate(270);
                text.setAlignment(Pos.BOTTOM_RIGHT);
                panes[j] = new StackPane(r[j], text);
                panes[j].setMaxSize(30, 120);
                gp.add(panes[j], (j % 8) + 1, 0);
            }
//            text.setFont(Font.font(9));

            text.setFont(Font.font(null,FontWeight.BOLD,9));
        }
//        gp.setAlignment(Pos.CENTER);
        gp.setHgap(0);
        gp.setMaxSize(360,360);
        return gp;
    }

    /**
     * Initializes the clues and background story information
     * @return clueBackground The GridPane associated
     */
    public GridPane getCluesAndBackground() {
        GridPane cluePane = new GridPane();
        GridPane backgroundStory = new GridPane();
        String[] clues = controller.getClues();
        String[] background = controller.getBackstory();
        for (int i = 0; i < clues.length; i++) {
            Text text = new Text(clues[i]);
            text.setWrappingWidth(250);
            cluePane.add(text, 0, i);
        }
        for (int j = 0; j < background.length; j++) {
            Text text = new Text(background[j]);
            text.setWrappingWidth(250);
            backgroundStory.add(text,0,j);
        }
        backgroundStory.setVgap(10);
        GridPane clueBackground = new GridPane();
        clueBackground.add(new Text("Active Clues"), 0, 0);
        clueBackground.add(cluePane, 0,1);
        clueBackground.add(backgroundStory, 0,2);
        clueBackground.setVgap(60);
        clueBackground.setTranslateX(-50);
        clueBackground.setTranslateY(100);
        return clueBackground;
    }

    /**
     *  Handles the onMouseClicked
     */
    private EventHandler<MouseEvent> event = mouseEvent -> {
        if (mouseEvent.getSource().equals(check))
            g.checkError();
        else if (mouseEvent.getSource().equals(undo))  // No implementation yet
            System.out.println("Undo");
        else if (mouseEvent.getSource().equals(hint))  // No implementation yet
            System.out.println("Hint");
        else if (mouseEvent.getSource().equals(startOver)) {
            g.startOver();
        } else if (mouseEvent.getSource().equals(play)) {
            scene.setRoot(mainPane);
        }
    };

    /**
     *  Handles the onMouseEntered
     *  Changes a buttons color to grey
     */
    private EventHandler<MouseEvent> event2 = mouseEvent -> {
        scene.setCursor(Cursor.HAND);
        if (mouseEvent.getSource().equals(check))
            check.setEffect(new ColorAdjust(0, -.8, 0, 0));
        else if (mouseEvent.getSource().equals(undo))
            undo.setEffect(new ColorAdjust(0, -.8, 0, 0));
        else if (mouseEvent.getSource().equals(hint))
            hint.setEffect(new ColorAdjust(0, -.8, 0, 0));
        else if (mouseEvent.getSource().equals(startOver))
            startOver.setEffect(new ColorAdjust(0, -.8, 0, 0));
        else if (mouseEvent.getSource().equals(play))
            play.setEffect(new ColorAdjust(1,0,0,0));
    };

    /**
     *  Handles the onMouseExited
     *  Changes a buttons color to default value
     */
    private EventHandler<MouseEvent> event3 = mouseEvent -> {
        scene.setCursor(Cursor.DEFAULT);
        if (mouseEvent.getSource().equals(check))
            check.setEffect(new ColorAdjust(0, 0, 0, 0));
        else if (mouseEvent.getSource().equals(undo))  // No implementation yet
            undo.setEffect(new ColorAdjust(0, 0, 0, 0));
        else if (mouseEvent.getSource().equals(hint))  // No implementation yet
            hint.setEffect(new ColorAdjust(0, 0, 0, 0));
        else if (mouseEvent.getSource().equals(startOver))
            startOver.setEffect(new ColorAdjust(0, 0, 0, 0));
        else if (mouseEvent.getSource().equals(play))
            play.setEffect(new ColorAdjust(0, 0, 0, 0));
    };

}












//        for (int j = 0; j < numItems * 2; j++) {
//            r[j] = new Rectangle(120, 30);
//            r[j].setFill(Color.WHITE);
//            r[j].setStroke(Color.BLACK);
//            Label text;
//            if (j / 4 < 1) text = new Label(g.getS()[0].getBoxItemLeft(j % 4, 0));
//            else text = new Label(g.getS()[2].getBoxItemLeft(j % 4, 0));
//            text.setFont(Font.font(9));
//            panes[j] = new StackPane(r[j], text);
//            panes[j].setMaxSize(120, 30);
//            gp.add(panes[j], 0, j + 1);
//        }
//        for (int k = numItems * 2; k < numItems * numItems; k++) {
//            r[k] = new Rectangle(30, 120);
//            r[k].setFill(Color.WHITE);
//            r[k].setStroke(Color.BLACK);
//            Label text;
//            if (k / 12 < 1) text = new Label(g.getS()[0].getBoxItemAbove(0, k % 4));
//            else text = new Label(g.getS()[1].getBoxItemAbove(0, k % 4));
//            text.setRotate(270);
//            text.setFont(Font.font(9));
//            panes[k] = new StackPane(r[k], text);
//            panes[k].setMaxSize(30, 120);

//        }
