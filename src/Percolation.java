import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int n;
    private boolean[] open;
    private WeightedQuickUnionUF uf;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {

        if(n <= 0){
            throw new IllegalArgumentException();
        }

        this.n = n;
        this.uf = new WeightedQuickUnionUF(1 + n*n);
        this.open = new boolean[1 + n*n];
        this.open[0] = true;
    }

    private void asseguraLimites(int i, int j){
        asseguraLimites(i);
        asseguraLimites(j);
    }

    private void asseguraLimites(int p){
        if(p < 1 || p > n){
            throw new IndexOutOfBoundsException();
        }
    }

    int toIndex(int i, int j) {
        return (i-1)*n + j - 1 + 1;
    }

    boolean hasNorth(int i){
        return i > 1;
    }

    boolean hasSouth(int i){
        return i < n;
    }

    boolean hasEast(int j){
        return j < n;
    }

    boolean hasWest(int j){
        return j > 1;
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        asseguraLimites(i, j);

        int index = toIndex(i, j);
        this.open[index] = true;

        if(i == 1){
            uf.union(0, index);
        }

        if(hasNorth(i) && isOpen(i-1, j)){
            int northIndex = toIndex(i-1, j);
            uf.union(index, northIndex);
        }

        if(hasSouth(i) && isOpen(i+1, j)){
            int southIndex = toIndex(i+1, j);
            uf.union(index, southIndex);
        }

        if(hasWest(j) && isOpen(i, j-1)){
            int westIndex = toIndex(i, j-1);
            uf.union(index, westIndex);
        }

        if(hasEast(j) && isOpen(i, j+1)){
            int eastIndex = toIndex(i, j+1);
            uf.union(index, eastIndex);
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        asseguraLimites(i, j);
        int index = toIndex(i, j);

        return this.open[index];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        asseguraLimites(i, j);

        int index = toIndex(i, j);

        return uf.connected(0, index);
    }

    // does the system percolate?
    public boolean percolates() {

        for(int k=1; k<=n; k++){

            int bottomIndex = toIndex(n, k);
            if(uf.connected(0, bottomIndex)){
                return true;
            }
        }

        return false;
    }

}
