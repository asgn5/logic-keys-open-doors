
public class Matrix {

    private Box[][] boxes;
    private int size;
    private Item[] x, y;
    private Category cX, cY;

    // size is number of items
    public Matrix(int size, Category cX, Category cY ) {
        this.size = size;
        boxes = new Box[size][size];
        y = cY.getItems();
        x = cX.getItems();
        this.cY = cY;
        this.cX = cX;
        init();
    }

    public void init() {
        for (int i = 0; i < x.length; i++)
            for (int j = 0; j < y.length; j++)
                boxes[i][j] = new Box(x[i],y[j]);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(cX).append(" ").append(cY).append("\n");
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes[1].length; j++) {
                sb.append(boxes[i][j]).append(" ");
                if (j == boxes[1].length-1)
                    sb.append("\n");
            }
            if (i == boxes.length-1)
                sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Test to check this class works.
     *
     *  EXPECTED OUTPUT BELOW:
     *
     *     One Two
     *     { a , 1 } { b , 1 } { c , 1 } { d , 1 }
     *     { a , 2 } { b , 2 } { c , 2 } { d , 2 }
     *     { a , 3 } { b , 3 } { c , 3 } { d , 3 }
     *     { a , 4 } { b , 4 } { c , 4 } { d , 4 }
     *
     */
    public static void main(String[] args) {
        Item[] a = { new Item("1"),new Item("2"),new Item("3"),new Item("4")};
        Item[] b = { new Item("a"),new Item("b"),new Item("c"),new Item("d")};
        Matrix abc = new Matrix(4, new Category("One",4, a),new Category("Two",4, b));
        System.out.println(abc.toString());
    }

}
