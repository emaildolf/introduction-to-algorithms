import collinear.Point;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.LinkedList;
import java.util.List;

public class KdTree {

    private Node root;
    private RectHV plane;

    // construct an empty set of points
    public KdTree() {
        this.plane = new RectHV(0, 0, 1, 1);
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size(root);
    }

    private int size(Node node) {

        if(node == null){
            return 0;
        }

        return size(node.lb) + size(node.rt) + 1;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {

        ensuresNotNull(p);
        root = insert(p, root, plane, true);
    }

    private Node insert(Point2D p, Node node, RectHV parentRect, boolean isVertical) {

        if(node == null){
            return new Node(p, parentRect,isVertical);
        }

        if(!node.point.equals(p)){
            if(node.isLeft(p)){
                node.lb = insert(p, node.lb, node.leftRect(), !isVertical);
            }else {
                node.rt = insert(p, node.rt, node.rightRect(parentRect), !isVertical);
            }
        }

        return node;

    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        ensuresNotNull(p);
        return contains(p, root);
    }

    private boolean contains(Point2D p, Node node) {

        if(node == null) {
            return false;
        }

        if(p.equals(node.point)){
            return true;
        }

        if(node.isLeft(p)){
            return contains(p, node.lb);
        }else {
            return contains(p, node.rt);
        }

    }

    // draw all points to standard draw
    public void draw() {
        draw(root, true);
    }

    private void draw(Node node, boolean isVertical) {

        if(node == null){
            return;
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.point.draw();

        StdDraw.setPenRadius();

        RectHV r = node.leftRect();
        if(isVertical){
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(r.xmax(), r.ymin(), r.xmax(), r.ymax());
        }else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(r.xmin(), r.ymax(), r.xmax(), r.ymax());
        }


        draw(node.lb, !isVertical);
        draw(node.rt, !isVertical);
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV query) {

        List<Point2D> points = new LinkedList<>();
        range(root, plane, query, points);

        return points;
    }

    private void range(Node node, RectHV parentRect, RectHV query, List<Point2D> points) {

        if(node == null) {
            return;
        }

        if(query.contains(node.point)){
            points.add(node.point);
        }

        if(node.leftRect().intersects(query)){
            range(node.lb, node.leftRect(), query, points);
        }

        if(node.rightRect(parentRect).intersects(query)){
            range(node.rt, node.rightRect(parentRect), query, points);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D query) {

        if(isEmpty()){
            return null;
        }

        Point2D best = new Point2D(1000, 1000);
        return nearest(root, plane, query, best);
    }

    private Point2D nearest(Node node, RectHV parentRect, Point2D query, Point2D best) {

        if(node == null){
            return best;
        }

        double distance = node.point.distanceSquaredTo(query);
        double bestDistance = best.distanceSquaredTo(query);

        if(distance < bestDistance) {
            best = node.point;
        }

        double bestDistanceFromLeft = node.leftRect().distanceSquaredTo(query);
        double bestDistanceFromRight = node.rightRect(parentRect).distanceSquaredTo(query);


        if(bestDistanceFromLeft < bestDistanceFromRight){

            //should go to the left first
            best = nearest(node.lb, node.leftRect(), query, best);

            //is the best distance from right better than my best distance?
            if(bestDistanceFromRight < best.distanceSquaredTo(query)){
                best = nearest(node.rt, node.rightRect(parentRect), query, best);
            }

        }else{

            //should go to the right first
            best = nearest(node.rt, node.rightRect(parentRect), query, best);

            //is the best distance from the left better than my best distance?
            if(bestDistanceFromRight < best.distanceSquaredTo(query)){
                best = nearest(node.lb, node.leftRect(), query, best);
            }
        }

        return best;
    }

    private class Node {

        private Node lb, rt;
        private RectHV leftRect;
        private Point2D point;

        public Node(Point2D point, RectHV parent, boolean isVertical){
            this.point = point;
            leftRect = KdTree.this.split(parent, point, isVertical);
        }

        public boolean isLeft(Point2D p){
            return leftRect.contains(p);
        }

        public boolean isRight(Point2D p){
            return !isLeft(p);
        }

        public RectHV leftRect() {
            return leftRect;
        }

        public RectHV rightRect(RectHV parentRect) {
            return KdTree.this.complement(parentRect, leftRect);
        }
    }

    private RectHV complement(RectHV parent, RectHV lb) {

        if(parent.ymax() == lb.ymax()){
            //vertical
            return new RectHV(  lb.xmax(), //xmin
                                lb.ymin(),  //ymin
                                parent.xmax(), //xmax
                                parent.ymax()); //ymax
        }else {
            //horizontal
            return new RectHV(  lb.xmin(), //xmin
                                lb.ymax(),  //ymin
                                lb.xmax(), //xmax
                                parent.ymax()); //ymax
        }
    }

    private RectHV split(RectHV parent, Point2D point, boolean isVertical) {

        if(isVertical){
            return new RectHV(  parent.xmin(),
                                parent.ymin(),
                                point.x(),
                                parent.ymax());
        }else {
            return new RectHV(  parent.xmin(),
                                parent.ymin(),
                                parent.xmax(),
                                point.y());
        }
    }

    private void ensuresNotNull(Object o) {
        if(o == null) {
            throw new NullPointerException();
        }
    }

    private static void testRectangelComplement(KdTree tree) {

        //test rectangle complement with full plane (0,0) - (1,1)
        RectHV parent = new RectHV(0, 0, 1, 1);
        RectHV left = new RectHV(0, 0, 0.5, 1);
        RectHV right = new RectHV(0.5, 0, 1, 1);

        assert tree.complement(parent, left).equals(right);

        RectHV top = new RectHV(0, 0.5, 1, 1);
        RectHV bottom = new RectHV(0, 0, 1, 0.5);

        assert tree.complement(parent, bottom).equals(top);

        //test complement with plane subset (0.2 , 0.2) - (0.5, 0.5)
        parent = new RectHV(0.2, 0.2, 0.5, 0.5);
        left = new RectHV(0.2, 0.2, 0.4, 0.5);
        right = new RectHV(0.4, 0.2, 0.5, 0.5);
        top = new RectHV(0.2, 0.3, 0.5, 0.5);
        bottom = new RectHV(0.2, 0.2, 0.5, 0.3);

        assert tree.complement(parent, left).equals(right);
        assert tree.complement(parent, bottom).equals(top);

        System.out.println("Rectangle Complement ok");
    }

    private static void testRectangleSplit(KdTree tree) {

        RectHV parent = new RectHV(0, 0, 1, 1);
        Point2D point = new Point2D(0.5, 0.5);
        RectHV left = new RectHV(0, 0, 0.5, 1);
        RectHV bottom = new RectHV(0, 0, 1, 0.5);

        assert tree.split(parent, point, true).equals(left);
        assert tree.split(parent, point, false).equals(bottom);

        parent = new RectHV(0.2, 0.2, 0.5, 0.5);
        point = new Point2D(0.4, 0.3);
        left = new RectHV(0.2, 0.2, 0.4, 0.5);
        bottom = new RectHV(0.2, 0.2, 0.5, 0.3);

        assert tree.split(parent, point, true).equals(left);
        assert tree.split(parent, point, false).equals(bottom);

        System.out.println("Rectangle Split ok");
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

        KdTree tree = new KdTree();

        testRectangelComplement(tree);
        testRectangleSplit(tree);

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
        List<Point2D> points = (List<Point2D>)tree.range(plane);
        assert points.size() == 5;
        assert points.contains(p1);
        assert points.contains(p2);
        assert points.contains(p3);
        assert points.contains(p4);
        assert points.contains(p5);

        RectHV subPlane = new RectHV(0, 0, 0.3, 0.5);
        points = (List<Point2D>)tree.range(subPlane);
        assert points.size() == 1;
        assert points.contains(p3);

        subPlane = new RectHV(0.3, 0.2, 0.6, 0.8);
        points = (List<Point2D>)tree.range(subPlane);
        assert points.size() == 2;
        assert points.contains(p2);
        assert points.contains(p4);

        //test nearest
        Point2D query = new Point2D(0, 0);
        Point2D nearest = tree.nearest(query);
        assert p3.equals(nearest);
    }
}
