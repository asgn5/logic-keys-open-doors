import java.io.IOException;

public class Controller {

    private int sizeX;
    private int sizeY;
    private Grid grid;

    public Controller(int x, int y) {
        try {
            grid = Grid.read("puzzles");
        } catch (IOException e) {
            e.printStackTrace();
        }
        initializeComponents();
    }

    public void initializeComponents() {
    }
}
