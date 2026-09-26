import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DynamicArrayTest {
    @Test
    void addAndGet() {
        DynamicArray a = new DynamicArray();
        a.add(10);
        a.add(20);
        a.add(30);
        assertEquals(3, a.size());
        assertEquals(10, a.get(0));
        assertEquals(20, a.get(1));
        assertEquals(30, a.get(2));
    }

    @Test
    void addAtIndex() {
        DynamicArray a = new DynamicArray();
        a.add(1); a.add(2); a.add(3);
        a.add(1, 99);
        assertEquals(4, a.size());
        assertEquals(1, a.get(0));
        assertEquals(99, a.get(1));
        assertEquals(2, a.get(2));
        assertEquals(3, a.get(3));
    }

    @Test
    void removeAtIndex() {
        DynamicArray a = new DynamicArray();
        a.add(1); a.add(2); a.add(3);
        int removed = a.remove(1);
        assertEquals(2, removed);
        assertEquals(2, a.size());
        assertEquals(1, a.get(0));
        assertEquals(3, a.get(1));
    }

    @Test
    void contains() {
        DynamicArray a = new DynamicArray();
        a.add(5); a.add(15); a.add(25);
        assertTrue(a.contains(15));
        assertFalse(a.contains(100));
    }

    @Test
    void growBeyondInitialCapacity() {
        DynamicArray a = new DynamicArray();
        for (int i = 0; i < 100; i++) a.add(i);
        assertEquals(100, a.size());
        for (int i = 0; i < 100; i++) assertEquals(i, a.get(i));
    }

    @Test
    void invalidIndexThrows() {
        DynamicArray a = new DynamicArray();
        a.add(1);
        assertThrows(IndexOutOfBoundsException.class, () -> a.get(5));
        assertThrows(IndexOutOfBoundsException.class, () -> a.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> a.get(10));
    }

    @Test
    void emptyStructure() {
        DynamicArray a = new DynamicArray();
        assertEquals(0, a.size());
        assertFalse(a.contains(1));
        assertThrows(IndexOutOfBoundsException.class, () -> a.get(0));
    }

    @Test
    void duplicateValues() {
        DynamicArray a = new DynamicArray();
        a.add(7); a.add(7); a.add(7);
        assertEquals(3, a.size());
        assertTrue(a.contains(7));
        a.remove(1);
        assertEquals(2, a.size());
        assertEquals(7, a.get(0));
        assertEquals(7, a.get(1));
    }

    @Test
    void matchesJavaArrayList() {
        java.util.ArrayList<Integer> reference = new java.util.ArrayList<>();
        DynamicArray a = new DynamicArray();
        java.util.Random r = new java.util.Random(1);
        for (int i = 0; i < 500; i++) {
            int x = r.nextInt(100);
            reference.add(x);
            a.add(x);
        }
        assertEquals(reference.size(), a.size());
        for (int i = 0; i < reference.size(); i++) {
            assertEquals(reference.get(i), a.get(i));
        }
    }

    @Test
    void singleElement() {
        DynamicArray a = new DynamicArray();
        a.add(99);
        assertEquals(1, a.size());
        assertEquals(99, a.get(0));
        assertTrue(a.contains(99));
        assertEquals(99, a.remove(0));
        assertEquals(0, a.size());
    }

    @Test
    void boundaryIndices() {
        DynamicArray a = new DynamicArray();
        a.add(1); a.add(2); a.add(3);
        a.add(3, 100);
        assertEquals(4, a.size());
        assertEquals(100, a.get(3));
        int removed = a.remove(3);
        assertEquals(100, removed);
        assertEquals(3, a.size());
    }
    @Test
    void invalidIndexOnAddThrows() {
        DynamicArray a = new DynamicArray();
        a.add(1); a.add(2);
        assertThrows(IndexOutOfBoundsException.class, () -> a.add(-1, 99));
        assertThrows(IndexOutOfBoundsException.class, () -> a.add(3, 99));
    }

    @Test
    void invalidIndexOnRemoveThrows() {
        DynamicArray a = new DynamicArray();
        a.add(1); a.add(2);
        assertThrows(IndexOutOfBoundsException.class, () -> a.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> a.remove(2));
    }
}