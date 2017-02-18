import javafx.scene.paint.ImagePattern;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Responsible for initiating the entire grid.
 * Reads the contents of a file ( That is specifically formatted)
 * and dynamically creates the respective components,
 * namely the Grid with a specified number of SubGrids (useful for scaling)
 * and the SubGrids are dynamically created with the correct number
 * of Boxes within.
 */
public class Controller {

    /**
     * The logical representation of the Grid.
     */
    private Grid grid;

    /**
     * Contains the clues for a given puzzle.
     */
    private String[] clues;

    /**
     * Contains the back-story for a given puzzle
     */
    private String[] backstory;

    /**
     * Contains the intro displayed before any puzzle.
     */
    private String[] intro;

    /**
     * Creates the controller with,
     * @param grid The Grid -- the logic-puzzle
     * @param clues The contents of the clues
     * @param backstory The contents of the backstory
     */
    public Controller(Grid grid, String[] intro, String[] clues, String[] backstory) {
        this.grid = grid;
        this.intro = intro;
        this.clues = clues;
        this.backstory = backstory;
    }

    /**
     * @return grid The Grid of the logic puzzle
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * @return The intro statements
     */
    public String[] getIntro() {
        return intro;
    }

    /**
     * @return clues The clues for the puzzle
     */
    public String[] getClues() {
        return clues;
    }

    /**
     * @return The back-story of the puzzle
     */
    public String[] getBackstory() {
        return backstory;
    }

    /**
     * A fundamentally crucial step of automation for the puzzle.
     * Reads a file that has a specific format for cues, interprets these cues,
     *   and then stores this information to be sorted.
     *
     * IMPORTANT: This is a large step towards scaling the puzzle to adapting larger sizes.
     *            as well as a very simple way to generate new puzzles.
     * @param path The path of the file to be parsed.
     * @return controller The controller with its respective components.
     * @throws IOException If the path does not exist or the file is not found.
     * Though it may be redundant, it is necessary to emphasize that different
     * IDEs respect various types of paths (relative or absolute) so,

     * IMPORTANT: MAKE SURE THE FILE IS IN THE CORRECT LOCATION FOR YOUR IDE!
     *            OR EDIT THE PATH TO REFLECT THE IDE's RESPECTED PATH
     */
    public static Controller read(String path) throws IOException {

        ArrayList<String> list = allLines(path);
        ArrayList<String> introItems = new ArrayList<>();
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String[]> vals = new ArrayList<>();
        ArrayList<ListEntry> answers = new ArrayList<>();
        ArrayList<String> clues = new ArrayList<>();
        ArrayList<String> backstory = new ArrayList<>();
        int numCat = 0;
        int numItem = 0;
        for (String tmp : list) {
            if (tmp.contains("intro:")) {
                tmp = tmp.replace("intro:","");
                introItems.add(tmp);
            } else if (tmp.contains("category number:")) {
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
            } else if (tmp.contains("clue:")) {
                tmp = tmp.replace("clue:", "").trim();
                clues.add(tmp);
            } else if (tmp.contains("backstory:")) {
                tmp = tmp.replace("backstory:", "").trim();
                backstory.add(tmp);
            }

        }
        SubGrid[] subGrids = new SubGrid[numCat];
        for (int k = 0; k < numCat; k++) { // For number of categories
            Box[][] box = new Box[numItem][numItem]; // the 2D array representing the subgrid
            for (int num = 0; num < numItem; num++) {
                for (int num2 = 0; num2 < numItem; num2++) {
                    ImagePattern pattern = Box.fillX;
                    for (ListEntry e : answers) { // For every entry in answers --> checks to see if box is an answer
                        if (k < numCat - 1) {
                            if (vals.get(0)[num].equals(e.getKey()) && vals.get(k + 1)[num2].equals(e.getValue()))
                                pattern = Box.fillC;
                            box[num][num2] = new Box(vals.get(0)[num2], vals.get(k + 1)[num], num, num2, pattern);
                        } else {
                            if (e.getKey().equals(vals.get(k)[num2]) && e.getValue().equals(vals.get(k - 1)[num]))
                                pattern = Box.fillC;
                            box[num][num2] = new Box(vals.get(k)[num2], vals.get(k - 1)[num], num, num2, pattern);
                        }
                    }
                }
            }
            if (k < numCat - 1) subGrids[k] = new SubGrid(numItem, box, 0, k + 1, keys.get(0), keys.get(k + 1));
            else subGrids[k] = new SubGrid(numItem, box, k, k - 1, keys.get(k), keys.get(k - 1));
        }
        String[] intro = introItems.toArray(new String[introItems.size()]);
                String[] cats = keys.toArray(new String[keys.size()]);
        String[] cluesCopy = clues.toArray(new String[clues.size()]);
        String[] backStoryCopy = backstory.toArray(new String[backstory.size()]);

        return new Controller(new Grid(numCat, numItem, cats, subGrids), intro, cluesCopy, backStoryCopy);
    }

    /**
     * Helper function that adds every line of the file into an ArrayList
     * that is then assigned in the method above
     * @param path The path to the file.
     * @return list The arraylist with each line representing an index in the list.
     * @throws IOException If the file is not found
     */
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

    /**
     * Helper class to
     * Simple representation of a Map.ListEntry that uses specific key and value types
     * here, String key and String val.
     */
    private static class ListEntry {

        /** Reference for the key. */
        private String key;

        /** Reference for the value */
        private String val;

        /**
         * @param key The key
         * @param val The value
         */
        public ListEntry(String key, String val) {
            this.key = key;
            this.val = val;
        }

        /** @return The key */
        public String getKey() {
            return key;
        }

        /** @return the value */
        public String getValue() {
            return val;
        }

        /**
         * @param v The value to be set.
         * @return the old value.
         */
        public String setValue(String v) {
            String tmp = val;
            val = v;
            return tmp;
        }
    }

    @Override
    public String toString() {
        return grid.toString();
    }

}
//        for (int j = 0; j < 1; j++)
//            for (int k = 0; k < numCat - 1; k++) {
//                Box[][] box = new Box[numItem][numItem];
//                for (int num = 0; num < vals.get(0).length; num++)
//                    for (int num2 = 0; num2 < vals.get(k + 1).length; num2++) {
//                        ImagePattern pattern = Box.fillX;
//                        for (ListEntry e : answers) {
//                            if (vals.get(0)[num].equals(e.getKey()) && vals.get(k + 1)[num2].equals(e.getValue()))
//                                pattern = Box.fillC;
//                            box[num][num2] = new Box(vals.get(0)[num], vals.get(k + 1)[num2], num, num2, pattern);
//                        }
//                    }
//                subGrids[k] = new SubGrid(numItem, box, 0, k + 1, keys.get(0), keys.get(k+1));
//            }
//        for (int l = numCat - 1; l > 1; l--) {
//            Box[][] box = new Box[numItem][numItem];
//            for (int num = 0; num < vals.get(l).length; num++)
//                for (int num2 = 0; num2 < vals.get(l - 1).length; num2++) {
//                    ImagePattern pattern = Box.fillX;
//                    for (ListEntry e : answers)
//                        if (e.getKey().equals(vals.get(l)[num2]) && e.getValue().equals(vals.get(l - 1)[num])) {
//                            pattern = Box.fillC;
//                            break;
//                        }
//                    box[num][num2] = new Box(vals.get(l)[num], vals.get(l-1)[num2], num, num2, pattern);
//                }
//            subGrids[l] = new SubGrid(numItem, box, l, l - 1, keys.get(l), keys.get(l-1));
//        }
