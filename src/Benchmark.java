import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Random;

public class Benchmark {
    private static final int[] SIZES = {100, 1_000, 10_000, 100_000};
    private static final int RUNS = 5;
    private static final int WARMUP_RUNS = 2;

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

            DynamicArray[] daHolder = new DynamicArray[1];
            double daTime = timeAverage(
                    () -> {
                        daHolder[0] = new DynamicArray();
                        for (int x : input) daHolder[0].add(x);
                    },
                    () -> {
                        for (int idx : indices) daHolder[0].get(idx);
                    }
            );
            emit(out, "random_access", "dynamic_array", n, daTime, 10_000);

            LinkedList[] llHolder = new LinkedList[1];
            double llTime = timeAverage(
                    () -> {
                        llHolder[0] = new LinkedList();
                        for (int x : input) llHolder[0].add(x);
                    },
                    () -> {
                        for (int idx : indices) llHolder[0].get(idx);
                    }
            );
            emit(out, "random_access", "linked_list", n, llTime, 10_000);
        }
    }

    private void workload2Search(PrintWriter out) {
        for (int n : SIZES) {
            int[] input = randomInts(n, 3);
            int[] searchValues = randomInts(1_000, 4);

            DynamicArray[] daHolder = new DynamicArray[1];
            double daTime = timeAverage(
                    () -> {
                        daHolder[0] = new DynamicArray();
                        for (int x : input) daHolder[0].add(x);
                    },
                    () -> {
                        for (int v : searchValues) daHolder[0].contains(v);
                    }
            );
            long comparisons = countSearchComparisons(input, searchValues);
            emit(out, "search", "dynamic_array", n, daTime, comparisons);

            LinkedList[] llHolder = new LinkedList[1];
            double llTime = timeAverage(
                    () -> {
                        llHolder[0] = new LinkedList();
                        for (int x : input) llHolder[0].add(x);
                    },
                    () -> {
                        for (int v : searchValues) llHolder[0].contains(v);
                    }
            );
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
        DynamicArray[] daHolder = new DynamicArray[1];
        double daInsertTime = timeAverage(
                () -> {
                    daHolder[0] = new DynamicArray();
                    for (int x : input) daHolder[0].add(x);
                },
                () -> {
                    for (int i = 0; i < 1_000; i++) daHolder[0].add(index, 1);
                }
        );
        emit(out, "insert_" + label, "dynamic_array", n, daInsertTime, daHolder[0].movements());

        LinkedList[] llHolder = new LinkedList[1];
        double llInsertTime = timeAverage(
                () -> {
                    llHolder[0] = new LinkedList();
                    for (int x : input) llHolder[0].add(x);
                },
                () -> {
                    for (int i = 0; i < 1_000; i++) llHolder[0].add(index, 1);
                }
        );
        emit(out, "insert_" + label, "linked_list", n, llInsertTime, llHolder[0].movements());

        int removals = Math.min(1_000, n);

        DynamicArray[] daRemoveHolder = new DynamicArray[1];
        double daRemoveTime = timeAverage(
                () -> {
                    daRemoveHolder[0] = new DynamicArray();
                    for (int x : input) daRemoveHolder[0].add(x);
                },
                () -> {
                    for (int i = 0; i < removals; i++)
                        daRemoveHolder[0].remove(Math.min(index, daRemoveHolder[0].size() - 1));
                }
        );
        emit(out, "remove_" + label, "dynamic_array", n, daRemoveTime, daRemoveHolder[0].movements());

        LinkedList[] llRemoveHolder = new LinkedList[1];
        double llRemoveTime = timeAverage(
                () -> {
                    llRemoveHolder[0] = new LinkedList();
                    for (int x : input) llRemoveHolder[0].add(x);
                },
                () -> {
                    for (int i = 0; i < removals; i++)
                        llRemoveHolder[0].remove(Math.min(index, llRemoveHolder[0].size() - 1));
                }
        );
        emit(out, "remove_" + label, "linked_list", n, llRemoveTime, llRemoveHolder[0].movements());
    }

    private void workload4Heap(PrintWriter out) {
        for (int n : SIZES) {
            int[] input = randomInts(n, 6);

            MinHeap[] insertHolder = new MinHeap[1];
            double insertTime = timeAverage(
                    () -> {},
                    () -> {
                        insertHolder[0] = new MinHeap();
                        for (int x : input) insertHolder[0].insert(x);
                    }
            );
            emit(out, "heap_insert", "min_heap", n, insertTime, insertHolder[0].comparisons());

            MinHeap[] extractHolder = new MinHeap[1];
            double extractTime = timeAverage(
                    () -> {
                        extractHolder[0] = new MinHeap();
                        for (int x : input) extractHolder[0].insert(x);
                        extractHolder[0].resetComparisons();
                    },
                    () -> {
                        while (extractHolder[0].size() > 0) extractHolder[0].extractMin();
                    }
            );
            emit(out, "heap_extract", "min_heap", n, extractTime, extractHolder[0].comparisons());
        }
    }

    private interface Task { void run(); }

    private double timeAverage(Task setup, Task timed) {
        // "Холостые" прогоны (не записываются), чтобы JVM успела
        // JIT-скомпилировать код — иначе самые первые (маленькие n)
        // замеры получаются искусственно медленнее поздних.
        for (int w = 0; w < WARMUP_RUNS; w++) {
            setup.run();
            timed.run();
        }

        double sum = 0;
        for (int r = 0; r < RUNS; r++) {
            setup.run();
            long t0 = System.nanoTime();
            timed.run();
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