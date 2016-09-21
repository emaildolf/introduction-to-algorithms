/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {

        double deltaY = that.y - this.y;
        double deltaX = that.x - this.x;

        if(deltaY == 0 && deltaX == 0){
            return Double.NEGATIVE_INFINITY;
        }else if(deltaX == 0){
            return Double.POSITIVE_INFINITY;
        }else {
            return deltaY/deltaX;
        }
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {

        if(this.y == that.y){
            return Integer.compare(this.x, that.x);
        }else {
            return Integer.compare(this.y, that.y);
        }
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {

        return new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {

                double slopeP1 = Point.this.slopeTo(p1);
                double slopeP2 = Point.this.slopeTo(p2);

                if(slopeP1 == slopeP2){

                    double distanceP1 = Point.this.distance(p1);
                    double distanceP2 = Point.this.distance(p2);

                    return Double.compare(distanceP1, distanceP2);
                }

                return Double.compare(slopeP1, slopeP2);
            }
        };
    }

    private double distance(Point that) {

        double deltaY = that.y - this.y;
        double deltaX = that.x - this.x;

        return Math.sqrt(deltaY*deltaY + deltaX*deltaX);
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */

        Point p = new Point(10, 0);

        Point a = new Point(3, 4);
        Point b = new Point(7, 3);
        Point c = new Point(6, 7);

        System.out.println(p + " -> " + a + " -> " + p.slopeTo(a));
        System.out.println(p + " -> " + b + " -> " + p.slopeTo(b));
        System.out.println(p + " -> " + c + " -> " + p.slopeTo(c));
    }
}