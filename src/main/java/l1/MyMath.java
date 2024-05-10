package l1;

public class MyMath {
  public static final int MAX_ARCCOS_TERMS = 10;
  public static final double HALF_PI = 0.5 * Math.PI;

  public static double arccos(int terms, double x) throws IllegalArgumentException {
    if (x < -1.0 || x > 1.0 || terms <= 0 || terms > MAX_ARCCOS_TERMS) {
      throw new IllegalArgumentException();
    }

    double acc_x_2n_p1 = x; // x^(2n+1)
    long acc_n_fact = 1; // n!
    long acc_2n_fact = 1; // (2n)!
    long acc_2_n = 1; // 2^n;

    double sum = 0.0;
    for (long n = 0; n <= terms; n++) {
      final double numerator = acc_2n_fact * acc_x_2n_p1;
      final double w = acc_2_n * acc_n_fact;
      final double denominator = w * w * (2 * n + 1);
      sum += numerator / denominator;

      acc_x_2n_p1 *= x * x;
      acc_2_n *= 2;
      acc_n_fact *= n + 1;
      acc_2n_fact *= (2 * n + 1) * (2 * n + 2);
    }

    return HALF_PI - sum;
  }
}