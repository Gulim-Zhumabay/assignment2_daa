import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Random;

public class Benchmark {
    private static final int[] SIZES = {100, 1_000, 10_000, 100_000};
    private static final int RUNS = 5;

    public void runAll(String file) throws IOException {
        Path path = Path.of(file);
        Files.createDirectories(path.getParent());
        try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(path))) {
            out.println("workload,structure,n,timeMs,metric");
            System.out.println("workload,structure,n,timeMs,metric");
            workload1RandomAccess(out);
            workload2Search(out);
            workload3InsertRemove(out);
            workload4Heap(out);
        }
    }
    private void workload1RandomAccess(PrintWriter out) {
        for (int n : SIZES) {
            int[] input = randomInts(n, 1);
            int[] indices = randomIndices(n, 10_000, 2);

            double daTime = timeAverage(() -> {
                DynamicArray da = new DynamicArray();
                for (int x : input) da.add(x);
                for (int idx : indices) da.get(idx);
            });
            emit(out, "random_access", "dynamic_array", n, daTime, 10_000);

            double llTime = timeAverage(() -> {
                LinkedList ll = new LinkedList();
                for (int x : input) ll.add(x);
                for (int idx : indices) ll.get(idx);
            });
            emit(out, "random_access", "linked_list", n, llTime, 10_000);
        }
    }

    private void workload2Search(PrintWriter out) {
        for (int n : SIZES) {
            int[] input = randomInts(n, 3);
            int[] searchValues = randomInts(1_000, 4);

            double daTime = timeAverage(() -> {
                DynamicArray da = new DynamicArray();
                for (int x : input) da.add(x);
                for (int v : searchValues) da.contains(v);
            });
            long comparisons = countSearchComparisons(input, searchValues);
            emit(out, "search", "dynamic_array", n, daTime, comparisons);

            double llTime = timeAverage(() -> {
                LinkedList ll = new LinkedList();
                for (int x : input) ll.add(x);
                for (int v : searchValues) ll.contains(v);
            });
            emit(out, "search", "linked_list", n, llTime, comparisons);
        }
    }

    private long countSearchComparisons(int[] input, int[] searchValues) {
        long comparisons = 0;
        for (int v : searchValues) {
            for (int x : input) {
                comparisons++;
                if (x == v) break;
            }
        }
        return comparisons;
    }

    private void workload3InsertRemove(PrintWriter out) {
        for (int n : SIZES) {
            int[] input = randomInts(n, 5);
            insertRemoveCase(out, n, input, 0, "begin");
            insertRemoveCase(out, n, input, n / 2, "middle");
        }
    }

    private void insertRemoveCase(PrintWriter out, int n, int[] input, int index, String label) {
        double daInsertTime = timeAverage(() -> {
            DynamicArray da = new DynamicArray();
            for (int x : input) da.add(x);
            for (int i = 0; i < 1_000; i++) da.add(index, 1);
        });
        long daInsertMovements = (long) 1_000 * (n - index);
        emit(out, "insert_" + label, "dynamic_array", n, daInsertTime, daInsertMovements);

        double llInsertTime = timeAverage(() -> {
            LinkedList ll = new LinkedList();
            for (int x : input) ll.add(x);
            for (int i = 0; i < 1_000; i++) ll.add(index, 1);
        });
        long llInsertMovements = (long) 1_000 * index;
        emit(out, "insert_" + label, "linked_list", n, llInsertTime, llInsertMovements);

        int removals = Math.min(1_000, n);

        double daRemoveTime = timeAverage(() -> {
            DynamicArray da = new DynamicArray();
            for (int x : input) da.add(x);
            for (int i = 0; i < removals; i++) da.remove(Math.min(index, da.size() - 1));
        });
        long daRemoveMovements = (long) removals * (n - index);
        emit(out, "remove_" + label, "dynamic_array", n, daRemoveTime, daRemoveMovements);

        double llRemoveTime = timeAverage(() -> {
            LinkedList ll = new LinkedList();
            for (int x : input) ll.add(x);
            for (int i = 0; i < removals; i++) ll.remove(Math.min(index, ll.size() - 1));
        });
        long llRemoveMovements = (long) removals * index;
        emit(out, "remove_" + label, "linked_list", n, llRemoveTime, llRemoveMovements);
    }
    private void workload4Heap(PrintWriter out) {
        for (int n : SIZES) {
            int[] input = randomInts(n, 6);

            double insertTime = timeAverage(() -> {
                MinHeap h = new MinHeap();
                for (int x : input) h.insert(x);
            });
            emit(out, "heap_insert", "min_heap", n, insertTime, n);

            double extractTime = timeAverage(() -> {
                MinHeap h = new MinHeap();
                for (int x : input) h.insert(x);
                while (h.size() > 0) h.extractMin();
            });
            emit(out, "heap_extract", "min_heap", n, extractTime, n);
        }
    }

    private interface Task { void run(); }

    private double timeAverage(Task task) {
        double sum = 0;
        for (int r = 0; r < RUNS; r++) {
            long t0 = System.nanoTime();
            task.run();
            long t1 = System.nanoTime();
            sum += (t1 - t0) / 1e6;
        }
        return sum / RUNS;
    }

    private int[] randomInts(int count, int seedOffset) {
        Random r = new Random(42 + seedOffset);
        int[] a = new int[count];
        for (int i = 0; i < count; i++) a[i] = r.nextInt(1_000_000);
        return a;
    }
    private int[] randomIndices(int n, int count, int seedOffset) {
        Random r = new Random(42 + seedOffset);
        int[] a = new int[count];
        for (int i = 0; i < count; i++) a[i] = r.nextInt(n);
        return a;
    }
    private void emit(PrintWriter out, String workload, String structure, int n, double timeMs, long metric) {
        String line = String.format(Locale.US, "%s,%s,%d,%.3f,%d", workload, structure, n, timeMs, metric);
        out.println(line);
        System.out.println(line);
    }
}