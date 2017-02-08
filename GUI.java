import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GUI extends Application {
    private ArrayList<Rectangle> re = new ArrayList<>();
    ArrayList<Box[][]> boxes = new ArrayList<>();
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("");

        Pane root = new Pane();
        Grid g = new Grid(3,4);
        Matrix[] ms = g.getM();



//        Box[][] b = ms[0].getBoxes();
//        Box[][] b2 = ms[1].getBoxes();
//        Box[][] b3 = ms[2].getBoxes();
//
//        boxes.add(b);
//        boxes.add(b2);
//        boxes.add(b3);
//        double x = 10.0;
        double y = 10.0;
        double size = 42;
        double[][] co = { {x,y}, {x,y+size}, {x,y+(size*2)}, {x,y+(size*3)} };
        double xoffset = 0;
        double yoffset = 0;




//        for (int d = 0; d < boxes.get(0).length; d++) {
//
////            for (double[] p : co) {
////            re.add(new Rectangle(p[0], p[1], size, size));
////                for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < boxes.get(0)[1].length; j++) {
//                for (int i = 0; i < ms.length; i++) {
//                    for (int k = 0; k < 4; k++) {
//
//
//                        boxes.get(i)[d][j].setHeight(size);
//                        boxes.get(i)[d][j].setWidth(size);
//                        boxes.get(i)[d][j].setX(x + xoffset);
//                        boxes.get(i)[d][j].setY(y + yoffset);
//                        boxes.get(i)[d][j].setFill(Color.WHITE);
//                        boxes.get(i)[d][j].setStroke(Color.BLACK);
//                        boxes.get(i)[d][j].setOnMouseClicked(event);
//
//                    }
//                    root.getChildren().add(boxes.get(i)[d][j]);
//                    xoffset += size;
//                }
//                xoffset = 0;
//                yoffset+= size;
//
//            }
////                re.add(new Rectangle(p[0] + size, p[1], size, size));
//                re.add(new Rectangle(p[0] + (size * 2), p[1], size, size));
//                re.add(new Rectangle(p[0] + (size * 3), p[1], size, size));
//                }
//            }
        }


        primaryStage.setScene(new Scene(root,400,500,Color.WHITE));
        primaryStage.show();

    }

    private EventHandler<MouseEvent> event = mouseEvent -> {
        for (int i = 0; i < boxes.size(); i++) {
            for (int j = 0; j < boxes.get(i).length; j++)
                for (int k = 0; k < boxes.get(i)[1].length; k++)
            if (mouseEvent.getSource() == boxes.get(i)[j][k]) {
                if (boxes.get(i)[j][k].getFill().equals(Color.WHITE))
                    boxes.get(i)[j][k].setFill(Color.RED);
                else if (boxes.get(i)[j][k].getFill().equals(Color.RED))
                    boxes.get(i)[j][k].setFill(Color.LIMEGREEN);
                else
                    boxes.get(i)[j][k].setFill(Color.WHITE);
            }
        }
    };

}
