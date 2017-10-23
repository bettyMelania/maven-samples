package repo;

import java.util.Set;
import java.util.TreeSet;

public class TreeSetBasedRepository<T> implements InMemoryRepository<T> {

    private Set<T> set;

    public TreeSetBasedRepository() {
        this.set=new TreeSet<>();
    }

    @Override
    public void add(T t) {
        set.add(t);
    }

    @Override
    public boolean contains(T t) {
        return set.contains(t);

    }

    @Override
    public void remove(T t) {
        set.remove(t);

    }

    @Override
    public void clear() {

    }
}
