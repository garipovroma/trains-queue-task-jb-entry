package solutions;

import java.util.Random;

public class TestGenerator {
    public static String test(int maxN) {
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder res = new StringBuilder();
        int n = rand.nextInt(maxN) + 1;
        res.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            int a = i;
            int b = rand.nextInt(1000) + 1;
            int c = rand.nextInt(1000);
            int d = rand.nextInt(1000);
            res.append(a).append(" ").append(b).append(" ").append(c).append(" ").append(d).append("\n");
        }
        return res.toString();
    }
}
