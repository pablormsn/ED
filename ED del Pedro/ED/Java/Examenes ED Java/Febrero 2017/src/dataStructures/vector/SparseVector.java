/******************************************************************************
 * Student's name:
 * Student's group:
 * Data Structures. Grado en Informática. UMA.
******************************************************************************/

package dataStructures.vector;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SparseVector<T> implements Iterable<T> {

    protected interface Tree<T> {

        T get(int sz, int i);

        Tree<T> set(int sz, int i, T x);

        int depthOf(int sz, int i);
    }

    // Unif Implementation

    protected static class Unif<T> implements Tree<T> {

        private T elem;

        public Unif(T e) {
            elem = e;
        }

        @Override
        public T get(int sz, int i) {
            // TODO
            return this.elem;
        }

        @Override
        public Tree<T> set(int sz, int i, T x) {
            // TODO
            if(sz == 1){
                return new Unif<>(x);
            }else{
                Node<T> t;
                if(i < sz/2){
                    t = new Node<>(set(sz/2,i,x), new Unif<>(this.elem));
                }else{
                    t = new Node<>(new Unif<>(this.elem), set(sz/2,i-(sz/2),x));
                }
                return t;
            }
        }

        public int depthOf(int sz, int i){
            return 0;
        }

        @Override
        public String toString() {
            return "Unif(" + elem + ")";
        }
    }

    // Node Implementation

    protected static class Node<T> implements Tree<T> {

        private Tree<T> left, right;

        public Node(Tree<T> l, Tree<T> r) {
            left = l;
            right = r;
        }

        @Override
        public T get(int sz, int i) {
            // TODO
            if(i < sz/2){
                return left.get(sz/2,i);
            }else{
                return right.get(sz/2,i-(sz/2));
            }
        }

        @Override
        public Tree<T> set(int sz, int i, T x) {
            // TODO
            if(i < sz/2){
                left = left.set(sz/2,i,x);
            }else{
                right = right.set(sz/2,i-(sz/2),x);
            }
            simplify();
            return this;
        }

        protected Tree<T> simplify() {
            // TODO
            if(this.left instanceof Unif<?> && this.right instanceof Unif<?> && left.get(1,0) == right.get(1,0)){
                return (Unif<T>) left;
            }else{
                return this;
            }
        }

        public int depthOf(int sz, int i){
            if(i < sz/2){
                return 1 + left.depthOf(sz/2,i);
            }else{
                return 1 + right.depthOf(sz/2, i-sz/2);
            }
        }

        @Override
        public String toString() {
            return "Node(" + left + ", " + right + ")";
        }
    }

    // SparseVector Implementation

    private int size;
    private Tree<T> root;

    public SparseVector(int n, T elem) {
        // TODO
        if(n < 0){
            throw new VectorException("N menor que 0");
        }
        size = (int) Math.pow(2,n);
        root = new Unif<>(elem);
    }

    public SparseVector(SparseVector<T> vector){
        root = new Unif<>(vector.get(0));
        size = vector.size();
        int current = 1; // Posición del elemento sobre el que estamos
        while(current < vector.size){
            root.set(vector.size(), current, vector.get(current));
            current++;
        }
    }

    public int size() {
        // TODO
        return size;
    }

    public T get(int i) {
        // TODO
        if(i<0 || i+1>size){
            throw new VectorException("Indice erroneo");
        }
        return root.get(size(),i);
    }

    public void set(int i, T x) {
        // TODO
        if(i<0 || i+1>size){
            throw new VectorException("Indice erroneo");
        }
        root = root.set(size(),i,x);
    }

    @Override
    public Iterator<T> iterator() {
        // TODO
        return new SparseVectorIterator<>();
    }

    private class SparseVectorIterator<T> implements Iterator<T>{
        int current;
        public SparseVectorIterator(){
            current = 0;
        }

        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public T next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            T x = (T) root.get(size,current);
            current++;
            return x;
        }

    }

    public int depthOf(int sz, int i){
        return root.depthOf(sz,i);
    }

    @Override
    public String toString() {
        return "SparseVector(" + size + "," + root + ")";
    }
}
