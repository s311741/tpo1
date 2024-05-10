package l1.heap;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BinomialHeapTest {
  static int[] sortedData;
  static int[] shuffledData;

  @BeforeAll
  static void init() {
    sortedData = new int[200];
    for (int i = 0; i < 100; i++) {
      sortedData[i] = i;
    }
    for (int i = 100; i < 200; i++) {
      sortedData[i] = 900 + i;
    }
    shuffledData = Arrays.copyOf(sortedData, sortedData.length);
    Collections.shuffle(Arrays.asList(shuffledData), new Random(2024));
  }

  @Test
  void emptyThrows() {
    var heap = new BinomialHeap();
    assertThrows(IllegalStateException.class, heap::extractMin);
  }

  private @NotNull BinomialHeap getFilled() {
    var heap = new BinomialHeap();
    for (int i: shuffledData) {
      heap.insert(i);
    }
    return heap;
  }

  @Test
  void heapSortWorks() {
    var heap = getFilled();
    assertFalse(heap::isEmpty);
    heap.print("After insertions");
    for (int i: sortedData) {
      int min = heap.extractMin();
      assertEquals(min, i);
    }
    assertTrue(heap::isEmpty);
    heap.print("Final state");
  }

  @Test
  void duplicates() {
    var heap = new BinomialHeap();
    final int size = 111;
    final int value = 757;
    for (int i = 0; i < size; i++) {
      heap.insert(value);
    }
    for (int i = 0; i < size; i++) {
      assertEquals(heap.extractMin(), value);
    }
  }

  @Test
  void clearingWorks() {
    var heap = getFilled();
    assertFalse(heap::isEmpty);
    heap.clear();
    assertTrue(heap::isEmpty);
  }
}