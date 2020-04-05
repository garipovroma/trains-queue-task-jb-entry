package solutions;

import base.*;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

public class FastSolution implements Solution{
    final long INF = (long) 1e18;
    void update(long[] t, int v, int tl, int tr, int x, long y) {   // segment tree function,
                                                                    // which update value to y on pos x if
                                                                    // y greater then actual value on pos x
        if (tl == tr) {
            t[v] = Math.max(t[v], y);
        } else {
            int tm = (tl + tr) / 2;
            if (x <= tm) {
                update(t, 2 * v, tl, tm, x, y);
            } else {
                update(t, 2 * v + 1, tm + 1, tr, x, y);
            }
            t[v] = Math.max(t[2 * v], t[2 * v + 1]);
        }
    }
    long get_max(long[] t, int v, int tl, int tr, int l, int r) {  // segment_tree function, which returns max on [l, r] segment
        if (l > r) {
            return -INF;
        }
        if (tl == l && tr == r) {
            return t[v];
        } else {
            int tm = (tl + tr) / 2;
            long res1 = get_max(t, 2 * v, tl, tm, l, Math.min(r, tm));
            long res2 = get_max(t, 2 * v + 1, tm + 1, tr, Math.max(tm + 1, l), r);
            return Math.max(res1, res2);
        }
    }
    @Override
    public long calculateAns(ArrivingTable table) {
        Train[] trains = table.getSortedByTime(); // returns trains in sorted by time order
        int n = trains.length;
        long[][] dp = new long[n][2];
        for (int i = 0; i < n; i++) {
            dp[i][1] = trains[i].getPayment();
        }
        dp[0][0] = 0;
        dp[0][1] = trains[0].getPayment();


        long[] t = new long[4 * n];
        for (int i = 0; i < 4 * n; i++) {
            t[i] = 0;
        }

        for (int i = 0; i < n; i++) {
            int j = getFirstAvailableTrain(trains, i);
            long val = get_max(t, 1, 0, n - 1, 0, i);
            dp[i][1] = Math.max(dp[i][1], val + trains[i].getPayment());
            if (j < n)
                update(t, 1, 0, n - 1, j, dp[i][1]);
            if (i + 1 < n) {
                dp[i + 1][0] = Math.max(dp[i + 1][0], Math.max(dp[i][0], dp[i][1]));
            }
        }
        long ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(dp[i][0], ans);
            ans = Math.max(dp[i][1], ans);
        }
        return ans;
    }
    public int getFirstAvailableTrain(Train[] trains, int x) {  // binarySearch function, which
                                                                // returns first available after i-th train unloading
        int l = x, r = trains.length;
        while (r - l > 1) {
            int m = (l + r) / 2;
            if (checkTimeIntervals(trains[x], trains[m])) {
                r = m;
            } else {
                l = m;
            }
        }
        return r;
    }
    public boolean checkTimeIntervals(Train a, Train b) { // a.getArrivingTime() < b.getArrivingTime()
        if (a == null || b == null) {
            return true;
        }
        int x1 = a.getArrivingTime();
        int y1 = x1 + a.getUnloadingTime();
        int x2 = b.getArrivingTime();
        return (y1 <= x2);
    }
}
