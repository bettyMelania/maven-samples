package repo;

import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.factory.Sets;

public class MutableSetRepo<T> implements InMemoryRepository<T> {
    private MutableSet<T> list;

    public MutableSetRepo(){
        list= Sets.mutable.empty();
    }
    @Override
    public boolean add(T e) {
        return list.add(e);
    }

    @Override
    public boolean contains(T e) {
        return list.contains(e);
    }

    @Override
    public boolean remove(T e) {
        return list.remove(e);
    }

    @Override
    public void clear() {
        list.clear();

    }
}
