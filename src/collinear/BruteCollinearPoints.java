package collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

public class BruteCollinearPoints {

    private List<LineSegment> segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] ps) {

        ensureArgumentNotNull(ps);
        ensureUniquePoints(ps);

        segments = new LinkedList<>();

        Point[] points = Arrays.copyOf(ps, ps.length);

        Arrays.sort(points);

        for (int p = 0; p < points.length; p++) {
            for (int q = p + 1; q < points.length; q++) {
                for (int r = q + 1; r < points.length; r++) {
                    for (int s = r + 1; s < points.length; s++) {

                        double slopeQ = points[p].slopeTo(points[q]);
                        double slopeR = points[p].slopeTo(points[r]);
                        double slopeS = points[p].slopeTo(points[s]);

                        if (slopeQ == slopeR && slopeR == slopeS) {
                            segments.add(new LineSegment(points[p], points[s]));
                        }
                    }
                }
            }
        }
    }

    private void ensureArgumentNotNull(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }
    }

    private void ensureUniquePoints(Point[] points) {

        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (i != j && points[i].equals(points[j])) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        }
    }
}
