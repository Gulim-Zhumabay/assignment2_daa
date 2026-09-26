import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LinkedListTest {
    @Test
    void addAndGet(){
        LinkedList l = new LinkedList();
        l.add(10);
        l.add(20);
        l.add(30);
        assertEquals(3, l.size());
        assertEquals(10, l.get(0));
        assertEquals(30, l.get(2));
    }
    @Test
    void addAtIndex(){
        LinkedList l = new LinkedList();
        l.add(1);
        l.add(2);
        l.add(3);
        l.add(1,99);
        assertEquals(4, l.size());
        assertEquals(1, l.get(0));
        assertEquals(99, l.get(1));
        assertEquals(2, l.get(2));
        assertEquals(3,l.get(3));
    }
    @Test
    void addAtBeginning(){
        LinkedList l = new LinkedList();
        l.add(1);
        l.add(2);
        l.add(0, 100);
        assertEquals(100, l.get(0));
        assertEquals(1, l.get(1));
        assertEquals(2, l.get(2));
    }
    @Test
    void removeAtIndex(){
        LinkedList l = new LinkedList();
        l.add(1); l.add(2); l.add(3);
        int removed = l.remove(1);
        assertEquals(2, removed);
        assertEquals(2, l.size());
        assertEquals(1, l.get(0));
        assertEquals(3, l.get(1));
    }
    @Test
    void removeFirst(){
        LinkedList l = new LinkedList();
        l.add(1); l.add(2); l.add(3);
        int removed = l.remove (0);
        assertEquals(1, removed);
        assertEquals(2, l.get(0));
    }
    @Test
    void contains(){
        LinkedList l = new LinkedList();
        l.add(5); l.add(15); l.add(25);
        assertTrue(l.contains(15));
        assertFalse(l.contains(100));
    }
    @Test
    void growsBeyondSmallSize(){
        LinkedList l = new LinkedList();
        for (int i = 0; i < 100; i++) l.add(i);
        assertEquals(100, l.size());
        for (int i=0; i< 100; i++) assertEquals(i, l.get(i));
    }
    @Test
    void invalidIndexThrows(){
        LinkedList l = new LinkedList();
        l.add(1);
        assertThrows(IndexOutOfBoundsException.class, () -> l.get(5));
        assertThrows(IndexOutOfBoundsException.class, () -> l.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> l.remove(10));
    }
    @Test
    void emptyStructure(){
        LinkedList l = new LinkedList();
        assertEquals(0, l.size());
        assertFalse(l.contains(1));
        assertThrows(IndexOutOfBoundsException.class, () -> l.get(0));
    }

}
