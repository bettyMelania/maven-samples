package states;

import order.Order;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
import repo.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.function.Supplier;

public enum RepositorySupplierInt implements Supplier<InMemoryRepositoryInt> {
  /*
    ECLIPSE_INT_LIST() {
        @Override
        public InMemoryRepositoryInt get() { return new EclipseListInteger();
        }
    },
        */
    FASTUTIL_INT_LIST() {
        @Override
        public InMemoryRepositoryInt get() {
            return new FastUtilListInteger();
        }
    }
}
