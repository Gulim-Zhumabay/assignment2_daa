public class LinkedList {
    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    private Node head;
    private Node tail;
    private int size;
    private long movements;

    public int size() {
        return size;
    }

    public long movements() {
        return movements;
    }

    public void resetMovements() {
        movements = 0;
    }

    public void add(int x) {
        Node node = new Node(x);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public void add(int index, int x) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        Node node = new Node(x);
        if (index == 0) {
            node.next = head;
            head = node;
            if (tail == null) tail = node;
        } else {
            Node prev = head;
            for (int i = 0; i < index - 1; i++) {
                prev = prev.next;
                movements++;
            }
            node.next = prev.next;
            prev.next = node;
            if (node.next == null) tail = node;
        }
        size++;
    }

    public int remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        int removed;
        if (index == 0) {
            removed = head.value;
            head = head.next;
            if (head == null) tail = null;
        } else {
            Node prev = head;
            for (int i = 0; i < index - 1; i++) {
                prev = prev.next;
                movements++;
            }
            removed = prev.next.value;
            prev.next = prev.next.next;
            if (prev.next == null) tail = prev;
        }
        size--;
        return removed;
    }

    public int get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.value;
    }

    public boolean contains(int x) {
        Node cur = head;
        while (cur != null) {
            if (cur.value == x) return true;
            cur = cur.next;
        }
        return false;
    }
}