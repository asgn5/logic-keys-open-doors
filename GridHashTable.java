import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @param <K> The object corresponding to the Key
 * @param <V> The object corresponding to the Value
 */
public class GridHashTable<K, V> implements GridHashMap<K, V> {

    private static final int CAPACITY = 4;
    private LinkedList<ListEntry<K, V>>[] table;
    private int numKeys;

    @SuppressWarnings("unchecked")
    public GridHashTable(int numKeys) {
        this.numKeys = numKeys;
        table = new LinkedList[numKeys];
    }

    @Override
    public V get(Object key) {
        int index = (key.hashCode()*33) % table.length;
        if (index < 0) index += table.length;
        if (table[index] == null) return null;
        LinkedList<ListEntry<K, V>> ref = table[index];
        for (ListEntry<K, V> a : ref)
            if (a.key.equals(key))
                return a.getValue();
        return null;
    }

    @Override
    public boolean isEmpty() {
        return table == null;
    }

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

    public static GridHashTable<String, String> read(String path, int num) throws IOException {
        GridHashTable<String, String> table = new GridHashTable<>(num);
        ArrayList<String> list = allLines(path);
        String current = "";
        String[] setItems;
        for (String tmp : list) {
            if (tmp.contains("category:")) {
                tmp = tmp.replace("category:", "").trim();
                current = tmp;
            } else if (tmp.contains("items:")) {
                tmp = tmp.replace("items:", "");
                setItems = tmp.split(",");
                for (String b : setItems)
                    table.put(current, b.trim());
            }
        }
        return table;
    }

    public static void main(String[] args) {
        try {
            GridHashTable<String, String> tbl = GridHashTable.read("puzzles", 3);
            System.out.println(tbl.print("Chapter"));
            System.out.println(tbl.print("Authors"));
            System.out.println(tbl.print("Subject"));
        } catch (IOException e) {

        }
    }

    @Override
    public V put(K key, V value) {
        int index = (key.hashCode() * 33) % table.length;
        if (index < 0) index += table.length;
        if (table[index] == null)
            table[index] = new LinkedList<>();
        table[index].add(new ListEntry<>(key, value));
        numKeys++;
        return null;
    }

    @Override
    public V remove(Object key) {
        int index = (key.hashCode() * 33) % table.length;

        if (index < 0) index += table.length;
        if (table[index] == null) return null;
        Iterator<ListEntry<K, V>> iter = table[index].iterator();
        while (iter.hasNext()) {
            ListEntry<K, V> next = iter.next();
            if (next.key.equals(key)) {
                V toReturn = next.value;
                if (table[index].size() == 1)
                    table[index] = null;
                else
                    iter.remove();
                return toReturn;
            }
        }
        return null;
    }


    @Override
    public int size() {
        return table.length;
    }

    public String print(K kb) {
        int index = (kb.hashCode() * 33) % table.length;
        if (index < 0) index += table.length;
        StringBuilder sb = new StringBuilder();
        if (table[index] == null)
            sb.append("\n\tEmpty");
        else {
            sb.append(kb.toString()).append("\n");
            for (ListEntry<K, V> entry : table[index]) {
                sb.append("\t")
                        .append(entry.getValue().toString())
                        .append("\n");
            }
        }
        return sb.toString();
    }


    /**
     * This inner class holds the key value pairs for the hash-table
     *
     * @param <K> The generic object representing the key
     * @param <V> The generic object representing the value
     */
    private static class ListEntry<K, V> implements Map.Entry<K, V> {


        /**
         * The Key reference
         */
        private K key;

        /**
         * The generic value reference
         */
        private V value;


        /**
         * Initializes the Entry with a distinct key-value pair
         *
         * @param key   the Key
         * @param value the corresponding value
         */
        public ListEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }


        /**
         * @return the entries key
         */
        @Override
        public K getKey() {
            return key;
        }


        /**
         * @return The corresponding value
         */
        @Override
        public V getValue() {
            return value;
        }


        /**
         * @param v
         * @return
         */
        @Override
        public V setValue(V v) {
            V old = value;
            value = v;
            return old;
        }
    }
}