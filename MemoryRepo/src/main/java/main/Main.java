package main;
import order.Order;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import repo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 20, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class Main {
    private static int nrEl=10;
    private InMemoryRepository listRepo=new ArrayListBasedRepository();
    private InMemoryRepository hashsetRepo=new HashSetBasedRepository();
    private InMemoryRepository treesetRepo=new TreeSetBasedRepository();

    @State(Scope.Thread)
    public static class Elements{
        private List<Order> orders=new ArrayList<>();
        Order o;
        @Setup(Level.Trial)
        public void doSetup(){
            for (int i = 0; i < 10; i++) {
                o = new Order(i, i * 2, i * 3);
                //System.out.println(o);
                orders.add(o);
            }
        }


    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Main.class.getSimpleName())
//                .addProfiler(HotspotMemoryProfiler.class)
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    public void addList(Blackhole consumer,Elements els) {
        for (int i = 0; i < els.orders.size(); i++){
            listRepo.add(els.orders.get(i));
        }
        listRepo.clear();
    }

   @Benchmark
    public void addSet(Blackhole consumer,Elements els) {
       for (int i = 0; i < els.orders.size(); i++){
           hashsetRepo.add(els.orders.get(i));
       }
    }

    @Benchmark
    public void addTreeSet(Blackhole consumer,Elements els) {
        for (int i = 0; i < els.orders.size(); i++) {
            treesetRepo.add(els.orders.get(i));
        }
    }
    @Benchmark
    public void containsList(Blackhole consumer,Elements els) {
        listRepo.contains(els.o);
    }

    @Benchmark
    public void containsSet(Blackhole consumer,Elements els) {
        hashsetRepo.contains(els.o);
    }

    @Benchmark
    public void containsTreeSet(Blackhole consumer,Elements els) {
        treesetRepo.contains(els.o);
    }

    @Benchmark
    public void removeList(Blackhole consumer,Elements els) {
        listRepo.remove(els.o);
    }

    @Benchmark
    public void removeSet(Blackhole consumer,Elements els) {
        hashsetRepo.remove(els.o);
    }

    @Benchmark
    public void removeTreeSet(Blackhole consumer,Elements els) {
        treesetRepo.remove(els.o);
    }

}
