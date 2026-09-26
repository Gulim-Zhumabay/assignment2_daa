public class MinHeap {
    private int[] data;
    private int size;

    public MinHeap() {
        data = new int[4];
        size = 0;
    }

    public int size() {
        return size;
    }

    private void ensureCapacity() {
        if (size == data.length) {
            int[] bigger = new int[data.length * 2];
            for (int i = 0; i < size; i++) bigger[i] = data[i];
            data = bigger;
        }
    }
    public void insert(int x) {
        ensureCapacity();
        data[size] = x;
        size++;
        siftUp(size - 1);
    }

    private void siftUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (data[parent] <= data[i]) break;
            swap(parent, i);
            i = parent;
        }
    }

    private void swap(int i, int j) {
        int t = data[i]; data[i] = data[j]; data[j] = t;
    }
    public int peekMin() {
        if (size == 0) throw new IllegalStateException("heap is empty");
        return data[0];
    }
    public int extractMin() {
        if (size == 0) throw new IllegalStateException("heap is empty");
        int min = data[0];
        size--;
        data[0] = data[size];
        siftDown(0);
        return min;
    }

    private void siftDown(int i) {
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int smallest = i;
            if (left < size && data[left] < data[smallest]) smallest = left;
            if (right < size && data[right] < data[smallest]) smallest = right;
            if (smallest == i) break;
            swap(i, smallest);
            i = smallest;
        }
    }
}