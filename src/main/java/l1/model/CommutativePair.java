package l1.model;

record CommutativePair(int a, int b) {
  CommutativePair {
    if (a > b) {
      int tmp = a;
      a = b;
      b = tmp;
    }
  }
}
