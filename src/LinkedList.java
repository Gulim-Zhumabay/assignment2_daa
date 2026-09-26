public class LinkedList {
    private static class Node{
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }
    private Node head;
    private int size;

    public int size(){
        return size;
    }
    public void add(int x){
        Node node = new Node (x);
        if (head == null) {
            head = node;
        }
        else {
            Node cur = head;
            while (cur.next != null) {
                cur = cur.next;
            }
            cur.next = node;
        }
        size++;
    }
    public void add(int index, int x){
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        Node node = new Node (x);
        if (index == 0) {
            node.next = head;
            head = node;
        }
        else {
            Node prev = head;
            for (int i = 0; i < index - 1; i++){
                prev = prev.next;
            }
            node.next = prev.next;
            prev.next = node;
        }
        size++;
    }
    public int remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        int removed;
        if (index == 0){
            removed = head.value;
            head = head.next;
        } else {
            Node prev = head;
            for (int i = 0; i < index - 1; i++){
                prev = prev.next;
            }
            removed = prev.next.value;
            prev.next = prev.next.next;
        }
        size--;
        return removed;
    }
    public int get (int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        Node cur = head;
        for(int i = 0; i < index; i++) {
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

