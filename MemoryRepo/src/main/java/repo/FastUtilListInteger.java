package repo;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

public class FastUtilListInteger implements InMemoryRepositoryInt {
    private IntList list;

    public FastUtilListInteger(){
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
        list.remove(e);
        return true;
    }

    @Override
    public void clear() {
        list.clear();
    }
}
