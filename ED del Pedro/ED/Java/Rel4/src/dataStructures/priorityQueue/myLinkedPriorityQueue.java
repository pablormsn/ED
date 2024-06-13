package dataStructures.priorityQueue;

public class myLinkedPriorityQueue <T extends Comparable<? super T>> implements PriorityQueue<T>{

    static private class Node<E> {
        E elem;
        Node<E> next;
        public Node(E x) {
            elem = x;
            next = null;
        }
    }

    // Nuestra cola enlazada tiene un puntero al primer elemento. Lo llamaremos first
    private Node<T> first;
    // Llevamos el tamaño de nuestra cola
    private int size;

    public myLinkedPriorityQueue(){
        first = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return first == null && size == 0;
    }

    @Override
    public void enqueue(T x) {
        // Encolar de manera ascendente
        Node<T> node = new Node<>(x);
        // Ningún elemento
        if(isEmpty()){
            first = node;
        }else if (first.elem.compareTo(x) > 0){ // Un elemento
            node.next = first;
            first = node;
        }else{
            Node<T> prev = null;
            Node<T> current = first;
            boolean posCorrecta = false;
            while(current != null && !posCorrecta){
                if(current.elem.compareTo(x) > 0) posCorrecta = true;
                else{
                    prev = current;
                    current = current.next;
                }
            }
            node.next = current;
            prev.next = node;
        }
        size++;
    }

    @Override
    public T first() {
        if(isEmpty()){
            throw new EmptyPriorityQueueException("Cola vacía");
        }else{
            return first.elem;
        }
    }

    @Override
    public void dequeue() {
        first = first.next;
    }

    @Override public String toString() {
        String className = getClass().getSimpleName();
        String s = className+"(";
        for(Node<T> node = first; node != null; node = node.next)
            s += node.elem + (node.next != null ? "," : "");
        s += ")";
        return s;
    }
}
