/**
 * Created by qp on 2/3/17.
 */
public class Box {

    private boolean isClicked;

    public Item itemLeft, itemAbove;

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
        return "{ " + itemAbove +" , "+ itemLeft + " }";
    }


}
