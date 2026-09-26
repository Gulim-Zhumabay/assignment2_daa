import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        new Benchmark().runAll("results/tables/results.csv");
        System.out.println("done, results are in results/tables/results.csv");
    }
}