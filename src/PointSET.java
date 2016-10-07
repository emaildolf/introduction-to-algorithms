import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        ensuresNotNull(p);
        points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        ensuresNotNull(p);
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {

        for (Point2D p : points) {
            p.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        ensuresNotNull(rect);
        List<Point2D> inside = new LinkedList<>();

        for (Point2D p : points) {
            if (rect.contains(p)) {
                inside.add(p);
            }
        }

        return inside;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        ensuresNotNull(p);

        Point2D nearest = null;

        for (Point2D point : points) {

            if (nearest == null) {
                nearest = point;
            } else if (p.distanceSquaredTo(point) < p.distanceSquaredTo(nearest)) {
                nearest = point;
            }
        }

        return nearest;
    }

    private void ensuresNotNull(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }

}
