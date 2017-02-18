import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Box extends Rectangle {

    private static final String xMark = "file:x28.png";
    private static final String circle = "file:circle28.png";
    private static final String white = "file:white_fill.png";
    public static final ImagePattern fillX = new ImagePattern(new Image(xMark));
    public static final ImagePattern fillC = new ImagePattern(new Image(circle));
    public static final ImagePattern fillW = new ImagePattern(new Image(white));
    private static Map<Color, Color> addMap = new HashMap<Color,Color>();

    static {
        addMap.put(Color.WHITE, Color.RED);
        addMap.put(Color.RED, Color.GREEN);
        addMap.put(Color.GREEN, Color.WHITE);
    }

    private static Map<ImagePattern, ImagePattern> imageMap = new HashMap<>();

    static {
        imageMap.put(fillW, fillX);
        imageMap.put(fillX, fillC);
        imageMap.put(fillC, fillW);
    }

    private String left, above;
    private int row, column;
    private Stack<ImagePattern> images;
    private ImagePattern answerImage;

    public Box(String itemLeft, String itemAbove, int row, int column, ImagePattern answerImage) {
        super(30, 30);
        this.above = itemAbove;
        this.left = itemLeft;
        this.row = row;
        this.column = column;
        this.answerImage = answerImage;
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(1);
        images = new Stack<>();
        images.push(fillW);
        this.setFill(fillW);
    }

    public String getItemAbove() {
        return above;
    }

    public String getItemLeft() {
        return left;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public ImagePattern getAnswerImage() {
        return answerImage;
    }

    public ImagePattern getImage() {
        return images.peek();
    }

    public ImagePattern nextImage() {
        ImagePattern tmp = imageMap.get(images.peek());
        this.setFill(tmp);
        images.push(tmp);
        return tmp;
    }

    public ImagePattern setImage(ImagePattern pattern) {
        images.push(pattern);
        this.setFill(pattern);
        return images.peek();
    }

    public ImagePattern removeImage() {
        images.pop();
        this.setFill(images.peek());
        return images.peek();
    }

    public void clearImage() {
        images = new Stack<>();
        this.setFill(fillW);
        images.push(fillW);
    }

    @Override
    public String toString() {
        return String.format("{%12s | %-12s}{%d,%d},{%s}{%s}", left, above, row, column, current(), getColorName(answerImage));
    }

    /**
     * From below down are simply testing functions
     * Utility function for testing purposes.
     * @param pattern The color to pretty print
     * @return the color in string form with color for console
     */
    public String getColorName(ImagePattern pattern) {
        String RESET = "\u001B[0m";
        String RED = "\u001B[31m";
        String GREEN = "\u001B[32m";
        String WHITE = "\u001B[37m";
        String ret = "N/A";
        if (pattern.equals(fillW))
            ret =  WHITE + "WHITE" + RESET;
        else if (pattern.equals(fillX))
            ret =  RED + "RED" + RESET;
        else if (pattern.equals(fillC))
            ret =  GREEN + "GREEN" + RESET;
        return ret;
    }

    public String current() {
        return getColorName(images.peek());
    }

    public String getStack() {
        StringBuilder sb = new StringBuilder("| ");
        for (ImagePattern c : images)
            sb.append(getColorName(c)).append(" | ");
        return sb.toString();
    }



}