package LinkedBag2;

public class LinkedBag<T extends Comparable<? super T>> implements Bag<T> {
    static class Node<E>{
        E elm;
        int reps;
        Node<E> next;
        public Node (E x, Node<E> nextNode){
            elm = x;
            next = nextNode;
            reps = 1;
        }
    }
    private Node<T> first;

    @Override
    public Bag<T> empty() {
        return new LinkedBag<>();
    }

    @Override
    public void insert(T x) {
        Node<T> previous = null;
        Node<T> current = first;

        while (current != null && current.elm.compareTo(x) < 0) {
            previous = current;
            current = current.next;
        }

        boolean found = (current != null) && current.elm.equals(x);
        if(!found) {
            if (previous == null) // Insert in first position
                first = new LinkedBag.Node<>(x, first);
            else  // Insert after previous
                previous.next = new LinkedBag.Node<>(x, current);
        }else{
            current.reps++;
        }
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public int occurrences(T x) {
        int apariciones;
        Node<T> current = first;
        while(current != null && (current.elm.compareTo(x) < 0)){
            current = current.next;
        }
        if(current.elm.compareTo(x) == 0){
            apariciones = current.reps;
        }else{
            apariciones = 0;
        }
        return apariciones;
    }

    @Override
    public void delete(T x) {
        Node<T> previous = null;
        Node<T> current = first;

        while(current != null && current.elm.compareTo(x) < 0){
            previous = current;
            current = current.next;
        }
        if(previous == null){
            first = current.next;
        }else if (current.elm.compareTo(x) == 0 && current.next == null){
            previous.next = null;
        }else if (current.elm.compareTo(x) == 0 && current.next != null){
            previous.next = current.next;
        }
    }
}
