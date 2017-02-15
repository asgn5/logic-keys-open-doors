/* Richard Kent */
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.ArrayList;

/**
 * The logical components of the logic puzzle
 * Contains an array of a specified number of SubGrids,
 * a reference to the number of items and categories,
 * as well as reference to all of the Category names
 * Specializes GridPane for its graphical representation only.
 */
public class Grid extends GridPane {

    /**
     * The number of categories and items in each category respectively.
     */
    private int categories, items;

    /**
     * Reference to the name of each category
     */
    private String[] c;

    /**
     * Reference to all og the SubGrids within this Grid.
     */
    private SubGrid[] s;

    /**
     * @param categories
     * @param items
     * @param c
     * @param s
     */
    public Grid(int categories, int items, String[] c, SubGrid[] s) {
        this.categories = categories;
        this.items = items;
        this.c = c;
        this.s = s;

        for (SubGrid subGrid : s)
            this.add(subGrid, subGrid.getCordY()+1, subGrid.getCordX()+1);
    }

    /**
     * @return s The SubGrids
     */
    public SubGrid[] getS() {
        return s;
    }

    /**
     * @return items A reference to the number of items in each category
     */
    public int numItems() {
        return items;
    }

    /**
     * @return c The categories
     */
    public String[] getCategories() {
        return c;
    }

    /**
     * @return categories Reference to the number of categories
     */
    public int numCategories() {
        return categories;
    }

    /**
     * Communicates to each SubGrid to reset all of its contents.
     */
    public void startOver() {
        for (SubGrid tmp : s) tmp.clearAll();
    }

    /**
     * Asks each SubGrid to check for errors.
     */
    public void checkError() {
        for (SubGrid subGrid : s) subGrid.checkError();
    }

    /**
     * @return The String representation of each SubGrid that is a member of this Grid.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (SubGrid a : s) sb.append(a);
        return sb.toString();
    }
}
