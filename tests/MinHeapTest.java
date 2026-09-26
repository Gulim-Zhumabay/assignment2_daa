import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class MinHeapTest {
    @Test
    void insertAndPeek() {
        MinHeap h = new MinHeap();
        h.insert(5);
        h.insert(2);
        h.insert(8);
        assertEquals(2, h.peekMin());
        assertEquals(3, h.size());
    }

    @Test
    void extractsInSortedOrder() {
        MinHeap h = new MinHeap();
        int[] values = {5, 2, 8, 1, 9, 3, 7, 4, 6, 0};
        for (int v : values) h.insert(v);

        int prev = h.extractMin();
        for (int i = 1; i < values.length; i++) {
            int next = h.extractMin();
            assertTrue(next >= prev);
            prev = next;
        }
        assertEquals(0, h.size());
    }

    @Test
    void randomLargeInput() {
        Random r = new Random(42);
        MinHeap h = new MinHeap();
        int n = 1000;
        for (int i = 0; i < n; i++) h.insert(r.nextInt(10000));

        int prev = h.extractMin();
        for (int i = 1; i < n; i++) {
            int next = h.extractMin();
            assertTrue(next >= prev);
            prev = next;
        }
    }

    @Test
    void duplicates() {
        MinHeap h = new MinHeap();
        h.insert(5); h.insert(5); h.insert(5);
        assertEquals(5, h.extractMin());
        assertEquals(5, h.extractMin());
        assertEquals(5, h.extractMin());
        assertEquals(0, h.size());
    }

    @Test
    void singleElement() {
        MinHeap h = new MinHeap();
        h.insert(42);
        assertEquals(42, h.peekMin());
        assertEquals(42, h.extractMin());
        assertEquals(0, h.size());
    }

    @Test
    void emptyHeapThrows() {
        MinHeap h = new MinHeap();
        assertThrows(IllegalStateException.class, h::peekMin);
        assertThrows(IllegalStateException.class, h::extractMin);
    }

    @Test
    void matchesJavaPriorityQueue() {
        java.util.PriorityQueue<Integer> reference = new java.util.PriorityQueue<>();
        MinHeap h = new MinHeap();
        java.util.Random r = new java.util.Random(2);
        int n = 500;
        for (int i = 0; i < n; i++) {
            int x = r.nextInt(1000);
            reference.add(x);
            h.insert(x);
        }
        for (int i = 0; i < n; i++) {
            assertEquals(reference.poll(), h.extractMin());
        }
    }

    @Test
    void heapPropertyAfterEachInsert() {
        MinHeap h = new MinHeap();
        java.util.Random r = new java.util.Random(3);
        int actualMin = Integer.MAX_VALUE;
        for (int i = 0; i < 200; i++) {
            int x = r.nextInt(1000);
            h.insert(x);
            actualMin = Math.min(actualMin, x);
            assertEquals(actualMin, h.peekMin());
        }
    }
}