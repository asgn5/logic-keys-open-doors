import java.io.IOException;

/**
 * Created by qp on 2/3/17.
 */
public class Controller {


    private int sizeX;
    private int sizeY;
    private Grid grid;

    public Controller(int x, int y) {
        try {
            grid = Grid.read("puzzles",3,4);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initializeComponents();
    }

    public void initializeComponents() {
//        try {
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public Box getBox(int coordinateX, int coordinateY) {
        return grid.getBox(coordinateX, coordinateY);
    }




//    private Category[] categories;

//    public Controller( int sizeX, int sizeY, Category[] categories, Matrix[] matricies) {
//        this.sizeX = sizeX;
//        this.sizeY = sizeY;
//        categories = new Category[sizeX];
//        matricies = new Matrix[sizeX];
//
//        view = new View();
//    }


}
