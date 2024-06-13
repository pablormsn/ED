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

    public static void greaterSumTree(BinaryTree<Integer> tree) {
        //TODO
    }
// -- SOLO PARA ALUMNOS A TIEMPO PARCIAL O FALTA JUSTIFICADA EN LA 2ª PRÁCTICA EVALUABLE
// -- ONLY FOR PART TIME STUDENTS OR STUDENTS THAT COULD NOT ATTEND SECOND ASSESSMENT DUE TO COVID

    public void mirroredTree() {
        //TODO
    }

    private static <T> List<List<T>> modifyLists(T val,List<T> preOrder, List<T> inOrder){
    }

    public static <T extends Comparable<? super T>> BinaryTree<T> traversal2Tree(List<T> preorder, List<T> inorder) {
        //TODO
    }
}
