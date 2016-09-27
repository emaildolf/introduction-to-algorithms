import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Solver {

    private MinPQ<SearchNode> queue;
    private LinkedList<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        queue = new MinPQ<>();
        queue.insert(new SearchNode(null, initial));

        SearchNode node = queue.delMin();
        while(!node.isGoal()){

            for(SearchNode neightbor : node.neightbors()){
                queue.insert(neightbor);
            }

            node = queue.delMin();
        }

        solution = new LinkedList<>();
        while(node != null){
            solution.addFirst(node.board);
            node = node.prev;
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return false;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solution.size();
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    private class SearchNode implements Comparable<SearchNode> {

        private SearchNode prev;
        private Board board;

        public SearchNode(SearchNode prev, Board board) {
            this.prev = prev;
            this.board = board;
        }

        public Iterable<SearchNode> neightbors() {

            List<SearchNode> neightbors = new LinkedList<>();

            for(Board b : board.neighbors()){
                if(!b.equals(prevBoard())){
                    neightbors.add(new SearchNode(this, b));
                }
            }

            return neightbors;
        }

        private Board prevBoard() {
            if(prev == null) {
                return null;
            }else {
                return prev.board;
            }
        }

        public int distance() {
            return board.manhattan();
        }

        public boolean isGoal() {
            return board.isGoal();
        }

        @Override
        public int compareTo(SearchNode o) {
            return Integer.compare(this.distance(), o.distance());
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        Board board = new Board(new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}});
        Solver solver = new Solver(board);

        for(Board b : solver.solution()){
            System.out.println(b);
        }
    }
}