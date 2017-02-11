import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Box extends Rectangle {

    private boolean isClicked;
    private String left, above;
    private int row, column, numClicks;
    private static int COUNT = 0;
    private final Color DEFAULT = Color.WHITE;
    private Stack<Color> colors;
    private static Map<Color, Color> addMap = new HashMap<Color,Color>();
    static {
        addMap.put(Color.WHITE, Color.RED);
        addMap.put(Color.RED, Color.GREEN);
        addMap.put(Color.GREEN, Color.WHITE);
    }
    //    public Box(double xLo, double yLo, double size, String left, String above, int x, int y) {
//        super(xLo,yLo,size,size);
//        this.x = x; // index in 2d array
//        this.y = y; // ""
//        this.above = above;
//        this.left = left;
//        isClicked = false;
//    }

    public Box(String itemLeft, String itemAbove, int row, int column) {
        super(42, 42);
        this.above = itemAbove;
        this.left = itemLeft;
        this.row = row;
        this.column = column;
        this.setFill(DEFAULT);
        this.setStroke(Color.BLACK);
        isClicked = false;
        colors = new Stack<>();
        colors.push(DEFAULT);
        numClicks = 0;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String setColor(Color color) {
        this.setFill(color);
        colors.push(color);
        return current();
    }

    public Color nextColor() {
        Color tmp = addMap.get(colors.peek());
        this.setFill(tmp);
        colors.push(tmp);
        return tmp;
    }

    public String removeColor() {
        colors.pop();
        this.setFill(colors.peek());
        return current();
    }

    public void setClicked() {
        isClicked = !isClicked;
    }

    @Override
    public String toString() {
        return String.format("{%12s | %-12s}{%d,%d},{%s}", left, above, row, column, current());
    }

    private String getColorName(Color color) {
        String RESET = "\u001B[0m";
        String BLACK = "\u001B[30m";
        String RED = "\u001B[31m";
        String GREEN = "\u001B[32m";
        String YELLOW = "\u001B[33m";
        String BLUE = "\u001B[34m";
        String PURPLE = "\u001B[35m";
        String CYAN = "\u001B[36m";
        String WHITE = "\u001B[37m";
        String ret = "N/A";
        if (color.equals(Color.WHITE))
            ret =  WHITE + "WHITE" + RESET;
        else if (color.equals(Color.RED))
            ret =  RED + "RED" + RESET;
        else if (color.equals(color.GREEN))
            ret =  GREEN + "GREEN" + RESET;
        return ret;
    }

    public String getStack() {
        StringBuilder sb = new StringBuilder("| ");
        for (Color c : colors)
            sb.append(getColorName(c)).append(" | ");
        return sb.toString();
    }

    public String current() {
       return getColorName(colors.peek());
    }

    /**
     * White: 0xffffffff
     * Red:   0xff0000ff
     * Green: 0x008000ff
     */
    public static void main(String[] args) {
        Box b = new Box("A", "1", 0, 0);
        System.out.println(test("CURRENTCOLOR --> NEXTCOLOR"));
        System.out.printf("%-10s \t-->\t %10s\n", b.current(),b.getColorName(b.nextColor()));

        System.out.println(test("CURRENTCOLOR --> SETCOLOR"));
        System.out.printf("%-10s \t-->\t %10s\n", b.current(),b.setColor(Color.RED));

        System.out.println(test("CONTENTS OF STACK"));
        System.out.println(b.getStack());

        System.out.println(test("REMOVE COLOR"));
        System.out.printf("%-10s \t-->\t %10s\n", b.current(),b.removeColor());
        System.out.printf("%-10s \t-->\t %10s\n", b.current(),b.removeColor());

        System.out.println(test("CONTENTS OF STACK"));
        System.out.println(b.getStack());

        System.out.println(test("NEXTCOLOR"));
        System.out.printf("%-10s \t>>>\t %10s\n", b.current(),b.getColorName(b.nextColor()));
        System.out.printf("%-10s \t>>>\t %10s\n", b.current(),b.getColorName(b.nextColor()));
        System.out.printf("%-10s \t>>>\t %10s\n", b.current(),b.getColorName(b.nextColor()));
        System.out.printf("%-10s \t>>>\t %10s\n", b.current(),b.getColorName(b.nextColor()));

    }

    private static String test(String str) {
        return "\u001B[33m"+"TEST: "+ str + "\u001B[0m";
    }
}
