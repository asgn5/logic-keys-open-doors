import java.util.ArrayList;
import java.util.Random;

/**
 * Created by qp on 2/3/17.
 */
public class Category {

    private String name;
    private Item[] items;
    private int numberOfItems;

    public Category(String name, int numberOfItems, Item[] items) {
        this.name = name;
        this.numberOfItems = numberOfItems;
        this.items = items;
    }
    public Category(String name, int numberOfItems) {
        this.name = name;
        this.numberOfItems = numberOfItems;
        items = new Item[numberOfItems];
        Random r = new Random();
        int rand = 0;
        char test = '.';
        String test2 = "";

        for (int i = 0; i < numberOfItems; i++) {
            for (int j = 0; j < numberOfItems; j++) {
                rand = r.nextInt(30) + 63;
                test = (char) rand;
                test2 = Character.toString(test);
                items[j] = new Item(test2);
            }
        }
    }



    public Item[] getItems() {
        return items.clone();
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String iName) {
        name = iName;
    }

    public String toString() { return name; }

    public int hashCode() {
        int toReturn = 0;
        int count = 0;
        char[] arr = name.toCharArray();
        for (char c : arr)
            toReturn += (int) c * 33 / (1 + (count*7));
        return toReturn;
    }

    public boolean equals(Object o) {
        return o instanceof Category && o.hashCode() == this.hashCode();
    }
}
