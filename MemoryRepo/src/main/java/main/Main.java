package main;
import order.Order;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import states.RepoState;
import states.SizeState;

import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, time = 1)
@Measurement(iterations = 20, time = 1)
@Fork(1)
@State(Scope.Benchmark)
public class Main {


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Main.class.getSimpleName())
//                .addProfiler(HotspotMemoryProfiler.class)
                .forks(1)
                .build();

        new Runner(opt).run();

    }

    @State(Scope.Benchmark)
    public static class BeforeState {
        Order order;

        @Setup(Level.Invocation)
        public void generateOrder(SizeState sizeState) {
            order = sizeState.before.get();
        }

        @TearDown(Level.Invocation)
        public void removeOrder(RepoState repoState) {
            repoState.orders.remove(order);
        }
    }

    @State(Scope.Benchmark)
    public static class AfterState {
        Order order;

        @Setup(Level.Invocation)
        public void generateOrder(SizeState sizeState) {
            order = sizeState.after.get();
        }

        @TearDown(Level.Invocation)
        public void removeOrder(RepoState repoState) {
            repoState.orders.remove(order);
        }
    }

    @State(Scope.Benchmark)
    public static class ExistingState {
        Order order;

        @Setup(Level.Invocation)
        public void generateOrder(SizeState sizeState) {
            order = sizeState.existing.get();
        }

        @TearDown(Level.Invocation)
        public void removeOrder(RepoState repoState) {
            repoState.orders.remove(order);
        }
    }


    @Benchmark
    public boolean add_before(RepoState repoState, BeforeState before) {
        return repoState.orders.add(before.order);
    }

    @Benchmark
    public boolean add_existing(RepoState repoState, ExistingState existing) {
        return repoState.orders.add(existing.order);
    }

    @Benchmark
    public boolean add_after(RepoState repoState, AfterState after) {
        return repoState.orders.add(after.order);
    }

    @Benchmark
    public boolean contains_existing(RepoState repoState,ExistingState existing) {
        return repoState.orders.contains(existing.order);
}
    @Benchmark
    public boolean contains_inexisting(RepoState repoState,AfterState after) {
        return repoState.orders.contains(after.order);
    }
    @Benchmark
    public boolean remove_existing(RepoState repoState,ExistingState existing) {
        return repoState.orders.remove(existing.order);
    }
    @Benchmark
    public boolean remove_inexisting(RepoState repoState,AfterState after) {
        return repoState.orders.remove(after.order);
    }








}
