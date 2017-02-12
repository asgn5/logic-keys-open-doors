import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Grid {

    private int categories, items;
    private String[] c;
    private Sub[] s;

    public Grid(int categories, int items, String[] c, Sub[] s) {//Matrix[] m) {
        this.categories = categories;
        this.items = items;
        this.c = c;
        this.s = s;
    }

//    public Box getBox(int x, int y) {
//        return s[x].getBox(x,y);
//    }

    public Sub[] getSubGrids() {
        return s;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Sub a : s)
            sb.append(a);
        return sb.toString();
    }

    public Sub[] getS() {
        return s;
    }

//    public Box[][] getBoxes(int i) {
//        return s[i].getBoxes();
//    }

    public static void main(String[] args) {
        try {
            Grid g = Grid.read("puzzles");
            for (int i = 0; i < 3; i++) {
                System.out.println(g.getS()[i].toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Grid read(String path) throws IOException {

        ArrayList<String> list = allLines(path);
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String[]> vals = new ArrayList<>();
        int numCat = 0;
        int numItem = 0;
        for (String tmp : list) {
            if (tmp.contains("category number:")) {
                tmp = tmp.replace("category number:", "").trim();
                numCat = Integer.parseInt(tmp);
            }
            else if (tmp.contains("item number:")) {
                tmp = tmp.replace("item number:","").trim();
                numItem = Integer.parseInt(tmp);
            }
            else if (tmp.contains("category:")) {
                tmp = tmp.replace("category:", "").trim();
                keys.add(tmp);
            } else if (tmp.contains("items:")) {
                tmp = tmp.replace("items:", "").trim();
                vals.add(tmp.split(","));
            }
        }
        Sub[] subGrids = new Sub[numCat];
        for (int j = 0; j < 1; j++) {
            for (int k = 0; k < numCat - 1; k++)
                subGrids[k] = new Sub(numItem, vals.get(0), vals.get(k+1), 0, k+1);
            for (int l = numCat-1; l > 1; l--)
                subGrids[l] = new Sub(numItem, vals.get(l), vals.get(l-1), l, l-1);
        }
        String[] cats = new String[numCat];
        for (int k = 0; k < keys.size(); k++) {
            cats[k] = keys.get(k);
        }
        return new Grid(numCat,numItem,cats,subGrids);
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
