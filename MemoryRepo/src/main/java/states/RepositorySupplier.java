package states;

import it.unimi.dsi.fastutil.objects.ObjectAVLTreeSet;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import order.Order;

import org.eclipse.collections.impl.factory.Lists;
import repo.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public enum RepositorySupplier implements Supplier<InMemoryRepository<Order>> {

    HASH_SET() {
        @Override
        public InMemoryRepository<Order> get() { return new CollectionRepository<>(HashSet::new);
        }
    },

    TREE_SET() {
        @Override
        public InMemoryRepository<Order> get() {
            return new CollectionRepository<>(TreeSet::new);
        }
    },

    ARRAY_LIST() {
        @Override
        public InMemoryRepository<Order> get() {
            return new CollectionRepository<>(ArrayList::new);
        }
    },

    LINKED_LIST() {
        @Override
        public InMemoryRepository<Order> get() {
            return new CollectionRepository<>(LinkedList::new);
        }
    },

    CONCURRENT_HASH_MAP() {
        @Override
        public InMemoryRepository<Order> get() {
            return new CollectionRepository<>(() -> Collections.newSetFromMap(new ConcurrentHashMap<Order, Boolean>()));
        }
    },
//from eclipse collection
    MUTABLE_LIST() {
        @Override
        public InMemoryRepository<Order> get() {
            return new CollectionRepository<>(()->Lists.mutable.empty());
        }
    },

//from eclipse collection
    MUTABLE_SET() {
        @Override
        public InMemoryRepository<Order> get() {
            return new MutableSetRepo();
        }
    },

    FASTUTIL_ARRAY_LIST() {
        @Override
        public InMemoryRepository<Order> get() {
            return new CollectionRepository<>(ObjectArrayList::new);
        }
    },
    FASTUTIL_AVL_TREE_SET() {
        @Override
        public InMemoryRepository<Order> get() {
            return new CollectionRepository<>(ObjectAVLTreeSet::new);
        }
    },

    KOLOBOKE_HASH_SET() {
     @Override
        public InMemoryRepository<Order> get() {
            return new KolobokeHashSet();
        }
    }


}