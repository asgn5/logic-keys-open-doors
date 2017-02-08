import java.util.ArrayList;
import java.util.Random;

public class Grid {

    private int x, y; // x is number of categories, y is number of items

    private Category[] c;
    private Matrix[] m;

    public Grid(int x, int y) {
        this.x = x;
        this.y = y;
        c = new Category[x];
        m = new Matrix[x];
        init();
    }

    public void init() {
        for (int i = 0; i < x; i++) {
            c[i] = new Category(""+i, y);
	    for (int j = 0; j < 1; j++) {
		for (int k = 0; k < x - 1; k++)
		    m[k] = new Matrix(y, c[0], c[k+1]);
		for (int l = x-1; l > 1; l--)
		    m[l] = new Matrix(y, c[l],c[l-1]);
	    }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Matrix a : m)
            sb.append(a);
        return sb.toString();
    }


    /**
     *  Test to check this class works.
     *
     *  EXPECTED OUTPUT BELOW (Note: the actual letters will vary
     *
     *      1 0
     *      { X , U } { F , U } { E , U } { R , U }
     *      { X , T } { F , T } { E , T } { R , T }
     *      { X , M } { F , M } { E , M } { R , M }
     *      { X , B } { F , B } { E , B } { R , B }
     *
     *      2 0
     *      { O , U } { ? , U } { K , U } { E , U }
     *      { O , T } { ? , T } { K , T } { E , T }
     *      { O , M } { ? , M } { K , M } { E , M }
     *      { O , B } { ? , B } { K , B } { E , B }
     *
     *      1 2
     *      { X , O } { F , O } { E , O } { R , O }
     *      { X , ? } { F , ? } { E , ? } { R , ? }
     *      { X , K } { F , K } { E , K } { R , K }
     *      { X , E } { F , E } { E , E } { R , E }
     *
     **/
    public static void main(String[] args) {
        Grid g = new Grid(3,4);
        System.out.println(g.toString());
    }

}
