import dataStructures.queue.EmptyQueueException;
import dataStructures.queue.Queue;

import java.util.StringJoiner;

public class MyLinkedQueue<T> implements Queue<T> {
    private static class Node<E>{
        E elem;
        Node<E> next;

        public Node (E x, Node<E> nextNode){
            elem = x;
            next = nextNode;
        }
    }

    private Node<T> first;
    private Node<T> last;

    public MyLinkedQueue (){
        first = null;
        last = null;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public void enqueue(T x) {
        Node<T> nuevo = new Node<T>(x, null);
        if (last != null){
            last.next = nuevo;  //enganchar en la estructura
        } else {
            first = nuevo;
        }
        last = nuevo;
    }

    @Override
    public T first() {
        if (isEmpty()){
            throw new EmptyQueueException("La cola esta vacia");
        }
        return first.elem;
    }

    @Override
    public void dequeue() {
        if (isEmpty()){
            throw new EmptyQueueException("La cola esta vacia");
        }
        first = first.next;
        if (first == null){
            last = null;
        }
    }

    public String toString() {

        StringJoiner sj = new StringJoiner(",","(Cola: ",")");
        /*
        Node<T> current = first;
        while (current != null){
            sj.add(current.elem.toString());
            current = current.next;
        }
        */
        for (Node<T> current = first; current != null; current = current.next) {
            sj.add(current.elem.toString());
        }
        return sj.toString();
    }
}
