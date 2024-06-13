/* ------------------------------------------------------------------------------
   -- Student's name:
   -- Student's group:
   -- Identity number (DNI if Spanish/passport if Erasmus):
   --
   -- Data Structures. Grado en Informática. UMA.
   -------------------------------------------------------------------------------
*/

package exercises;
import dataStructures.tuple.Tuple2;

import javax.swing.text.StyledEditorKit;

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
// -- EXERCISE 2

    private static <T extends Comparable<? super T>> Tuple2<Integer,Boolean> subTreesInRangeRec (T min, T max, Node<T> root){
        // Necesito que los nodos hijos me devuelvan el número de subárboles que tienen y si está en el rango
        // Primero miramos que mi árbol no este vacío
        if(root == null){
            return new Tuple2<>(0,true);
        }else{
            // Crearé una tuple donde almacenaré un número y un boolean
            Tuple2<Integer,Boolean> info;
            // Ahora iteraremos sobre los hijos
            // Vamos a mirar que no estemos sobre una hoja
            if(root.left == null && root.right == null){
                // En una hoja miramos que esté en rango
                if(root.value.compareTo(min) >= 0 && root.value.compareTo(max) <= 0){
                    info = new Tuple2<>(1,true);
                }else{
                    info = new Tuple2<>(0,false);
                }
            }else if(root.value.compareTo(max) > 0){
                info = subTreesInRangeRec(min,max,root.left);
            }else if(root.value.compareTo(min) < 0){
                info = subTreesInRangeRec(min,max,root.right);
            }else{
                Tuple2<Integer,Boolean> leftInfo = subTreesInRangeRec(min,max,root.left);
                Tuple2<Integer,Boolean> rightInfo = subTreesInRangeRec(min,max,root.right);
                if(leftInfo._2() && rightInfo._2()){
                    info = new Tuple2<>(1 + leftInfo._1()+ rightInfo._1(), true);
                }else{
                    info = new Tuple2<>(leftInfo._1()+ rightInfo._1(), false);
                }
            }
            return info;
        }
    }

    public int subTreesInRange(T min, T max) {
        // Es un árbol vacío
        if(isEmpty()){
            return 0;
        }else{
            // Vamos a mirar ambos hijos primero
            Tuple2<Integer,Boolean> info = subTreesInRangeRec(min,max,root); // Itero sobre el árbol hasta los hijos y voy retrocediendo con la información
            return info._1();
        }
    }
}
