/* ------------------------------------------------------------------------------
   -- Student's name:
   -- Student's group:
   -- Identity number (DNI if Spanish/passport if Erasmus):
   --
   -- Data Structures. Grado en Informática. UMA.
   -------------------------------------------------------------------------------
*/

package exercises;

import dataStructures.list.LinkedList;
import dataStructures.list.List;

import java.util.Iterator;

public class BinaryTree<T extends Comparable<? super T>> {

    private static class Node<E> {
        E value;
        Node<E> left;
        Node<E> right;

        public Node(E value, Node<E> left, Node<E> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public Node(E value) {
            this(value, null, null);
        }
    }

    private Node<T> root;

    public BinaryTree() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void insertBST(T value) {
        root = insertBSTRec(root, value);
    }

    private Node<T> insertBSTRec(Node<T> node, T elem) {
        if (node == null) {
            node = new Node<>(elem);
        } else if (elem.compareTo(node.value) < 0)
            node.left = insertBSTRec(node.left, elem);
        else if (elem.compareTo(node.value) > 0)
            node.right = insertBSTRec(node.right, elem);
        else
            node.value = elem;
        return node;
    }

    /**
     * Returns representation of tree as a String.
     */
    @Override
    public String toString() {
        String className = getClass().getSimpleName();
        return className + "(" + toStringRec(this.root) + ")";
    }

    private static String toStringRec(Node<?> node) {
        return node == null ? "null" : "Node<" + toStringRec(node.left) + ","
                + node.value + "," + toStringRec(node.right) + ">";
    }

// -- ESCRIBE TU SOLUCIÓN DEBAJO ----------------------------------------------
// -- WRITE YOUR SOLUTION BELOW  ----------------------------------------------

    /*    private static Integer change2Greater(Integer n, List<Integer> nodes){
        int res = 0;

        for(Integer v: nodes){
            if(v > n){
                res += v;
            }
        }

        return res;
    }

    private static Node<Integer> greaterSumTreeRec(Node<Integer> bst, List<Integer> nodes){
        if(bst == null){
            return null;
        }else{
            bst.right = greaterSumTreeRec(bst.right,nodes);
            bst.value = change2Greater(bst.value,nodes);
            bst.left = greaterSumTreeRec(bst.left,nodes);
        }
        return bst;
    }

    private static List<Integer> inOrder(Node<Integer> root){
        List<Integer> nodes = new LinkedList<>();

        if(root.left != null){
            for(Integer v: inOrder(root.left)){
                nodes.append(v);
            }
        }

        nodes.append(root.value);

        if(root.right != null){
            for(Integer v: inOrder(root.right)){
                nodes.append(v);
            }
        }

        return nodes;
    }*/

    private static int traverseR(Node<Integer> root){
        if(root == null){
            return 0;
        }else{
            return root.value + traverseR(root.left) + traverseR(root.right);
        }
    }

    private static int greaterSumTreeRec(Node<Integer> root, int suma){
        if(root.right != null){
            suma = greaterSumTreeRec(root.right,suma);
        }

        int aux = root.value;
        root.value = suma;
        suma += aux;

        if(root.left != null){
            suma = greaterSumTreeRec(root.left,suma);
        }

        return suma;
    }

    public static void greaterSumTree(BinaryTree<Integer> tree) {
        //TODO
        /*if(tree != null){
            List<Integer> nodes = inOrder(tree.root);
            tree.root = greaterSumTreeRec(tree.root,nodes);
        }*/
        // Necesito una variable donde vaya guardando la suma
        greaterSumTreeRec(tree.root,0);
    }
// -- SOLO PARA ALUMNOS A TIEMPO PARCIAL O FALTA JUSTIFICADA EN LA 2ª PRÁCTICA EVALUABLE
// -- ONLY FOR PART TIME STUDENTS OR STUDENTS THAT COULD NOT ATTEND SECOND ASSESSMENT DUE TO COVID

    public void mirroredTree() {
        //TODO
    }

    private static <T> List<List<T>> modifyLists(T val,List<T> preOrder, List<T> inOrder){
        return null;
    }

    public static <T extends Comparable<? super T>> BinaryTree<T> traversal2Tree(List<T> preorder, List<T> inorder) {
        //TODO
        return null;
    }
}
