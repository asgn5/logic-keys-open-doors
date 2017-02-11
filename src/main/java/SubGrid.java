
public class SubGrid{

    private Box[][] boxes;
    private int size;
    private String[] x, y;
    private String categoryX, categoryY;

    public SubGrid(String categoryX, String categoryY, int size, String[] x, String[] y ) {
        this.categoryX = categoryX;
        this.categoryY = categoryY;
        this.size = size;
        boxes = new Box[size][size];
        this.x = x;
        this.y = y;
        init();
    }

    public SubGrid(int size, String[] x, String[] y ) {
        this.size = size;
        boxes = new Box[size][size];
        this.x = x;
        this.y = y;
        init();
    }

    public void init() {
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < y.length; j++) {
                boxes[i][j] = new Box(x[j],y[i], i, j);
                System.out.println(i + " " + j);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
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

    public Box getBox(int x, int y) {
        return boxes[x][y];
    }

    public Box[][] getBoxes() {
        return boxes;
    }

    public int getSize() {
        return size;
    }

    public static void main(String[] args) {
        String[] a = { "1","2","3","4" };
        String[] b = { "a","b","c","d" };
        SubGrid abc = new SubGrid(4, a, b);
        System.out.println(abc.toString());
    }

}
