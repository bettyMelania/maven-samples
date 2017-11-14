package repo;

public interface InMemoryRepositoryInt {
    boolean add(int e);

    boolean contains(int e);

    boolean remove(int e);

    void clear();
}
