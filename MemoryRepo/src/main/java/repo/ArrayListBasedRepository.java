package repo;

import java.util.ArrayList;
import java.util.List;

public class ArrayListBasedRepository<T> implements InMemoryRepository<T> {

    private List<T> list;

    public ArrayListBasedRepository() {
        list=new ArrayList<>();
    }

    @Override
    public void add(T o) {
        list.add(o);
    }

    @Override
    public boolean contains(T o) {

        return list.contains(o);
    }

    @Override
    public void remove(T o) {
        list.remove(o);

    }
    @Override
    public void clear(){
        list.clear();
    }
}
