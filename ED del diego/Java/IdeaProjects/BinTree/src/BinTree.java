/*
  Estructuras de Datos
  Grado en Ingeniería Informática, del Software y de Computadores
  Tema 4. Árboles
  Pablo López
*/

import dataStructures.list.ArrayList;
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

    private static <E> int weightRec(Tree<E> tree){
        int suma = 0;
        if(tree != null){
            if(isLeaf(tree)){
                return 1;
            }else{
                Tree<E> l = tree.left;
                Tree<E> r = tree.right;
                if(l != null && r == null){
                    suma = 1 + weightRec(l);
                }else if(l == null && r != null){
                    suma = 1 + weightRec(r);
                }else{
                    suma = 1 + weightRec(l) + weightRec(r);
                } 
            }
        }
        return suma;
    }
    
    public int height() {
        return heightRec(root);
    }

    private static <E> int heightRec(Tree<E> tree){
        int altura = 0;
        if(tree == null){
            return 0;
        }else{
            if(isLeaf(tree)){
                return 1;
            }else{
                if(tree.left != null){
                    altura = 1 + heightRec(tree.left);
                }else {
                    altura = 1 + heightRec(tree.right);
                }
            }
        }       
        return altura;
    }

    public List<T> border() {
        return borderRec(root);
    }

    private static <E> List<E> borderRec(Tree<E> tree){
        List<E> b = new ArrayList<>();
        if(tree != null) {
            if (isLeaf(tree)) {
                List<E> aux = new dataStructures.list.ArrayList<>();
                aux.append(tree.elem);
                return aux;
            } else {
                List<E> lList = borderRec(tree.left);
                List<E> rList = borderRec(tree.right);
                for (E v : rList) {
                    lList.append(v);
                }
                b = lList;
            }
        }
        return b;
    }

    public boolean isElem(T x) {
        Tree<T> tree = this.root;
        boolean encontrado = searchRec(tree,x);
        return encontrado;
    }

    private static <E> boolean searchRec(Tree<E> tree, E x){
        boolean ok = tree.elem.equals(x);
        if(tree != null){
            if(!ok){
                if(!isLeaf(tree)){
                   return searchRec(tree.left, x) || searchRec(tree.right, x);
                }
            }
        }
        return ok;
    }

    public List<T> atLevel(int i) {
        return null;
    }

    public List<T> inOrder() {
        return null;
    }

    public void trim() {
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
