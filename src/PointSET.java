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
    // unit testing of the methods (optional)
    public static void main(String[] args) {

        PointSET tree = new PointSET();

        //test insert and contains
        Point2D p1 = new Point2D(0.7, 0.2);
        Point2D p2 = new Point2D(0.5, 0.4);
        Point2D p3 = new Point2D(0.2, 0.3);
        Point2D p4 = new Point2D(0.4, 0.7);
        Point2D p5 = new Point2D(0.9, 0.6);

        tree.insert(p1);
        tree.insert(p2);
        tree.insert(p3);
        tree.insert(p4);
        tree.insert(p5);
        tree.insert(p5);

        assert tree.size() == 5;
        assert tree.contains(p1);
        assert tree.contains(p2);
        assert tree.contains(p3);
        assert tree.contains(p4);
        assert tree.contains(p5);
        assert !tree.contains(new Point2D(0.5, 0.5));

        //test range
        RectHV plane = new RectHV(0, 0, 1, 1);
        List<Point2D> points = (List<Point2D>) tree.range(plane);
        assert points.size() == 5;
        assert points.contains(p1);
        assert points.contains(p2);
        assert points.contains(p3);
        assert points.contains(p4);
        assert points.contains(p5);

        RectHV subPlane = new RectHV(0, 0, 0.3, 0.5);
        points = (List<Point2D>) tree.range(subPlane);
        assert points.size() == 1;
        assert points.contains(p3);

        subPlane = new RectHV(0.3, 0.2, 0.6, 0.8);
        points = (List<Point2D>) tree.range(subPlane);
        assert points.size() == 2;
        assert points.contains(p2);
        assert points.contains(p4);

        //test nearest
        Point2D query = new Point2D(0, 0);
        Point2D nearest = tree.nearest(query);
        assert p3.equals(nearest);
    }

}
