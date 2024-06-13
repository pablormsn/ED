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

    private static int traverseR(Node<Integer> t){
        if(t == null){
            return 0;
        }else{
            return t.value + traverseR(t.left) + traverseR(t.right);
        }
    }

    private static Node<Integer> greaterSumTreeRec(int val, Node<Integer> left, Node<Integer> right, int suma){
        if(left == null && right == null){
            return new Node<>(suma,null,null);
        }else{
            int auxVal = val;
            val = traverseR(right) + suma;
            if(left == null){
                right = greaterSumTreeRec(right.value, right.left, right.right, suma);
            }else if(right == null){
                left = greaterSumTreeRec(left.value, left.left, left.right, (suma + auxVal + traverseR(null)));
            }else{
                left = greaterSumTreeRec(left.value, left.left, left.right, (suma + auxVal + traverseR(right)));
                right = greaterSumTreeRec(right.value, right.left, right.right, suma);
            }
            return new Node<>(val,left,right);
        }
    }

    public static void greaterSumTree(BinaryTree<Integer> tree) {
        //TODO
        int suma = 0;
        if(tree != null){
            tree.root = greaterSumTreeRec(tree.root.value,tree.root.left,tree.root.right,suma);
        }
    }
// -- SOLO PARA ALUMNOS A TIEMPO PARCIAL O FALTA JUSTIFICADA EN LA 2ª PRÁCTICA EVALUABLE
// -- ONLY FOR PART TIME STUDENTS OR STUDENTS THAT COULD NOT ATTEND SECOND ASSESSMENT DUE TO COVID

    private static <T> Node<T> mirroredTreeRec(Node<T> t){
        if(t == null){
            return null;
        }else if(t.left == null && t.right == null){
            return t;
        }else {
            Node<T> auxLeft = new Node<>(t.left.value,t.left.left,t.left.right);
            t.left = mirroredTreeRec(t.right);
            t.right = mirroredTreeRec(auxLeft);
            return t;
        }
    }

    public void mirroredTree() {
        //TODO
        root = mirroredTreeRec(root);
    }

    private static <T> List<List<T>> modifyLists(T val,List<T> preOrder, List<T> inOrder){
        preOrder.remove(0);

        List<T> preOrderLeft = new LinkedList<>();

        List<T> inOrderLeft = new LinkedList<>();

        List<T> preOrderRight = new LinkedList<>();

        List<T> inOrderRight = new LinkedList<>();

        List<List<T>> listas = new LinkedList<>();

        int i = 0;
        boolean found = false;
        // Añadimos los elementos a la lista inOrder de la izquierda
        while(i < inOrder.size() && !found){
            if(inOrder.get(0).equals(val)){
                found = true;
            }else{
                inOrderLeft.append(inOrder.get(0));
                inOrder.remove(0);
            }
            i++;
        }

        // Añado los elementos a la lista inOrder de la derecha
        for(T aux: inOrder){
            inOrderRight.append(aux);
        }

        // Añadimos los elementos de la lista preOrder de la izq
        for(int j = 0; j < inOrderLeft.size(); j++){
            preOrderLeft.append(preOrder.get(0));
            preOrder.remove(0);
        }

        // Añadimos los elementos de la lista preOrder de la der
        for(T aux: preOrder){
            preOrderRight.append(aux);
        }

        listas.append(preOrderLeft);
        listas.append(inOrderLeft);
        listas.append(preOrderRight);
        listas.append(inOrderRight);

        return listas;
    }

    private static <T extends Comparable<? super T>> void traversal2TreeRec(List<T> preOrder, List<T> inOrder, Node<T> t){
        if(!preOrder.isEmpty() && !inOrder.isEmpty()){
            t = new Node<>(preOrder.get(0));
            if(preOrder.size() > 1){
                List<List<T>> lists = modifyLists(t.value,preOrder,inOrder);
                traversal2TreeRec(lists.get(0), lists.get(1),t.left);
                traversal2TreeRec(lists.get(2), lists.get(3),t.right);
            }
        }
    }

    public static <T extends Comparable<? super T>> BinaryTree<T> traversal2Tree(List<T> preorder, List<T> inorder) {
        //TODO
        BinaryTree<T> bt = new BinaryTree<>();
        if(preorder.isEmpty() && inorder.isEmpty()){
            return null;
        }else{
            bt.root = new Node<>(preorder.get(0));
            List<List<T>> lists = modifyLists(bt.root.value,preorder,inorder);
            traversal2TreeRec(lists.get(0), lists.get(1),bt.root.left);
            traversal2TreeRec(lists.get(2), lists.get(3),bt.root.right);
            return bt;
        }
    }
}
