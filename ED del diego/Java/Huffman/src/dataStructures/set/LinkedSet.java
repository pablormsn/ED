/**
 * @author Pepe Gallardo, Data Structures, Grado en Inform�tica. UMA.
 *
 * Sets implemented using a sorted linked list
 */

package dataStructures.set;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class LinkedSet<T> implements Set<T> {
    static private class Node<E> {
        E elem;
        Node<E> next;

        Node(E x, Node<E> node) {
            elem = x;
            next = node;
        }
    }

    private Node<T> first; // keep linked list sorted by "elem"
    private int size;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() { return size; }

    public void insert(T item) {
        Node<T> previous = null;
        Node<T> current = first;

        while (current != null && !current.elem.equals(item)) {
            previous = current;
            current = current.next;
        }

        boolean found = (current != null);
        if(!found) {
            if (previous == null) // Insert in first position
                first = new Node<>(item, null);
            else  // Insert after previous
                previous.next = new Node<>(item, null);
            size++;
        }
    }

    public boolean isElem(T item) {
        Node<T> current = first;

        while (current != null && !current.elem.equals(item)) {
            current = current.next;
        }

        return (current != null) ;
    }

    public void delete(T item) {
        Node<T> previous = null;
        Node<T> current = first;

        while (current != null && !current.elem.equals(item)) {
            previous = current;
            current = current.next;
        }

        boolean found = (current != null) ;
        if(found) {
            if (previous == null) {
                first = current.next;
            } else {
                previous.next = current.next;
            }
            size--;
        }
    }

    public String toString() {
        String className = getClass().getSimpleName();
        StringJoiner joiner = new StringJoiner(",",className+"(",")");
        for (Node<T> p = first; p != null; p = p.next)
            joiner.add(p.elem.toString());
        return joiner.toString();
    }

    /**
     * Iterator over elements in set.
     * Note that {@code remove} method is not supported. Note also
     * that linked structure should not be modified during iteration as
     * iterator state may become inconsistent.
     * @see Iterable#iterator()
     */
    public Iterator<T> iterator() {
        return new LinkedSetIterator();
    }

    private class LinkedSetIterator implements Iterator<T> {
        Node<T> current;

        public LinkedSetIterator(){
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public T next() {
            if (!hasNext())
                throw new NoSuchElementException();
            T x = current.elem;
            current = current.next;
            return x;
        }
    }
}


