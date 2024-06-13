import dataStructures.list.ArrayList;
import dataStructures.list.List;
import exercises.BinaryTree;

public class Traversal2TreeDemo {

    public static void main(String[] args) {
        List<Integer> preorder = new ArrayList<>();
        for (int i : new int[]{7, 5, 8, 6, 13, 4, 1, 15, 2, 9, 17}) {
            preorder.append(i);
        }
        List<Integer> inorder = new ArrayList<>();
        for (int i : new int[]{8, 5, 6, 7, 1, 15, 4, 13, 2, 17, 9}) {
            inorder.append(i);
        }
        BinaryTree<Integer> t = BinaryTree.traversal2Tree(preorder, inorder);
        System.out.println("Árbol reconstruido " + t);
    }
}
