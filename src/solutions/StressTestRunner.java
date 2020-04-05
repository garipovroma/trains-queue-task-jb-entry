package solutions;

import base.ArrivingTable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class StressTestRunner {
    public static void main(String[] args) {
        try {
            File input = new File("src/solutions/data/test.txt");
            int testsCount = Integer.parseInt(args[2]);
            int maxTrainsCount = Integer.parseInt(args[3]);
            for (int test = 0; test < testsCount; test++) {
                FileWriter fw = new FileWriter(input);
                fw.write(TestGenerator.test(maxTrainsCount));
                fw.close();
                Solution solve = null;
                if (args[0].equals("stupid")) {
                    solve = new StupidSolution();
                } else if (args[0].equals("square")) {
                    solve = new SquareTimeSolution();
                }
                long ans = solve.calculateAns(new ArrivingTable(new Scanner(input)));
                Solution fastSolution = null;
                if (args[1].equals("fast")) {
                    fastSolution = new FastSolution();
                } else if (args[1].equals("square")){
                    fastSolution = new SquareTimeSolution();
                }
                long myAns = fastSolution.calculateAns(new ArrivingTable(new Scanner(input)));
                if (ans != myAns) {
                    System.out.println("WA " + Integer.toString(test + 1) + ", expected : " + ans + " , found:" + myAns);
                    System.exit(0);
                } else {
                    System.out.println("Test #" + Integer.toString(test + 1) + " OK : " + myAns);
                }
            }
            System.out.println("----------------------");
            System.out.println("Tests passed : " + testsCount);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error\nprogram arguments : <checker_solution> <solution_to_stress> <tests count> <max count of trains>");
            System.out.println("checker_solution = stupid or square, where stupid - solution which use O((2 ^ n) * n) time, square - O(n ^ 2) time");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("Error\nYou should change <max count of trains>, because stupid solution can't works with too large count of trains");
        } catch (IOException e) {
            System.out.println("Error\nIt might be FileNotFoundException");
        }
    }
}
