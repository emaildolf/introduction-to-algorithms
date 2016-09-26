import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board {

    private int[][] blocks;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.blocks = blocks;
    }

    // board dimension n
    public int dimension() {
        return blocks[0].length;
    }

    // number of blocks out of place
    public int hamming() {

        int count = 0;
        for(int i=0; i<dimension(); i++){
            for(int j=0; j<dimension(); j++){

                if(blocks[i][j]!=0 && goalFor(i, j) != blocks[i][j]){
                    count++;
                }
            }
        }

        return count;
    }

    private int goalFor(int i, int j){
        return i*dimension() + j + 1;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {

        int count = 0;
        for(int i=0; i<dimension(); i++){
            for(int j=0; j<dimension(); j++){

                if(blocks[i][j] != 0) {
                    int goalX = goalX(blocks[i][j]);
                    int xDiff = Math.abs(goalX - j);
                    count += xDiff;

                    int goalY = goalY(blocks[i][j]);
                    int yDiff = Math.abs(goalY - i);
                    count += yDiff;
                }
            }
        }

        return count;
    }

    private int goalX(int value) {
        return (value-1)%dimension();
    }

    private int goalY(int value) {
        return (value-1)/dimension();
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {

        Random r = new Random();

        int x1, y1, x2, y2;
        do{
            x1 = r.nextInt(dimension());
            y1 = r.nextInt(dimension());
            x2 = r.nextInt(dimension());
            y2 = r.nextInt(dimension());
        }
        while(x1 == x2 && y1 == y2);

        Board twin = cloneBoard();
        twin.swap(x1, y1, x2, y2);

        return twin;
    }

    private void swap(int x1, int y1, int x2, int y2) {

        int tmp = blocks[x1][y1];
        blocks[x1][y1] = blocks[x2][y2];
        blocks[x2][y2] = tmp;
    }

    // does this board equal y?
    public boolean equals(Object other) {

        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;

        Board otherBoard = (Board)other;

        return Arrays.deepEquals(blocks, otherBoard.blocks);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        int i = 0;
        int j = 0;
        while(blocks[i][j] != 0){
            i++;
            if(i == dimension()){
                i = 0;
                j++;
            }
        }

        List<Board> neighbors = new ArrayList<>();

        if(hasNorthNeightbor(i)){
            neighbors.add(northNeighbor(i, j));
        }

        if(hasSouthNeightbor(i)){
            neighbors.add(southNeighbor(i, j));
        }

        if(hasEastNeightbor(j)){
            neighbors.add(eastNeighbor(i, j));
        }

        if(hasWestNeighbor(j)){
            neighbors.add(westNeighbor(i, j));
        }

        return neighbors;
    }

    private boolean hasNorthNeightbor(int y0) {
        return y0 > 0;
    }

    private Board northNeighbor(int x0, int y0){
        Board north = cloneBoard();
        north.swap(x0, y0, x0, y0-1);

        return north;
    }

    private boolean hasSouthNeightbor(int y0){
        return y0 + 1 < dimension();
    }

    private Board southNeighbor(int x0, int y0){
        Board south = cloneBoard();
        south.swap(x0, y0, x0, y0+1);

        return south;
    }

    private boolean hasEastNeightbor(int x0) {
        return x0 + 1 < dimension();
    }

    private Board eastNeighbor(int x0, int y0){
        Board east = cloneBoard();
        east.swap(x0, y0, x0+1, y0);

        return east;
    }

    private boolean hasWestNeighbor(int x0){
        return x0 > 0;
    }

    private Board westNeighbor(int x0, int y0){
        Board west = cloneBoard();
        west.swap(x0, y0, x0-1, y0);

        return west;
    }

    private Board cloneBoard() {

        int[][] cloneBlocks = new int[dimension()][dimension()];
        for(int i=0; i<dimension(); i++){
            for(int j=0; j<dimension(); j++){
                cloneBlocks[i][j] = blocks[i][j];
            }
        }

        return new Board(cloneBlocks);
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension() + "\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {

        Board goal = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        assert goal.dimension() == 3;
        assert goal.isGoal();
        assert goal.toString().equals("3\n 1  2  3 \n 4  5  6 \n 7  8  0 \n");

        Board noGoal = goal.cloneBoard();
        noGoal.swap(0, 0, 1, 1);
        assert !noGoal.isGoal();
        assert noGoal.hamming() == 2;
        assert noGoal.manhattan() == 4;

        assert noGoal.equals(noGoal.cloneBoard());
        assert !noGoal.equals(goal);
        assert !noGoal.equals(noGoal.twin());


        Board hole = new Board(new int[][]{{1, 2, 3},{4, 0, 5},{6, 7, 8}});

        List<Board> neigthbors = (List<Board>)hole.neighbors();
        assert neigthbors.size() == 4;

        Board north = new Board(new int[][]{{1, 0, 3}, {4, 2, 5}, {6, 7, 8}});
        neigthbors.contains(north);

        Board south = new Board(new int[][]{{1, 2, 3}, {4, 7, 5}, {6, 0, 8}});
        neigthbors.contains(south);

        Board east = new Board(new int[][]{{1, 2, 3}, {0, 4, 5}, {6, 7, 8}});
        neigthbors.contains(east);

        Board west = new Board(new int[][]{{1, 2, 3}, {4, 5, 0}, {6, 7, 8}});
        neigthbors.contains(west);

        System.out.println("ok!");
    }
}