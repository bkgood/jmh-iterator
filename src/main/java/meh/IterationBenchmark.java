package meh;

import java.util.ArrayList;
import java.util.List;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@Fork(jvmArgs = "-Xmx1G")
public class IterationBenchmark {
    private static class Data {
        int i;

        Data(int i) {
            this.i = i;
        }
    }

    @Param({ "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" })
    private int capBase;

    private ArrayList<Data> xs = new ArrayList<>();

    @Setup
    public void setup() {
        int elems = 1 << capBase;
        xs.ensureCapacity(elems);

        for (int i = 0; i < elems; i++) {
            xs.add(new Data(i));
        }

        xs.trimToSize();
    }

    @Benchmark
    public int forTest() {
        int s = 0;

        for (int i = 0; i < xs.size(); i++) {
            s += xs.get(i).i;
        }

        return s;
    }

    @Benchmark
    public int forTestLengthSaved() {
        int s = 0;

        for (int i = 0, len = xs.size(); i < len; i++) {
            s += xs.get(i).i;
        }

        return s;
    }

    @Benchmark
    public int iteratorTest() {
        int s = 0;

        for (Data x : xs) {
            s += x.i;
        }

        return s;
    }
}
