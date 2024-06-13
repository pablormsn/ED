import exercises.BinaryTree;

public class GreaterSumDemo {

    public static void main(String[] args) {
        Integer[] values = {11, 2, 1, 7, 29, 15, 40, 35};
        BinaryTree<Integer> t = mkBST(values);
        System.out.println("Árbol original " + t);

        BinaryTree.greaterSumTree(t);
        System.out.println("Árbol de suma máxima " + t);

        t.mirroredTree();
        System.out.println("Árbol reflejado " + t);
    }

    private static <T extends Comparable<? super T>> BinaryTree<T> mkBST(T[] values) {
        BinaryTree<T> t = new BinaryTree<>();
        for (T v : values) {
            t.insertBST(v);
        }
        return t;
    }
}
