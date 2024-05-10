package l1;

import org.junit.jupiter.api.Test;

import static java.lang.Math.incrementExact;
import static l1.MyMath.*;
import static org.junit.jupiter.api.Assertions.*;
import static java.lang.Math.PI;

public class MyMathTest {
  @Test
  void domain() {
    final var signs = new int[]{-1, 1};
    final var iae = IllegalArgumentException.class;
    for (int terms = 1; terms <= MAX_ARCCOS_TERMS; terms++) {
      final int t = terms;
      for (var sign : signs) {
        assertThrows(iae, () -> arccos(t, sign * 1.1));
        assertThrows(iae, () -> arccos(t, sign * 100));
        assertDoesNotThrow(() -> arccos(t, sign * 0.9));
        assertDoesNotThrow(() -> arccos(t, sign * 1.0));
      }
      assertDoesNotThrow(() -> arccos(t, 0));
    }
    assertThrows(iae, () -> arccos(0, 0));
    assertThrows(iae, () -> arccos(-1, 0));
    assertThrows(iae, () -> arccos(MAX_ARCCOS_TERMS + 1, 0));
    assertThrows(iae, () -> arccos(MAX_ARCCOS_TERMS + 10, 0));
  }

  private void assertEpsilon(double a, double b, double epsilon) {
    double diff = Math.abs(a - b);
    assertTrue(diff <= epsilon, String.format("(%f - %f) = %f > %f", a, b, diff, epsilon));
  }

  @Test
  void extremes() {
    assertEpsilon(arccos(9, 1.0), 0.0, 0.2);
    assertEpsilon(arccos(9, -1.0), PI, 0.2);

    assertEpsilon(arccos(8, 1.0), 0.0, 0.25);
    assertEpsilon(arccos(8, -1.0), PI, 0.25);

    assertEpsilon(arccos(7, 1.0), 0.0, 0.3);
    assertEpsilon(arccos(7, -1.0), PI, 0.3);
  }

  @Test
  void nearZero() {
    for (int terms = 1; terms <= MAX_ARCCOS_TERMS; terms++) {
      assertEquals(arccos(terms, 0), PI * 0.5);
    }
    assertEpsilon(arccos(9, 0.01), HALF_PI, 0.1);
    assertEpsilon(arccos(9, -0.01), HALF_PI, 0.1);
  }

  @Test
  void roundtrip() {
    for (double x = -1.0; x <= 1.0; x += 0.01) {
      assertEpsilon(Math.cos(arccos(MAX_ARCCOS_TERMS, x)), x, 0.02);
    }
    for (double x = 0.0; x <= PI; x += 0.01) {
      assertEpsilon(arccos(MAX_ARCCOS_TERMS, Math.cos(x)), x, 0.2);
    }
  }
}