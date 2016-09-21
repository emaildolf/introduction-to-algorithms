import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {

    private List<LineSegment> segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        ensureArgumentNotNull(points);
        ensureUniquePoints(points);

        segments = new LinkedList<>();

        Point[] sortedPoints = Arrays.copyOf(points, points.length);

        for(int p=0; p<points.length; p++){

            Point point = points[p];
            Arrays.sort(sortedPoints, point.slopeOrder());

            double slope = point.slopeTo(point);
            int count = 0;
            for(int i=1; i<sortedPoints.length; i++){

                double newSlope = point.slopeTo(sortedPoints[i]);
                if(newSlope == slope){
                    count++;
                }else {

                    if(count >= 3){
                        segments.add(new LineSegment(point, sortedPoints[i-1]));
                    }

                    slope = newSlope;
                    count = 1;
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