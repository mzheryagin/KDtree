import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

/**
 * A mutable data type PointSET.java that represents a set of points in the unit square.
 * Implement the following API by using a red–black BST
 * You must use either SET or java.util.TreeSet; do not implement your own red–black BST.
 */
public class PointSET {

  private SET<Point2D> pSet;

  // construct an empty set of points
  public PointSET() {
    pSet = new SET<>();
  }

  // is the set empty?
  public boolean isEmpty() {
    return pSet.isEmpty();
  }

  // number of points in the set
  public int size() {
    return pSet.size();
  }

  // add the point to the set (if it is not already in the set)
  public void insert(Point2D p) {
    if (p == null) {
      throw new java.lang.IllegalArgumentException();
    }
    if (!pSet.contains(p)) {
      pSet.add(p);
    }
  }

  // does the set contain point p?
  public boolean contains(Point2D p) {
    if (p == null) {
      throw new java.lang.IllegalArgumentException();
    }
    return pSet.contains(p);
  }

  // draw all points to standard draw
  public void draw() {
    for (Point2D p: pSet) {
      p.draw();
    }
  }

  // all points that are inside the rectangle (or on the boundary)
  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) {
      throw new java.lang.IllegalArgumentException();
    }
    ArrayList<Point2D> pList = new ArrayList<>();
    for (Point2D p: pSet) {
      if (rect.contains(p)) {
        pList.add(p);
      }
    }
    return pList;
  }

  // a nearest neighbor in the set to point p; null if the set is empty
  public Point2D nearest(Point2D p) {
    if (p == null) {
      throw new java.lang.IllegalArgumentException();
    }
    if (pSet.isEmpty()) {
      return null;
    }
    Point2D nearestPoint = null;
    double minDistance = Double.POSITIVE_INFINITY;
    for (Point2D point: pSet) {
      double distance = p.distanceTo(point);
      if (distance < minDistance) {  //possibly needs && !p.equals(point) if p is in the set
        nearestPoint = p;
        minDistance = distance;
      }
    }
    return nearestPoint;
  }


  // unit testing of the methods (optional)
  public static void main(String[] args) {

  }
}