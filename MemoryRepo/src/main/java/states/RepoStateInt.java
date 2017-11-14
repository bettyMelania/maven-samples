package states;

import org.openjdk.jmh.annotations.*;
import repo.InMemoryRepositoryInt;

import java.util.stream.IntStream;

@State(Scope.Benchmark)
public class RepoStateInt {
    @Param
    private RepositorySupplierInt repositorySupplier;

    public InMemoryRepositoryInt ints;

    @Setup
    public void setUpInt(SizeState sizeState) {
        System.out.println(getClass().getSimpleName() + " > setup int > populate");
        ints = repositorySupplier.get();

        IntStream.rangeClosed(1, sizeState.size)
                .mapToObj(Integer::new)
                .forEach(ints::add);
    }

    /* run after each benchmark */
    @TearDown
    public void tearDownInt() {
        System.out.println(getClass().getSimpleName() + " > teardown int > clear");
        ints.clear();
        System.gc();
    }
}
