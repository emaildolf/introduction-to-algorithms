package collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {

    private List<LineSegment> segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        ensureArgumentNotNull(points);
        ensureUniquePoints(points);

        segments = new LinkedList<>();
        Point[] naturalOrder = Arrays.copyOf(points, points.length);
        Arrays.sort(naturalOrder);

        for (int p = 0; p < naturalOrder.length; p++) {

            Point point = naturalOrder[p];

            Point[] slopeOrder = Arrays.copyOf(naturalOrder, naturalOrder.length);
            Arrays.sort(slopeOrder, point.slopeOrder());

            int count = 1;
            for (int i = 1; i < slopeOrder.length-1; i++) {

                double currentSlope = point.slopeTo(slopeOrder[i]);
                double nextSlope = point.slopeTo(slopeOrder[i+1]);


                if (currentSlope == nextSlope) {

                    if(point.compareTo(slopeOrder[i]) > 0){
                        count = -slopeOrder.length;
                    }else {
                        count++;
                    }
                }

                if (currentSlope != nextSlope) {
                    if(count >= 3){
                        segments.add(new LineSegment(point, slopeOrder[i]));
                    }
                    count = 1;
                }
                else if(i == slopeOrder.length - 2 && count >= 3){
                    segments.add(new LineSegment(point, slopeOrder[i+1]));
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        }
    }

}