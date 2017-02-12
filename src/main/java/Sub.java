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
                box[i][j] = new Box(itemx[i],itemy[j],i,j);
                boxes.add(box[i][j]);
            }
        }

        for (int k = 0; k < numItems * numItems; k++) {
            Box tmp = boxes.get(k);
            this.add(tmp, tmp.getRow(),tmp.getColumn());
            this.getChildren().get(k).setOnMouseClicked(event);
        }

    }

    public int getCordX() { return cordX; }

    public int getCordY() { return cordY; }


    public Node getRowColumn(int row, int column) {
        Node ret = null;
        ObservableList<Node> child = this.getChildren();
        for (Node node : child)
            if (getRowIndex(node) == row &&
                    getColumnIndex(node) == column) {
                ret = node;
                break;
            }
        return ret;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < box.length; i++) {
            for (int j = 0; j <  numItems; j++) {
                sb.append(box[j][i]).append(" ");
                if (j == box[1].length-1)
                    sb.append("\n");
            }
            if (i == box.length-1)
                sb.append("\n");
        }
        return sb.toString();
    }

    private EventHandler<MouseEvent> event = mouseEvent -> {
        for (int i = 0; i < 16; i++) {
            if (mouseEvent.getSource().equals(this.getChildren().get(i))) {
                Node node = this.getChildren().get(i);
                Box as = (Box) node;
                if (as.getFill().equals(Color.RED)) {
                    for (int j = 0; j < 4; j++) {
                        Box b = (Box) getRowColumn(j,as.getRow());
                        Box c = (Box) getRowColumn(as.getColumn(),j);
                        if (!(c.getFill().equals(Color.GREEN)))
                            b.setColor(Color.RED);
                        if (!(c.getFill().equals(Color.GREEN)))
                            c.setColor(Color.RED);
                    }
                }
                else if (as.getFill().equals(Color.GREEN)) {
                    for (int q = 0; q < 4; q++) {
                        Box d = (Box) getRowColumn(q,as.getRow());
                        Box e = (Box) getRowColumn(as.getColumn(),q);
                        if (!(d.getFill().equals(Color.GREEN)))
                            d.removeColor();
                        if (!(e.getFill().equals(Color.GREEN)))
                            e.removeColor();
                    }
                }
                as.nextColor();
            }
        }
    };

    public static void main(String[] args) {
        String[] a = {"A", "B", "C", "D"};
        String[] b = {"1", "2", "3", "4"};
        Sub s = new Sub(4, a,b, 0, 0);
        System.out.println(s.toString());
    }


}
