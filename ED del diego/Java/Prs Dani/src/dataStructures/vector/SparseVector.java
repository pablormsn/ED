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
    }

    // Unif Implementation

    protected static class Unif<T> implements Tree<T> {

        private final T elem;

        public Unif(T e) {
            elem = e;
        }

        @Override
        public T get(int sz, int i) {
            return elem;
        }

        @Override
        public Tree<T> set(int sz, int i, T x) {
            if(elem.equals(x)){
                return this;
            }
            if(sz == 1){
                return new Unif<T>(x);
            }else if(i < sz / 2){
                return new Node<T>(set(sz / 2, i, x), this);
            }else {
                return new Node<T>(this, set(sz / 2, i - (sz / 2), x));
            }
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
            if(i < sz / 2){
                return left.get(sz / 2, i);
            }else {
                return right.get(sz / 2, i - sz / 2);
            }

        }

        @Override
        public Tree<T> set(int sz, int i, T x) {
            if(i < sz / 2){
                left = left.set(sz / 2, i, x);
            }else {
                right = right.set(sz / 2, i - sz / 2, x);
            }
            simplify();
            return this;
        }

        protected Tree<T> simplify() {
            if(left instanceof Unif<?> && right instanceof Unif<?> && left.get(1, 0) == right.get(1, 0)){
                return (Unif<T>) left;
            }
                return this;
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
        if(n < 0){
            throw new VectorException("N menor que cero");
        }
        size = (int) Math.pow(2, n);
        root = new Unif<T>(elem);
    }

    public int size() {
        return size;
    }

    public T get(int i) {
       if(i < 0 || i > size - 1){
           throw new VectorException("Índice no válido");
       }
       return root.get(size, i);
    }

    public void set(int i, T x) {
        if(i < 0 || i > size - 1){
            throw new VectorException("Índice no válido");
        }
        root = root.set(size, i, x);
    }

    @Override
    public Iterator<T> iterator() {
        return new SparseVectorIterator();
    }

    private class SparseVectorIterator implements Iterator<T>{
        int i;

        public SparseVectorIterator(){
            i = 0;
        }

        @Override
        public boolean hasNext() {
            return i < size;
        }

        public T next(){
            if(! hasNext()){
                throw new NoSuchElementException();
            }
            T x = root.get(size, i);
            i ++;
            return x;
        }
    }

    @Override
    public String toString() {
        return "SparseVector(" + size + "," + root + ")";
    }

    /**
     * Returns a String with the representation of tree in DOT (graphviz).
     */
    public String toDot(String vectorName) {
        final StringBuffer sb = new StringBuffer();
        sb.append(String.format("digraph \"%s\" {\n", vectorName));
        sb.append("labelloc=\"t\"");
        sb.append(String.format("label=\"size = %s\"", size));
        sb.append("node [fontname=\"Arial\", fontcolor=red, shape=circle, style=filled, color=\"#66B268\", fillcolor=\"#AFF4AF\" ];\n");
        sb.append("edge [color = \"#0070BF\"];\n");
        toDotRec(root, sb);
        sb.append("}");
        return sb.toString();
    }

    private static <E> void toDotRec(Tree<E> current, StringBuffer sb) {
        final int currentId = System.identityHashCode(current);
        if (current instanceof Node<E>) {
            Node<E> node = (Node<E>) current;
            sb.append(String.format("%d [label=\"%s\"];\n", currentId, ""));
            processChild(node.left, sb, currentId);
            processChild(node.right, sb, currentId);
        } else {
            Unif<E> unif = (Unif<E>) current;
            sb.append(String.format("%d [label=\"%s\" , shape=square];\n", currentId, unif.elem));
        }
    }

    private static void processChild(Tree<?> child, StringBuffer sb, int parentId) {
        sb.append(String.format("%d -> %d;\n", parentId, System.identityHashCode(child)));
        toDotRec(child, sb);
    }
}
