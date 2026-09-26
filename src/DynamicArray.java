public class DynamicArray {
    private int [] data;
    private int size;

    public DynamicArray(){
        data=new int[4];
        size=0;
    }
    public int size() {
        return size;
    }
    public void add(int x){
        ensureCapacity();
        data[size]=x;
        size++;
    }
    private void ensureCapacity(){
        if (size == data.length) {
            int[] bigger =new int[data.length * 2];
            for (int i=0; i < size; i++) bigger[i] = data[i];
            data = bigger;
        }
    }
    public void add(int index, int x) {
        if (index < 0 || index> size) throw new IndexOutOfBoundsException();
        ensureCapacity();
        for (int i = size; i> index; i--) {
            data[i] = data[i-1];
        }
        data[index]=x;
        size++;
    }
    public int remove (int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        int removed = data[index];
        for (int i = index; i < size -1; i++){
            data[i] = data[i+1];
        }
        size--;
        return removed;
    }
    public int get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return data[index];
    }
    public boolean contains (int x){
        for (int i=0; i< size; i++){
            if (data[i] == x) return true;
        }
        return false;
    }
}
