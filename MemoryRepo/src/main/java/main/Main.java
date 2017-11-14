package main;
import order.Order;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import states.RepoState;
import states.RepoStateInt;
import states.SizeState;
import states.SizeStateInt;

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

    @State(Scope.Benchmark)
    public static class BeforeStateInt {
        int nr;

        @Setup(Level.Invocation)
        public void generateInt(SizeStateInt sizeState) {
            nr = sizeState.before.getAsInt();
        }

        @TearDown(Level.Invocation)
        public void removeInt(RepoStateInt repoState) {
            repoState.ints.remove(nr);
        }
    }

    @State(Scope.Benchmark)
    public static class AfterStateInt {
        int nr;

        @Setup(Level.Invocation)
        public void generateInt(SizeStateInt sizeState) {
            nr = sizeState.after.getAsInt();
        }

        @TearDown(Level.Invocation)
        public void removeInt(RepoStateInt repoState) {
            repoState.ints.remove(nr);
        }
    }

    @State(Scope.Benchmark)
    public static class ExistingStateInt {
        int nr;

        @Setup(Level.Invocation)
        public void generateInt(SizeStateInt sizeState) {
            nr = sizeState.existing.getAsInt();
        }

        @TearDown(Level.Invocation)
        public void removeInt(RepoStateInt repoState) {
            repoState.ints.remove(nr);
        }
    }
/*
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
*/
    @Benchmark
    public boolean add_before_int(RepoStateInt repoState, BeforeStateInt before) {
        return repoState.ints.add(before.nr);
    }

    @Benchmark
    public boolean add_existing_int(RepoStateInt repoState, ExistingStateInt existing) {
        return repoState.ints.add(existing.nr);
    }

    @Benchmark
    public boolean add_after_int(RepoStateInt repoState, AfterStateInt after) {
        return repoState.ints.add(after.nr);
    }
/*
    @Benchmark
    public boolean contains_existing_int(RepoStateInt repoState,ExistingStateInt existing) {
        return repoState.ints.contains(existing.nr);
    }
    @Benchmark
    public boolean contains_inexisting_int(RepoStateInt repoState,AfterStateInt after) {
        return repoState.ints.contains(after.nr);
    }
    @Benchmark
    public boolean remove_existing_int(RepoStateInt repoState,ExistingStateInt existing) {
        return repoState.ints.remove(existing.nr);
    }
    @Benchmark
    public boolean remove_inexisting_int(RepoStateInt repoState,AfterStateInt after) {
        return repoState.ints.remove(after.nr);
    }
*/
}
