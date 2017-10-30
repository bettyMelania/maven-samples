package repo;


import com.koloboke.collect.set.hash.HashObjSet;
import com.koloboke.collect.set.hash.HashObjSets;

public class KolobokeHashSet<T> implements InMemoryRepository<T> {
    private HashObjSet<T> set;

    public KolobokeHashSet() {
        set=HashObjSets.newMutableSet();
    }

    @Override
    public boolean add(T e) {
        return set.add(e);
    }

    @Override
    public boolean contains(T e) {
        return set.contains(e);
    }

    @Override
    public boolean remove(T e) {
        return set.remove(e);
    }

    @Override
    public void clear() {
        set.clear();
    }
}
