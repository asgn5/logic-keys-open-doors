import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class MyMap {

    public static ArrayList<String> allLines(String path) throws IOException {
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

    public static Hashtable<String, String> read(String path, int num) throws IOException {
        Hashtable<String, String> table = new Hashtable<>(num);
        ArrayList<String> list = allLines(path);
        ArrayList<String> cats = new ArrayList<>();
        ArrayList<String[]> items = new ArrayList<>();
        String current = "";
        String[] setItems;

        for (String tmp: list) {
            if (tmp.contains("category:")) {
                tmp = tmp.replace("category:", "");
                tmp = tmp.trim();
                current = tmp;
                cats.add(tmp);
            }
            else if (tmp.contains("items:")) {
                tmp = tmp.replace("items:", "");
                setItems = tmp.split(",");
                for (String b : setItems) {
                    table.put(current, b.trim());
                }

            }
        }
        return table;
    }

    public static void main(String[] args) {
        try {
            Hashtable<String, String> tbl = MyMap.read("puzzles",3);
            System.out.println(tbl.get("Authors"));
        } catch(IOException e) {
        }
    }


}

