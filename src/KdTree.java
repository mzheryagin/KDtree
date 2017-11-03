import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

/**
 * Write a mutable data type KdTree.java that uses a 2d-tree to implement
 * the same API (but replace PointSET with KdTree). A 2d-tree is a generalization of a BST to two-dimensional keys.
 * The idea is to build a BST with points in the nodes, using the x- and y-coordinates of the points
 * as keys in strictly alternating sequence.
 */
public class KdTree {

  private int size;

  // construct an empty set of points
  public KdTree() {

  }

  // is the set empty?
  public boolean isEmpty() {
    return size == 0;
  }

  // number of points in the set
  public int size() {
    return size;
  }

  // add the point to the set (if it is not already in the set)
  public void insert(Point2D p) {

  }

  // does the set contain point p?
  public boolean contains(Point2D p) {

  }

  // draw all points to standard draw
  public void draw() {

  }

  // all points that are inside the rectangle (or on the boundary)
  public Iterable<Point2D> range(RectHV rect) {

  }

  // a nearest neighbor in the set to point p; null if the set is empty
  public Point2D nearest(Point2D p) {

  }

  private static class Node {
    private Point2D p;      // the point
    private RectHV rect;    // the axis-aligned rectangle corresponding to this node
    private Node lb;        // the left/bottom subtree
    private Node rt;        // the right/top subtree

    public Node () {

    }
  }

  // unit testing of the methods (optional)
  public static void main(String[] args) {

  }
}

