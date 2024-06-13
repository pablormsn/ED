/*
 * Estructuras de Datos. 2º Curso. ETSI Informática. UMA
 * PRACTICA 6. Montículos maxifóbicos en Haskell
 * APELLIDOS, NOMBRE:
 */


// Solo tienes que completar el método merge.

import dataStructures.heap.EmptyHeapException;
import dataStructures.heap.Heap;

/**
 * Heap implemented using maxiphobic heap-ordered binary trees.
 *
 * @param <T> Type of elements in heap.
 */
public class MaxiphobicHeap<T extends Comparable<? super T>> implements Heap<T> {

    private static class Tree<E> {
        private final E elem;
        private final int size;
        private final Tree<E> left;
        private final Tree<E> right;

        public Tree(E x, Tree<E> l, Tree<E> r) {
            elem = x;
            left = l;
            right = r;
            size = 1 + size(l) + size(r);
        }
    }

    private Tree<T> root;

    /**
     * Creates an empty Maxiphobic Heap.
     * <p>
     * Time complexity: O(1)
     */
    public MaxiphobicHeap() {
        root = null;
    }

    private static int size(Tree<?> heap) {
        return heap == null ? 0 : heap.size;
    }

    private static boolean isLeaf(Tree<?> current) {
        return current != null && current.left == null && current.right == null;
    }

    private static <T extends Comparable<? super T>> Tree<T> merge(Tree<T> h1, Tree<T> h2) {
        Tree<T> masPesado;
        Tree<T> ligero1;
        Tree<T> ligero2;
        if(h1 == null){
            return h2;
        }else if(h2 == null){
            return h1;
        }else{
            if(h1.elem.compareTo(h2.elem) < 0){
                ligero1 = h1.left;
                ligero2 = h1.right;
                masPesado = buscarWinner(ligero1,ligero2,h2);
                if(masPesado == h1.left){
                    ligero1 = h2;
                }else if(masPesado == h1.right){
                    ligero2 = h2;
                }
                return new Tree<T>(h1.elem, masPesado, merge(ligero1,ligero2));
             }else{
                ligero1 = h2.left;
                ligero2 = h2.right;
                masPesado = buscarWinner(ligero1,ligero2,h1);
                if(masPesado == h1.left){
                    ligero1 = h2;
                }else if(masPesado == h1.right){
                    ligero2 = h2;
                }
                return new Tree<T>(h2.elem,masPesado, merge(ligero1,ligero2));
            }
        }
    }

    private static  <T extends Comparable<? super T>> Tree<T>  buscarWinner(Tree<T> l, Tree<T> r, Tree<T> h){
        if(size(l) > size(r) && size(l) > size(h)){
            return l;
        }else if(size(r) > size(h)) {
            return r;
        } else {
            return h; 
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(1)
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(1)
     */
    @Override
    public int size() {
        return root == null ? 0 : root.size;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(1)
     *
     * @throws <code>EmptyHeapException</code> if heap stores no element.
     */
    @Override
    public T minElem() {
        if (isEmpty())
            throw new EmptyHeapException("minElem on empty heap");
        else
            return root.elem;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(log n)
     *
     * @throws <code>EmptyHeapException</code> if heap stores no element.
     */
    @Override
    public void delMin() {
        if (isEmpty())
            throw new EmptyHeapException("delMin on empty heap");
        else
            root = merge(root.left, root.right);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Time complexity: O(log n)
     */
    @Override
    public void insert(T value) {
        Tree<T> newHeap = new Tree<>(value, null, null);
        root = merge(root, newHeap);
    }

    public void clear() {
        root = null;
    }

    /**
     * Returns representation of tree as a String.
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + toStringRec(this.root) + ")";
    }

    /**
     * Returns a String with the representation of tree in DOT (graphviz).
     */
    public String toDot(String treeName) {
        final StringBuffer sb = new StringBuffer();
        sb.append(String.format("digraph \"%s\" {\n", treeName));
        sb.append("node [fontname=\"Arial\", fontcolor=red, shape=circle, style=filled, color=\"#66B268\", fillcolor=\"#AFF4AF\" ];\n");
        sb.append("edge [color = \"#0070BF\"];\n");
        toDotRec(root, sb);
        sb.append("}");
        return sb.toString();
    }

    private static String toStringRec(Tree<?> tree) {
        return tree == null ? "null" : "Node<" + toStringRec(tree.left) + ","
                + tree.elem + "," + toStringRec(tree.right)
                + ">";
    }

    private static void toDotRec(Tree<?> current, StringBuffer sb) {
        if (current != null) {
            final int currentId = System.identityHashCode(current);
            sb.append(String.format("%d [label=\"%s\"];\n", currentId, current.elem));
            if (!isLeaf(current)) {
                processChild(current.left, sb, currentId);
                processChild(current.right, sb, currentId);
            }
        }
    }

    private static void processChild(Tree<?> child, StringBuffer sb, int parentId) {
        if (child == null) {
            sb.append(String.format("l%d [style=invis];\n", parentId));
            sb.append(String.format("%d -> l%d;\n", parentId, parentId));
        } else {
            sb.append(String.format("%d -> %d;\n", parentId, System.identityHashCode(child)));
            toDotRec(child, sb);
        }
    }
}
