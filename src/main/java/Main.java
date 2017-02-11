import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    private GridPane gridPane;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("GridPane Experiment");
        ArrayList<Box> b = new ArrayList<>();
        gridPane = new GridPane();
        String[] i1 = {"A", "B", "C", "D"};
        String[] i2 = {"1", "2", "3", "4"};

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                b.add(new Box(i1[i], i2[j], i, j));

        for (int k = 0; k < 16; k++) {
            gridPane.add(b.get(k), b.get(k).getRow(), b.get(k).getColumn());
            gridPane.getChildren().get(k).setOnMouseClicked(event);
        }

        Scene scene = new Scene(gridPane, 440, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private EventHandler<MouseEvent> event = mouseEvent -> {
        for (int i = 0; i < 16; i++) {
            if (mouseEvent.getSource().equals(gridPane.getChildren().get(i))) {
                Node node = gridPane.getChildren().get(i);
                Box as = (Box) node;
                if (as.getFill().equals(Color.RED)) {
                    for (int j = 0; j < 4; j++) {
                        Box b = (Box) getByRowColumn(j,as.getRow());
                        Box c = (Box) getByRowColumn(as.getColumn(),j);
                        if (!(c.getFill().equals(Color.GREEN)))
                            b.setColor(Color.RED);
                        if (!(c.getFill().equals(Color.GREEN)))
                            c.setColor(Color.RED);
                    }
                }
                else if (as.getFill().equals(Color.GREEN)) {
                    for (int q = 0; q < 4; q++) {
                        Box d = (Box) getByRowColumn(q,as.getRow());
                        Box e = (Box) getByRowColumn(as.getColumn(),q);
                        if (!(d.getFill().equals(Color.GREEN)))
                            d.removeColor();
                        if (!(e.getFill().equals(Color.GREEN)))
                            e.removeColor();
                    }
                }
                as.nextColor();
//                if (as.getFill().equals(Color.WHITE)) {
//                    as.setFill(Color.RED);
//                } else if (as.getFill().equals(Color.RED)) {
//                    as.setFill(Color.GREEN);
//                    for (int j = 0; j < 4; j++) {
//                        Node b = getByRowColumn(j, as.getRow());
//                        Node c = getByRowColumn(as.getColumn(), j);
//                        Box q = (Box) b;
//                        Box w = (Box) c;
//                        if (q.getFill().equals(Color.WHITE)) {
//                            q.setFill(Color.RED);
//                        }
//                        if (((Box) c).getFill().equals(Color.WHITE))
//                            ((Box) c).setFill(Color.RED);
//                    }
//
//                } else if (as.getFill().equals(Color.GREEN)) {
//                    as.setFill(Color.WHITE);
//                    for (int k = 0; k < 4; k++) {
//                        Node b = getByRowColumn(k, as.getRow());
//                        Node c = getByRowColumn(as.getColumn(), k);
//                        Box q = (Box) b;
//                        Box w = (Box) c;
//                        if (q.getFill().equals(Color.RED)) {
//                            q.setFill(Color.WHITE);
//                        }
//                        if (((Box) c).getFill().equals(Color.RED))
//                            ((Box) c).setFill(Color.WHITE);
//                    }
//
//                }
            }
        }
    };

    public Node getByRowColumn(int row, int column) {
        Node ret = null;
        ObservableList<Node> child = gridPane.getChildren();
        for (Node node : child)
            if (gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                ret = node;
                break;
            }
        return ret;
    }


}