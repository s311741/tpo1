package l1.heap;

import org.jetbrains.annotations.NotNull;

public class BinomialHeap {
  Node root = null;

  public void insert(int key) {
    merge(new Node(key));
  }

  public int extractMin() throws IllegalStateException {
    if (root == null) {
      throw new IllegalStateException("Heap is empty");
    }

    Node prev = null;
    Node min = root;
    Node minPrev = null;
    for (Node current = root; current != null; prev = current, current = current.next) {
      if (current.key < min.key) {
        min = current;
        minPrev = prev;
      }
    }

    if (minPrev == null) {
      root = root.next;
    } else {
      minPrev.next = min.next;
    }
    min.next = null;

    Node child = min.child;
    min.child = null;

    while (child != null) {
      Node next = child.next;
      child.next = null;
      merge(child);
      child = next;
    }

    return min.key;
  }

  public boolean isEmpty() {
    return root == null;
  }

  public void clear() {
    root = null;
  }

  private void merge(@NotNull Node other) {
    if (root == null) {
      root = other;
      return;
    }

    Node out = null;
    Node cur1 = root;
    Node cur2 = other;

    while (cur2 != null) {
      if (cur1.degree < cur2.degree) {
        Node tmp = cur1;
        cur1 = cur2;
        cur2 = tmp;
      }
      if (out == null) {
        root = cur2;
      } else {
        out.next = cur2;
      }
      out = cur2;
      cur2 = cur2.next;
    }

    out.next = cur1;

    Node prev = null;
    out = root;
    while (out.next != null) {
      if (out.degree == out.next.degree) {
        Node parent = out;
        Node child = out.next;

        if (parent.key > child.key) {
          Node tmp = parent;
          parent = child;
          child = tmp;
        }

        parent.next = out.next.next;
        if (prev != null) {
          prev.next = parent;
        }
        child.next = parent.child;
        parent.child = child;
        parent.degree += child.degree;

        if (root == child) {
          root = parent;
        }

        out = parent;
      } else {
        prev = out;
        out = out.next;
      }
    }
  }

  void print(String message) {
    System.out.println("=========== " + message + " ==========");
    if (root == null) {
      System.out.println("(empty)");
    } else {
      root.print(0);
    }
  }
}

class Node {
  Node next = null;
  Node child = null;
  int key;
  int degree = 1;

  Node(int key) {
    this.key = key;
  }

  void print(int depth) {
    for (Node cur = this; cur != null; cur = cur.next) {
      for (int i = 0; i < depth; i++) {
        System.out.print("  ");
      }
      System.out.printf("(%d) %d\n", cur.degree, cur.key);
      if (cur.child != null) {
        cur.child.print(depth + 1);
      }
    }
  }
}