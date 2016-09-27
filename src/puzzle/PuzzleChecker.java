package puzzle; /******************************************************************************
 *  Compilation:  javac puzzle.PuzzleChecker.java
 *  Execution:    java puzzle.PuzzleChecker filename1.txt filename2.txt ...
 *  Dependencies: puzzle.Board.java puzzle.Solver.java
 *
 *  This program creates an initial board from each filename specified
 *  on the command line and finds the minimum number of moves to
 *  reach the goal state.
 *
 *  % java puzzle.PuzzleChecker puzzle*.txt
 *  puzzle00.txt: 0
 *  puzzle01.txt: 1
 *  puzzle02.txt: 2
 *  puzzle03.txt: 3
 *  puzzle04.txt: 4
 *  puzzle05.txt: 5
 *  puzzle06.txt: 6
 *  ...
 *  puzzle3x3-impossible: -1
 *  ...
 *  puzzle42.txt: 42
 *  puzzle43.txt: 43
 *  puzzle44.txt: 44
 *  puzzle45.txt: 45
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PuzzleChecker {

    public static void main(String[] args) {

        File dir = new File(args[0]);

        if(dir.isDirectory()){

            int poolSize = Runtime.getRuntime().availableProcessors();
            ExecutorService pool = Executors.newFixedThreadPool(poolSize);

            System.out.println("Pool size: " + poolSize);

            for (File file : dir.listFiles()) {

                final File f = file;
                pool.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            solveFile(f);
                        }
                        catch(Throwable e) {
                            System.err.println("Erro processando " + f.getPath());
                        }
                    }
                });
            }
        }else{
            solveFile(dir);
        }
    }

    private static void solveFile(File file) {
        // read in the board specified in the filename
        In in = new In(file.getPath());
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        // solve the slider puzzle
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        StdOut.println(file.getName() + ": " + solver.moves());
    }
}
