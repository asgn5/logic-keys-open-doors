import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;

public class SubGrid extends GridPane {

    private int numItems;
    private Box[][] box;
    private int cordX, cordY;
    private String catAbove;
    private String catBelow;

    public SubGrid(int numItems, Box[][] box, int cordX, int cordY, String catAbove, String catBelow) {
        this.numItems = numItems;
        this.box = box;
        this.cordX = cordX;
        this.cordY = cordY;
        this.catAbove = catAbove;
        this.catBelow = catBelow;
        for (int i = 0; i < numItems; i++)
            for (int j = 0; j < numItems; j++) {
                box[i][j].setOnMouseClicked(event);
//                System.out.println(box[i][j].getRow() + " " + box[i][j].getColumn());
                System.out.println(cordX + " " + cordY);
                this.add(box[i][j],box[i][j].getRow(),box[i][j].getColumn());
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

    public String getCatAbove() {
        return catAbove;
    }

    public String getCatLeft() {
        return catBelow;
    }

    public void checkError() {
        for (int i = 0; i < numItems; i++)
            for (int j = 0; j < numItems; j++)
                if ((box[i][j].getImage() != Box.fillW) && (box[i][j].getImage() != box[i][j].getAnswerImage()))
                    box[i][j].clearImage();
    }

    public void clearAll() {
        for (int i = 0; i < numItems; i++)
            for (int j = 0; j < numItems; j++)
                box[i][j].clearImage();
    }

    private boolean checkCircle(int boxRow, int boxColumn/**/) {
        for (int i = 0; i < numItems; i++) {
            if (box[boxRow][i].getFill().equals(Box.fillC) || box[i][boxColumn].getFill().equals(Box.fillC)) {
                return true;
            }
        }
        return false;
    }

    private EventHandler<MouseEvent> event = mouseEvent -> {
        Box as = (Box) mouseEvent.getSource();
        System.out.println("BOX: Left " + as.getItemLeft() + " Above " + as.getItemAbove());
        if (as.getFill().equals(Box.fillW))
            as.nextImage();
        else if (as.getFill().equals(Box.fillX)) {
            if (!(checkCircle(as.getRow(),as.getColumn()))) {
                for (int i = 0; i < numItems; i++) {
                    box[as.getRow()][i].setImage(Box.fillX);
                    box[i][as.getColumn()].setImage(Box.fillX);
                }
                as.nextImage();
            }
        }
        else if (as.getFill().equals(Box.fillC)) {
            for (int q = 0; q < numItems; q++) {
                if (q != as.getRow())
                    box[q][as.getColumn()].removeImage();
                if (q != as.getColumn())
                    box[as.getRow()][q].removeImage();
            }
            as.clearImage();
        }
    };


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < box.length; i++) {
            for (int j = 0; j < numItems; j++) {
                sb.append(box[i][j]).append(" ");
                if (j == box[1].length - 1)
                    sb.append("\n");
            }
            if (i == box.length - 1)
                sb.append("\n");
        }
        return sb.toString();
    }
}
