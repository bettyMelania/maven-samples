package states;

import order.Order;
import org.openjdk.jmh.annotations.*;
import repo.InMemoryRepository;

import java.util.stream.IntStream;


@State(Scope.Benchmark)
public class RepoState {
    @Param
    private RepositorySupplier repositorySupplier;

    public InMemoryRepository<Order> orders;

    /* run before each benchmark */
    @Setup
    public void setUp(SizeState sizeState) {
        System.out.println(getClass().getSimpleName() + " > setup > populate");
        orders = repositorySupplier.get();

        IntStream.rangeClosed(1, sizeState.size)
                .mapToObj(Order::new)
                .forEach(orders::add);
    }

    /* run after each benchmark */
    @TearDown
    public void tearDown() {
        System.out.println(getClass().getSimpleName() + " > teardown > clear");
        orders.clear();
        System.gc();
    }
}