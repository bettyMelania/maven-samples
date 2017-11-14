package states;

import repo.*;

import java.util.function.Supplier;

public enum RepositorySupplierInt implements Supplier<InMemoryRepositoryInt> {

    ECLIPSE_INT_LIST() {
        @Override
        public InMemoryRepositoryInt get() { return new EclipseListInteger();
        }
    },

    FASTUTIL_INT_LIST() {
        @Override
        public InMemoryRepositoryInt get() {
            return new FastUtilListInteger();
        }
    }
}
