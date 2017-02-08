import javafx.scene.shape.Rectangle;

/**
 * Created by qp on 2/3/17.
 */
public class Box extends Rectangle {

    private boolean isClicked;

    public Item itemLeft, itemAbove;
    public double x, y, size;

    public Box(double x, double y, double size, Item itemLeft, Item itemAbove) {
        super(x,y,size,size);
        this.x = x;
        this.y = y;
        this.size = size;
        this.itemAbove = itemAbove;
        this.itemLeft = itemLeft;
        isClicked = false;
    }

    public Box(Item itemLeft, Item itemAbove) {
        this.itemAbove = itemAbove;
        this.itemLeft = itemLeft;
        isClicked = false;
    }

    public void setClicked() {
        isClicked = !isClicked;
    }

    @Override
    public String toString() {
        return "{ " + itemAbove +" , "+ itemLeft + " }" + "{" + x + " " + y + " " + size + "}";
    }


}
