package base;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ArrivingTable {
    private ArrayList<Train> table = new ArrayList<>();
    private Scanner in;
    public ArrivingTable(Scanner in) throws FileNotFoundException {
        this.in = in;
        readTable();
    }
    public ArrivingTable() {}
    public void readTable() throws FileNotFoundException {
        int n = this.in.nextInt();
        for (int i = 0; i < n; i++) { // format : train number, arriving time, unloading time, payment
            table.add(new Train(this.in.nextInt(), this.in.nextInt(), this.in.nextInt(), this.in.nextInt()));
        }
        in.close();
    }
    public Train[] getSortedByTime() {
        ArrayList <Train> mas = new ArrayList<>(table);
        Collections.sort(mas, new SortByTime());
        Train[] res = new Train[mas.size()];
        for (int i = 0; i < mas.size(); i++) {
            res[i] = mas.get(i);
        }
        return res;
    }
}

class SortByTime implements Comparator<Train> {
    @Override
    public int compare(Train o1, Train o2) {
        return o1.compareTo(o2);
    }
}
