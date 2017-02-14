import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Grid extends GridPane {

    private int categories, items;
    private String[] c;
    private SubGrid[] s;

    public Grid(int categories, int items, String[] c, SubGrid[] s) {
        this.categories = categories;
        this.items = items;
        this.c = c;
        this.s = s;
        for (SubGrid subGrid : s)
            this.add(subGrid, subGrid.getCordY(), subGrid.getCordX());
    }

    public SubGrid[] getS() {
        return s;
    }

    public int numItems() {
        return items;
    }

    public String[] getCategories() {
        return c;
    }

    public int numCategories() {
        return categories;
    }

    public void startOver() {
        for (SubGrid tmp : s) {
            tmp.clearAll();
        }
    }

    public void checkError() {
        for (SubGrid subGrid : s) {
            subGrid.checkError();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (SubGrid a : s)
            sb.append(a);
        return sb.toString();
    }

    public static Grid read(String path) throws IOException {
        ArrayList<String> list = allLines(path);
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String[]> vals = new ArrayList<>();
        ArrayList<ListEntry> answers = new ArrayList<>();
        int numCat = 0;
        int numItem = 0;
        for (String tmp : list) {
            if (tmp.contains("category number:")) {
                tmp = tmp.replace("category number:", "").trim();
                numCat = Integer.parseInt(tmp);
            } else if (tmp.contains("item number:")) {
                tmp = tmp.replace("item number:", "").trim();
                numItem = Integer.parseInt(tmp);
            } else if (tmp.contains("category:")) {
                tmp = tmp.replace("category:", "").trim();
                keys.add(tmp);
            } else if (tmp.contains("items:")) {
                tmp = tmp.replace("items:", "").trim();
                vals.add(tmp.split(","));
            } else if (tmp.contains("answer:")) {
                tmp = tmp.replace("answer:", "").trim();
                String[] holder = tmp.split(",");
                answers.add(new ListEntry(holder[0], holder[1]));
            }
        }
        SubGrid[] subGrids = new SubGrid[numCat];
        for (int j = 0; j < 1; j++)
            for (int k = 0; k < numCat - 1; k++) {
                Box[][] box = new Box[numItem][numItem];
                for (int num = 0; num < vals.get(0).length; num++)
                    for (int num2 = 0; num2 < vals.get(k + 1).length; num2++) {
                        ImagePattern pattern = Box.fillX;
                        for (ListEntry e : answers) {
                            if (vals.get(0)[num].equals(e.getKey()) && vals.get(k + 1)[num2].equals(e.getValue()))
                                pattern = Box.fillC;
                            box[num][num2] = new Box(vals.get(0)[num], vals.get(k + 1)[num2], num, num2, pattern);
                        }
                    }
                subGrids[k] = new SubGrid(numItem, box, 0, k + 1, keys.get(0), keys.get(k+1));
            }
        for (int l = numCat - 1; l > 1; l--) {
            Box[][] box = new Box[numItem][numItem];
            for (int num = 0; num < vals.get(l).length; num++)
                for (int num2 = 0; num2 < vals.get(l - 1).length; num2++) {
                    ImagePattern pattern = Box.fillX;
                    for (ListEntry e : answers)
                        if (e.getKey().equals(vals.get(l)[num2]) && e.getValue().equals(vals.get(l - 1)[num])) {
                            pattern = Box.fillC;
                            break;
                        }
                    box[num][num2] = new Box(vals.get(l)[num], vals.get(l-1)[num2], num, num2, pattern);
                }
            subGrids[l] = new SubGrid(numItem, box, l, l - 1, keys.get(l), keys.get(l-1));
        }
        String[] cats = new String[numCat];
        for (int k = 0; k < keys.size(); k++) cats[k] = keys.get(k);
        return new Grid(numCat, numItem, cats, subGrids);
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

    private static class ListEntry {
        private String key;
        private String val;
        public ListEntry(String key, String val) {
            this.key = key;
            this.val = val;
        }
        public String getKey() {
            return key;
        }
        public String getValue() {
            return val;
        }
        public String setValue(String v) {
            String tmp = val;
            val = v;
            return tmp;
        }
    }

    public static void main(String[] args) {
        try {
            Grid g = Grid.read("puz");
            System.out.println(g.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
