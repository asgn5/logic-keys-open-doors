/**
 * Created by qp on 2/3/17.
 */
public class Controller {

    private View view;
    private int sizeX;
    private int sizeY;
    private Matrix[] matrices;
    private Category[] categories;

    public Controller( int sizeX, int sizeY, Category[] categories, Matrix[] matricies) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        categories = new Category[sizeX];
        matricies = new Matrix[sizeX];

        view = new View();
    }


}
