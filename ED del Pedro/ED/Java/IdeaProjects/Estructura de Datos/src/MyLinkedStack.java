import dataStructures.stack.EmptyStackException;
import dataStructures.stack.Stack;

public class MyLinkedStack<T> implements Stack<T> {

    private static class Node<E>{
        E elem;
        Node<E> next;

        public Node (E x, Node<E> nextNode){
            elem = x;
            next = nextNode;
        }
    }
    private Node<T> top;

    public MyLinkedStack(){
        top = null;
    }
    @Override
    public boolean isEmpty() {
        return top == null;
    }

    @Override
    public void push(T x) {
        top = new Node<>(x, top);
    }

    @Override
    public T top() {
        if (isEmpty()){
            throw new EmptyStackException("....");
        }
        return top.elem;
    }

    @Override
    public void pop() {
        if (isEmpty()){
            throw new EmptyStackException("....");
        }
        top = top.next;
    }
}
