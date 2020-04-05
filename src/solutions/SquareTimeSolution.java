package solutions;

import base.*;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

public class SquareTimeSolution implements Solution {
    @Override
    public long calculateAns(ArrivingTable table) {
        Train[] trains = table.getSortedByTime(); // returns trains in sorted by time order
        int n = trains.length;
        final long INF = (long) 1e18;
        long[][] dp = new long[n][2];
        for (int i = 0; i < n; i++) {
            dp[i][1] = trains[i].getPayment();
        }
        dp[0][0] = 0;
        dp[0][1] = trains[0].getPayment();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++)
                if (j < n) {
                    if (checkTimeIntervals(trains[i], trains[j])) {
                        dp[j][1] = Math.max(dp[j][1], dp[i][1] + trains[j].getPayment());
                    }
                    dp[j][0] = Math.max(dp[j][0], Math.max(dp[i][0], dp[i][1]));
                }
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
    public boolean checkTimeIntervals(Train a, Train b) {   // returns true, if time-segments intersection is empty
        // a.getArrivingTime() < b.getArrivingTime()
                                                            // a.getArrivingTime() < b.getArrivingTime()
        if (a == null || b == null) {
            return true;
        }
        int x1 = a.getArrivingTime();
        int y1 = x1 + a.getUnloadingTime();
        int x2 = b.getArrivingTime();
        return (y1 <= x2);
    }
}
