/********************************************************************
 * Estructuras de Datos. 2º Curso. ETSI Informática. UMA
 * PRACTICA 4.
 * Ejercicio 12 de la tercera relación. Implementar el TAD Bolsa
 *
 * Alumno: APELLIDOS, NOMBRE
 ********************************************************************/

package dataStructures.bag;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedLinkedBag<T extends Comparable<? super T>> implements Bag<T> {

    static private class Node<E> {
        private E elem;
        private int count;
        private Node<E> next;

        Node(E x, int n, Node<E> node) {
            elem = x;
            count = n;
            next = node;
        }
    }

    /**
     * Invariant:
     * <p>
     * 1. keep the linked list sorted by "elem", with no duplicates
     * <p>
     * 2. the counter "count" must be positive
     * <p>
     * Example: first -> ('a', 5) -> ('d', 1) -> ('t', 3) -> ('z', 2)
     */
    private Node<T> first;

    public SortedLinkedBag() {
        first = null;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public void insert(T item) {
        Node<T> previous = null;
        Node<T> current = first;

        while (current != null && current.elem.compareTo(item) < 0){
            previous = current;
            current = current.next;
        }

        boolean found = current != null && current.elem.equals(item);

        if(!found){
            if(previous == null){
                first = new Node<>(item, 1, first);
            }else {
                previous.next = new Node<>(item, 1, current);
            }
        }else {
            current.count ++;
        }
    }

    @Override
    public int occurrences(T item) {
        Node<T> current = first;

        while (current != null && current.elem.compareTo(item) < 0){
            current = current.next;
        }

        boolean found = current != null && current.elem.equals(item);

        if(found){
            return current.count;
        }else {
            return 0;
        }
    }

    @Override
    public void delete(T item) {
        Node<T> previous = null;
        Node<T> current = first;

        while (current != null && current.elem.compareTo(item) < 0){
            previous = current;
            current = current.next;
        }

        boolean found = current != null && current.elem.equals(item);

        if(found){
            if(current.count > 1){
                current.count --;
            }else{
                if(previous == null){
                    first = current.next;
                }else {
                    previous.next = current.next;
                }
            }
        }
    }

    @Override
    public void copyOf(Bag<T> source) {
        // TODO
        // you cannot use insert on 'this'
    }

    @Override
    public String toString() {
        String className = getClass().getName().substring(getClass().getPackage().getName().length() + 1);
        String s = className + "(";
        for(Node<T> p = first; p != null; p = p.next){
            s += p.elem + ";" + p.count + (p.next != null ? "," : "");
        }

        return s + ")";
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedBagIterator();
    }

    private class LinkedBagIterator implements Iterator<T> {
        Node<T> current;

        public LinkedBagIterator(){
            current = first;
        }

        public boolean hasNext(){
            return current != null;
       }

       public T next(){
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            T x = current.elem;
            current = current.next;
            return x;
       }

    }
}
