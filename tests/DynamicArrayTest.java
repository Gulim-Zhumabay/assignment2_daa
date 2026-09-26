import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DynamicArrayTest {
    @Test
    void addAndGet(){
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
    void addAtIndex(){
        DynamicArray a=new DynamicArray();
        a.add(1); a.add(2); a.add(3);
        a.add(1,99);
        assertEquals(4, a.size());
        assertEquals(1, a.get(0));
        assertEquals(99, a.get(1));
        assertEquals(2, a.get(2));
        assertEquals(3, a.get(3));
    }
    @Test
    void removeAtIndex(){
        DynamicArray a = new DynamicArray();
        a.add(1); a.add(2); a.add(3);
        int removed = a.remove(1);
        assertEquals(2, removed);
        assertEquals(2, a.size());
        assertEquals(1, a.get(0));
        assertEquals(3, a.get(1));
    }
    @Test
    void contains(){
        DynamicArray a = new DynamicArray();
        a.add(5); a.add(15); a.add(25);
        assertTrue(a.contains(15));
        assertFalse(a.contains(100));
    }
    @Test
    void growBeyondInitialCapacity(){
        DynamicArray a = new DynamicArray();
        for (int i=0; i<100; i++) a.add(i);
        assertEquals(100, a.size());
        for(int i=0; i<100; i++) assertEquals(i, a.get(i));
    }
    @Test
    void invalidIndexThrows(){
        DynamicArray a = new DynamicArray();
        a.add(1);
        assertThrows(IndexOutOfBoundsException.class, () -> a.get(5));
        assertThrows(IndexOutOfBoundsException.class, () -> a.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> a.get(10));
    }
    @Test
    void emptyStructure(){
        DynamicArray a = new DynamicArray();
        assertEquals(0, a.size());
        assertFalse(a.contains(1));
        assertThrows(IndexOutOfBoundsException.class, () -> a.get(0));
    }
}
