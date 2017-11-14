package repo;


import org.eclipse.collections.api.list.primitive.MutableIntList;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;

public class EclipseListInteger implements InMemoryRepositoryInt {
    private MutableIntList list;

    public EclipseListInteger(){
        list=new IntArrayList();
    }
    @Override
    public boolean add(int e) {
        return list.add(e);
    }

    @Override
    public boolean contains(int e) {

        return list.contains(e);
    }

    @Override
    public boolean remove(int e){
        return list.remove(e);
    }

    @Override
    public void clear() {
        list.clear();
    }
}
