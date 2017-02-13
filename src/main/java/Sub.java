import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

import javafx.scene.input.MouseEvent;

public class Sub extends GridPane {

    private ArrayList<Box> boxes;
    private int numItems;
    private Box[][] box;
    private int cordX, cordY;

    public Sub(int numItems, String[] itemx, String[] itemy, int cordX, int cordY) {
        this.numItems = numItems;
        this.cordX = cordX;
        this.cordY = cordY;

        box = new Box[numItems][numItems];
        boxes = new ArrayList<>();

        for (int i = 0; i < numItems; i++) {
            for (int j = 0; j < numItems; j++) {
                box[i][j] = new Box(itemx[i], itemy[j], i, j);
                boxes.add(box[i][j]);
            }
        }

        for (int k = 0; k < numItems * numItems; k++) {
            Box tmp = boxes.get(k);
            this.add(tmp, tmp.getRow(), tmp.getColumn());
            this.getChildren().get(k).setOnMouseClicked(event);
        }

    }

    public int getCordX() {
        return cordX;
    }

    public int getCordY() {
        return cordY;
    }

        public String getBoxItemAbove(int i, int j) {
        return box[i][j].getItemAbove();
    }

    public String getBoxItemLeft(int i, int j) {
        return box[i][j].getItemLeft();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < box.length; i++) {
            for (int j = 0; j < numItems; j++) {
                sb.append(box[j][i]).append(" ");
                if (j == box[1].length - 1)
                    sb.append("\n");
            }
            if (i == box.length - 1)
                sb.append("\n");
        }
        return sb.toString();
    }

    private EventHandler<MouseEvent> event = mouseEvent -> {
        Box as = (Box) mouseEvent.getSource();
        if (as.getFill().equals(Color.WHITE))
            as.nextColor();
        else if (as.getFill().equals(Color.RED)) {
            if (!(checkGreen(as.getRow(), as.getColumn()))) {
                for (int j = 0; j < numItems; j++) {
                    box[as.getRow()][j].setColor(Color.RED);
                    box[j][as.getColumn()].setColor(Color.RED);
                }
                as.nextColor();
            }
        } else if (as.getFill().equals(Color.GREEN)) {
            for (int q = 0; q < numItems; q++) {
                if (q != as.getRow())
                    box[q][as.getColumn()].removeColor();
                if (q != as.getColumn())
                    box[as.getRow()][q].removeColor();

            }
            as.setFill(Color.WHITE);
            as.clearColor();
        }

    };

    private boolean checkGreen(int boxRow, int boxColumn) {
        for (int i = 0; i < numItems; i++)
            if (box[boxRow][i].getFill().equals(Color.GREEN) || box[i][boxColumn].getFill().equals(Color.GREEN))
                return true;
        return false;
    }

    public static void main(String[] args) {
        String[] a = {"A", "B", "C", "D"};
        String[] b = {"1", "2", "3", "4"};
        Sub s = new Sub(4, a, b, 0, 0);
        System.out.println(s.toString());
    }
}
