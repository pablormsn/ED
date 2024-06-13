import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.lang.Comparable;

public class TreeMultiMap<K extends Comparable<? super K>,V extends Comparable<? super V>>{
    private static class Tree<K,V>{
        K key;
        List<V> value = new LinkedList<>();
        Tree<K,V> left;
        Tree<K,V> right;

        public Tree(K x, List<V> xs){
            this.key = x;
            this.value = xs;
            left = null;
            right = null;
        }
    }

    private Tree<K,V> root;
    private int sz;

    public TreeMultiMap(){
        root = null;
        sz = 0;
    }

    public TreeMultiMap<K,V> empty(){
        return new TreeMultiMap<>();
    }

    public boolean isEmpty(){
        return root == null;
    }

    public int size(){
        return root == null ? 0: this.sz;
    }

    public boolean isDefinedAt(K elm){
        return isDefinedAtRec(elm,root);
    }

    private static <K extends Comparable<? super K>,V extends Comparable<? super V>> boolean isDefinedAtRec(K elm,Tree<K,V> tree){
        if(tree != null){
            if(elm.compareTo(tree.key) == 0){
                return true;
            }else{
                return isDefinedAtRec(elm,tree.left) || isDefinedAtRec(elm,tree.right);
            }
        }else{
            return false;
        }
    }

    public void insert(K elm, List<V> values){
        if(root == null){
            root = new Tree<>(elm,values);
            sz++;
        }else{
            root = insertRec(elm,values,root);
            sz++;
        }
    }

    private static <K extends Comparable<? super K>,V extends Comparable<? super V>> Tree<K,V> insertRec(K elm, List<V> values, Tree<K,V> tree) {
        if(tree != null){
            if (isDefinedAtRec(elm, tree)) {
                if (elm.compareTo(tree.key) == 0) {
                    for (int i = 0; i < values.size(); i++) {
                        if (!tree.value.contains(values.get(i))) {
                            tree.value.add(values.get(i));
                        }
                    }
                } else if (elm.compareTo(tree.key) > 0) {
                    return insertRec(elm, values, tree.right);
                } else {
                    return insertRec(elm, values, tree.left);
                }
            } else {
                boolean isLeaf = tree.key != null && tree.left == null && tree.right == null;
                if (!isLeaf) {
                    if (elm.compareTo(tree.key) < 0) {
                        tree.left = insertRec(elm, values, tree.left);
                    } else {
                        tree.right = insertRec(elm, values, tree.right);
                    }
                } else {
                    if (elm.compareTo(tree.key) < 0) {
                        tree.left = insertRec(elm, values, tree.left);
                    } else {
                        tree.right = insertRec(elm, values, tree.right);
                    }
                }
            }
        }else{
            tree = new Tree<>(elm,values);
        }
        return tree;
    }

    public List<V> valuesOf (K elm){
        if(isDefinedAt(elm)){
            return valuesOfRec(elm,root).value;
        }else{
            return null;
        }
    }

    private static <K extends Comparable<? super K>,V extends Comparable<? super V>> Tree<K,V> valuesOfRec(K elm, Tree<K,V> tree){
        if(elm.compareTo(tree.key) == 0){
            return tree;
        }else if(elm.compareTo(tree.key) > 0){
            return valuesOfRec(elm,tree.right);
        }else{
            return valuesOfRec(elm,tree.left);
        }
    }

    public void deleteKey(K elm){
        if(isDefinedAt(elm)) {
            root = deleteKeyRec(elm, root);
        }
    }

    private static <K extends Comparable<? super K>,V extends Comparable<? super V>> Tree<K,V> deleteKeyRec(K elm, Tree<K,V> tree){
        if(tree != null) {
            if (tree.key.compareTo(elm) == 0) {
                if(tree.right == null){
                    tree = tree.left;
                }else if(tree.left == null){
                    tree = tree.right;
                }else{
                    tree.right = modificarTreeRec(tree.right,tree);
                }
            } else if (elm.compareTo(tree.key) > 0) {
                return deleteKeyRec(elm,tree.right);
            } else {
                return deleteKeyRec(elm,tree.left);
            }
        }
        return tree;
    }

    private static <K extends Comparable<? super K>,V extends Comparable<? super V>> Tree<K,V> modificarTreeRec(Tree<K,V> right,Tree<K,V> tree){
        if(tree.left == null){
            tree.key = right.key;
            tree.value = right.value;
            return tree.right;
        }else{
            tree.left.key = modificarTreeRec(tree.left,tree).key;
            tree.left.value = modificarTreeRec(tree.left,tree).value;
            return tree;
        }
    }

    private static String toStringRec(Tree<?, ?> tree) {
        return tree == null ? "null" : "Node<" + toStringRec(tree.left) + ","
                + tree.key + "," + tree.value + "," + toStringRec(tree.right) + ">";
    }
    /**
     * Returns representation of tree as a String.
     */
    @Override public String toString() {
        String className = getClass().getSimpleName();
        return className+"("+toStringRec(this.root)+")";
    }
}
