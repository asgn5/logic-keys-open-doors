import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Grid {


    private int x, y; // x is number of categories, y is number of items

    private String[] c;

    private SubGrid[] s;

    public Grid(int x, int y, String[] c, SubGrid[] s) {//Matrix[] m) {
        this.x = x;
        this.y = y;
        this.c = c;
        this.s = s;
    }

    public Box getBox(int x, int y) {
        return s[x].getBox(x,y);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(SubGrid a : s)
            sb.append(a);
        return sb.toString();
    }

    public SubGrid[] getS() {
        return s;
    }

    public Box[][] getBoxes(int i) {
        return s[i].getBoxes();
    }

    public static void main(String[] args) {
        try {
            Grid g = Grid.read("puzzles",3,4);
            for (int i = 0; i < 3; i++) {
                System.out.println(g.getS()[i].toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Grid read(String path, int x, int y) throws IOException {

        ArrayList<String> list = allLines(path);
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String[]> vals = new ArrayList<>();
        for (String tmp : list) {
            if (tmp.contains("category:")) {
                tmp = tmp.replace("category:", "").trim();
                keys.add(tmp);
            } else if (tmp.contains("items:")) {
                tmp = tmp.replace("items:", "").trim();
                vals.add(tmp.split(","));
            }
        }
        SubGrid[] subGrids = new SubGrid[x];
        for (int j = 0; j < 1; j++) {
            for (int k = 0; k < x - 1; k++)
                subGrids[k] = new SubGrid(y, vals.get(0), vals.get(k+1));
            for (int l = x-1; l > 1; l--)
                subGrids[l] = new SubGrid(y, vals.get(l), vals.get(l-1));
        }
        String[] cats = new String[x];
        for (int k = 0; k < keys.size(); k++) {
            cats[k] = keys.get(k);
        }
        return new Grid(x,y,cats,subGrids);
    }

    private static ArrayList<String> allLines(String path) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        Scanner s = new Scanner(new File(path));
        while (s.hasNextLine()) {
            String temp = s.nextLine();
            temp = temp.trim();
            list.add(temp);
        }
        s.close();
        return list;
    }

}
