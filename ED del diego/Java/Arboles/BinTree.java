/*
  Estructuras de Datos
  Grado en Ingeniería Informática, del Software y de Computadores
  Tema 4. Árboles
  Pablo López
*/

import dataStructures.list.ArrayList;
import dataStructures.list.LinkedList;
import dataStructures.list.List;

public class BinTree<T> {

    private static class Tree<E> {
        private E elem;
        private Tree<E> left;
        private Tree<E> right;

        public Tree(E e, Tree<E> l, Tree<E> r) {
            elem = e;
            left = l;
            right = r;
        }
    }

    private Tree<T> root;

    public BinTree() {
        root = null;
    }

    public BinTree(T x) {
        root = new Tree<>(x, null, null);
    }

    public BinTree(T x, BinTree<T> l, BinTree<T> r) {
        root = new Tree<>(x, l.root, r.root);
    }

    private static boolean isLeaf(Tree<?> current) {
        return current != null && current.left == null && current.right == null;
    }

    public int weight() {
        return weightRec(root);
    }

    private <T> int weightRec(Tree<T> r){
        return r == null ? 0 : 1 + weightRec(r.left) + weightRec(r.right);
    }

    public int height() {
        return heightRec(root);
    }

    private <T> int heightRec(Tree<T> r){
        return r == null ? 0 : 1 + Math.max(heightRec(r.left),heightRec(r.right));
    }

    public List<T> border() {
        return borderRec(root);
    }

    private <T> List<T> borderRec(Tree<T> r){
        if(r == null){
            return null;
        }else if(isLeaf(r)){
            List<T> list = new LinkedList<>();
            list.append(r.elem);
            return list;
        }else{
            List<T> borderLeft = borderRec(r.left);
            List<T> borderRight = borderRec(r.right);
            for(T aux: borderRight){
                borderLeft.append(aux);
            }
            return borderLeft;
        }
    }

    public boolean isElem(T x) {
        return isElemRec(x,root);
    }

    private static <T,E> boolean isElemRec(T x, Tree<E> r){
        return r == null ? false : (x == r.elem || isElemRec(x,r.left) || isElemRec(x,r.right));
    }

    public List<T> atLevel(int i) {
        return atLevelRec(i,root);
    }

    private static <E> List<E> atLevelRec(int level, Tree<E> r){
        if(level == 0 && r != null){
            List<E> l = new LinkedList<>();
            l.append(r.elem);
            return l;
        }else if(level > 0 && r != null){
            List<E> lLeft = atLevelRec(level-1,r.left);
            List<E> lRight = atLevelRec(level-1,r.right);
            for(E aux: lRight){
                lLeft.append(aux);
            }
            return lLeft;
        }else{
            return new LinkedList<>();
        }
    }

    public List<T> inOrder() {
        return inOrderRec(root);
    }

    private static <E> List<E> inOrderRec(Tree<E> r){
        Tree<E> father = r;
        if(father == null){
            return new LinkedList<>();
        }else{
            List<E> leftList = inOrderRec(r.left);
            List<E> rightList = inOrderRec(r.right);
            leftList.append(father.elem);
            for(E aux: rightList){
                leftList.append(aux);
            }
            return leftList;
        }
    }

    public void trim() { // Función poda
        root = trimRec(root);
    }

    private static <E> Tree<E> trimRec(Tree<E> r){
        if(r == null || isLeaf(r)){
            return null;
        }else{
            trimRec(r.left);
            trimRec(r.right);
            return r;
        }
    }

    /**
     * Returns representation of tree as a String.
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + toStringRec(this.root) + ")";
    }

    private static String toStringRec(Tree<?> tree) {
        return tree == null ? "null" : "Node<" + toStringRec(tree.left) + ","
                + tree.elem + "," + toStringRec(tree.right)
                + ">";
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
