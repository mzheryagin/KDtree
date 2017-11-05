import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

/**
 * Write a mutable data type KdTree.java that uses a 2d-tree to implement
 * the same API (but replace PointSET with KdTree). A 2d-tree is a generalization of a BST to two-dimensional keys.
 * The idea is to build a BST with points in the nodes, using the x- and y-coordinates of the points
 * as keys in strictly alternating sequence.
 */
public class KdTree {
  private static final boolean VERTICAL = true;
  private static final boolean HORIZONTAL = false;
  private Node root;
  private int size;

  // construct an empty set of points
  public KdTree() {
    root = null;
    size = 0;
  }

  // is the set empty?
  public boolean isEmpty() {
    return size == 0;
  }

  // number of points in the set
  public int size() {
    return size;
  }

  private Node insert(Node n, Point2D p, boolean orient,
                      double xmin, double ymin, double xmax, double ymax) {
    if (n == null) {
      size++;
      return new Node(p, orient, new RectHV(xmin, ymin, xmax, ymax));
    }
    if (orient == VERTICAL) {
      if (p.x() < n.p.x()) {
        insert(n.lb, p, HORIZONTAL, n.rect.xmin(), n.rect.ymin(), n.p.x(), n.rect.ymax());
      } else {
        insert(n.rt, p, HORIZONTAL, n.p.x(), n.rect.ymin(), n.rect.xmax(), n.rect.ymax());
      }
    } else {
      if (p.y() < n.p.y()) {
        insert(n.lb, p, VERTICAL, n.rect.xmin(), n.rect.ymin(), n.rect.xmax(), n.p.y());
      } else {
        insert(n.rt, p, VERTICAL, n.rect.xmin(), n.p.y(), n.rect.xmax(), n.rect.ymax());
      }
    }
    n.p = p;
    n.o = orient;
    return n;
  }

  // add the point to the set (if it is not already in the set)
  public void insert(Point2D p) {
    if (p == null) {
      throw new java.lang.IllegalArgumentException();
    }
    if (!contains(root, p)) {
      root = insert(root, p, VERTICAL, 0,0,1,1);
    }
  }

  private boolean contains(Node n, Point2D p) {
    if (n == null) {
      return false;
    }
    if (p.equals(n.p)) {
      return true;
    }
    if (n.isVertical()) {
      if (p.x() < n.p.x()) {
        return contains(n.lb, p);
      } else {
        return contains(n.rt, p);
      }
    } else {
      if (p.y() < n.p.y()) {
        return contains(n.lb, p);
      } else {
        return contains(n.rt, p);
      }
    }
  }

  // does the set contain point p?
  public boolean contains(Point2D p) {
    if (p == null) {
      throw new java.lang.IllegalArgumentException();
    }
    return contains(root, p);
  }

  private void draw(Node n, boolean orientation) {
    if (orientation == VERTICAL) {
      StdDraw.setPenRadius();
      StdDraw.setPenColor(StdDraw.RED);
      StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
    } else {
      StdDraw.setPenRadius();
      StdDraw.setPenColor(StdDraw.BLUE);
      StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
    }

    if (n.lb != null) {
      draw(n.lb, !orientation);
    }

    if (n.rt != null) {
      draw(n.rt, !orientation);
    }

    StdDraw.setPenRadius(0.01);
    StdDraw.setPenColor(StdDraw.BLACK);
    n.p.draw();
  }

  // draw all points to standard draw
  public void draw() {
    draw(root, VERTICAL);
  }

  private void range(Node n, RectHV rect, ArrayList<Point2D> list) {
    if (n == null) {
      return;
    }
    if (!n.rect.intersects(rect)) {
      return;
    }
    if (rect.contains(n.p)) {
      list.add(n.p);
    }

    range(n.lb, rect, list);
    range(n.rt, rect, list);
  }

  // all points that are inside the rectangle (or on the boundary)
  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) {
      throw new java.lang.IllegalArgumentException();
    }
    ArrayList<Point2D> pList = new ArrayList<>();
    range(root, rect, pList);
    return pList;
  }

  private Point2D nearest(Node n, Point2D p, double distance) {
    if (n == null) {
      return null;
    }

    if (n.rect.distanceTo(p) >= distance) {
      return null;
    }

    Point2D nearestPoint = null;
    double nearestDistance = distance;
    double d;

    d = p.distanceTo(n.p);
    if (d < nearestDistance) {
      nearestPoint = n.p;
      nearestDistance = d;
    }

    // Choose subtree that is closer to point.
    Node firstNode = n.lb;
    Node secondNode = n.rt;

    if (firstNode != null && secondNode != null) {
      if (firstNode.rect.distanceTo(p) > secondNode.rect.distanceTo(p)) {
        firstNode = n.rt;
        secondNode = n.lb;
      }
    }

    Point2D nearestP1 = nearest(firstNode, p, nearestDistance);
    if (nearestP1 != null) {
      d = p.distanceTo(nearestP1);
      if (d < nearestDistance) {
        nearestPoint = nearestP1;
        nearestDistance = d;
      }
    }

    Point2D nearestP2 = nearest(secondNode, p, nearestDistance);
    if (nearestP2 != null) {
      d = p.distanceTo(nearestP2);
      if (d < nearestDistance) {
        nearestPoint = nearestP2;
        nearestDistance = d;
      }
    }

    return nearestPoint;
  }

  // a nearest neighbor in the set to point p; null if the set is empty
  public Point2D nearest(Point2D p) {
    if (p == null) {
      throw new java.lang.IllegalArgumentException();
    }
    return nearest(root, p, Double.POSITIVE_INFINITY);

  }

  private static class Node {

    private Point2D p;      // the point
    private RectHV rect;    // the axis-aligned rectangle corresponding to this node
    private Node lb;        // the left/bottom subtree
    private Node rt;        // the right/top subtree
    private boolean o;      // orientation

    public Node (Point2D point, boolean orientation, RectHV r) {
      p = point;
      o = orientation;
      rect = r;
    }

    private boolean isVertical () {
      return o;
    }
  }

  // unit testing of the methods (optional)
  public static void main(String[] args) {

  }
}

