package solutions;

import base.ArrivingTable;
import base.Train;

import java.util.ArrayList;

public class StupidSolution implements Solution {
    @Override
    public long calculateAns(ArrivingTable table) {
        Train[] trains = table.getSortedByTime();
        int n = trains.length;
        if (n > 25) {
            throw new IllegalArgumentException("Stupid solutions can works only with n <= 25");
        }
        final long INF = (long) 1e18;
        long ans = -INF;
        for (int mask = 0; mask < (1 << n); mask++) {
            ArrayList <Train> cur = new ArrayList<Train>();
            for (int train = 0; train < n; train++) {
                int b = (mask >> train) & 1;
                if (b == 1) {
                    cur.add(trains[train]);
                }
            }
            boolean ok = true;
            for (int i = 0; i < cur.size() - 1; i++) {
                int x1 = cur.get(i).getArrivingTime();
                int y1 = x1 + cur.get(i).getUnloadingTime();
                int x2 = cur.get(i + 1).getArrivingTime();
                int y2 = x2 + cur.get(i + 1).getUnloadingTime();
                if (y1 > x2) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                long curSum = 0;
                for (int i = 0; i <  cur.size(); i++) {
                    curSum += cur.get(i).getPayment();
                }
                ans = Math.max(ans, curSum);
            }
        }
        return ans;
    }
}
