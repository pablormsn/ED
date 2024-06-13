package LinkedBag2;

public interface Bag<T> {
    Bag<T> empty();
    void insert(T x);
    boolean isEmpty();
    int occurrences (T x);
    void delete(T x);
}
