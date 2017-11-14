package states;

import order.Order;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

@State(Scope.Benchmark)
public class SizeStateInt {
    @Param({"1000", "10000"})
    public int size;

    public IntSupplier existing = new IntSupplier() {
        private final Random random = new Random();

        @Override
        public int getAsInt() {
            return random.nextInt(size);
        }
    };

    public IntSupplier before = new IntSupplier() {
        private final AtomicInteger currentSize = new AtomicInteger(size);
        @Override
        public int getAsInt() {
            return currentSize.decrementAndGet();
        }
    };

    public IntSupplier after = new IntSupplier() {
        private final AtomicInteger currentSize = new AtomicInteger(size);
        @Override
        public int getAsInt() {
            return currentSize.incrementAndGet();
        }
    };
}