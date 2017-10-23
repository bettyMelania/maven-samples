package repo;

public interface InMemoryRepository<T> {
    void add(T t);
    boolean contains(T t);
    void remove(T t);
    void clear();
}
