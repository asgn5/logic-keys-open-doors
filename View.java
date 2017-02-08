import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 *
 *       NOT INTEGRATED WITH LOGIC YET
 *
 */
public class View extends Application {

    private ArrayList<Rectangle> re = new ArrayList<>();

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("");
        Pane root = new Pane();
        double x = 10.0;
        double y = 10.0;
        double size = 42;
        double[][] co = { {x,y}, {x,y+size}, {x,y+(size*2)}, {x,y+(size*3)} };

        for ( double[] p : co) {
            re.add(new Rectangle(p[0], p[1], size, size));
            for (int i = 0; i < 4; i++) {
                re.add(new Rectangle(p[0] + size, p[1], size, size));
                re.add(new Rectangle(p[0] + (size * 2), p[1], size, size));
                re.add(new Rectangle(p[0] + (size * 3), p[1], size, size));
            }
        }

        for (Rectangle r : re) {
            r.setFill(Color.WHITE);
            r.setStroke(Color.BLACK);
            r.setOnMouseClicked(event);
            root.getChildren().add(r);
        }

        primaryStage.setScene(new Scene(root,400,500,Color.WHITE));
        primaryStage.show();

    }

    private EventHandler<MouseEvent>  event = mouseEvent -> {
        for (Rectangle aRe : re) {
            if (mouseEvent.getSource() == aRe) {
                if (aRe.getFill().equals(Color.WHITE))
                    aRe.setFill(Color.RED);
                else if (aRe.getFill().equals(Color.RED))
                    aRe.setFill(Color.LIMEGREEN);
                else
                    aRe.setFill(Color.WHITE);
            }
        }
    };
}