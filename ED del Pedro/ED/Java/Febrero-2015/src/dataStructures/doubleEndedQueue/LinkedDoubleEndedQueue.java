/**
 * Estructuras de Datos. Grado en Informática, IS e IC. UMA.
 * Examen de Febrero 2015.
 *
 * Implementación del TAD Deque
 *
 * Apellidos:
 * Nombre:
 * Grado en Ingeniería ...
 * Grupo:
 * Número de PC:
 */

package dataStructures.doubleEndedQueue;

public class LinkedDoubleEndedQueue<T> implements DoubleEndedQueue<T> {

    private static class Node<E> {
        private E elem;
        private Node<E> next;
        private Node<E> prev;

        public Node(E x, Node<E> nxt, Node<E> prv) {
            elem = x;
            next = nxt;
            prev = prv;
        }
    }

    private Node<T> first, last;

    /**
     *  Invariants:
     *  if queue is empty then both first and last are null
     *  if queue is non-empty:
     *      * first is a reference to first node and last is ref to last node
     *      * first.prev is null
     *      * last.next is null
     *      * rest of nodes are doubly linked
     */

    /**
     * Complexity:
     */
    public LinkedDoubleEndedQueue() {
        // TODO Auto-generated method stub
        this.first = null;
        this.last = null;
    }

    /**
     * Complexity:
     */
    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return first == null && last == null;
    }

    /**
     * Complexity:
     */
    @Override
    public void addFirst(T x) {
        // TODO Auto-generated method stub
        // Si first null
        if(isEmpty()){
            Node<T> newNode = new Node<>(x,null,null);
            first = newNode;
            last = newNode;
        }else{
            // Creamos el nodo
            Node<T> newNode = new Node<>(x,first,null);
            // Una vez creado el nodo se convierte en mi first
            first.prev = newNode;
            first = newNode;
        }
    }

    /**
     * Complexity:
     */
    @Override
    public void addLast(T x) {
        // TODO Auto-generated method stub
        if(isEmpty()){
            Node<T> n = new Node<>(x,null,null);
            first = n;
            last = n;
        }else{
            Node<T> n = new Node<>(x,null,last);
            last.next = n;
            last = n;
        }
    }

    /**
     * Complexity:
     */
    @Override
    public T first() {
        // TODO Auto-generated method stub
        return first.elem;
    }

    /**
     * Complexity:
     */
    @Override
    public T last() {
        // TODO Auto-generated method stub
        return last.elem;
    }

    /**
     * Complexity:
     */
    @Override
    public void deleteFirst() {
        // TODO Auto-generated method stub
        if(first != null){
            if(first.next == null){
                first = null;
                last = null;
            }else{
                first = first.next;
                first.prev = null;
            }
        }
    }

    /**
     * Complexity:
     */
    @Override
    public void deleteLast() {
        // TODO Auto-generated method stub
        if(last != null){
            if(last.prev == null){
                first = null;
                last = null;
            }else{
                last = last.prev;
                last.next = null;
            }
        }
    }

    /**
     * Returns representation of queue as a String.
     */
    @Override
    public String toString() {
    String className = getClass().getName().substring(getClass().getPackage().getName().length()+1);
        String s = className+"(";
        for (Node<T> node = first; node != null; node = node.next)
            s += node.elem + (node.next != null ? "," : "");
        s += ")";
        return s;
    }
}
