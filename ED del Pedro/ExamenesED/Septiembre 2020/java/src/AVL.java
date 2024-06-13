/**
 * Student's name:
 *
 * Student's group:
 */

import dataStructures.list.List;
import dataStructures.list.LinkedList;

import java.util.Iterator;


class Bin {
    private int remainingCapacity; // capacity left for this bin
    private List<Integer> weights; // weights of objects included in this bin

    public Bin(int initialCapacity) {
        remainingCapacity = initialCapacity;
        weights = new LinkedList<>();
    }

    // returns capacity left for this bin
    public int remainingCapacity() {

        return remainingCapacity;
    }

    // adds a new object to this bin
    public void addObject(int weight) {
        if(weight > remainingCapacity){
            throw new RuntimeException("Weight higher than capacity");
        }

        weights.append(weight);
        remainingCapacity -= weight;
    }

    // returns an iterable through weights of objects included in this bin
    public Iterable<Integer> objects() {
        // todo
        //  SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        //  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        return null;
    }

    public String toString() {
        String className = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder(className);
        sb.append("(");
        sb.append(remainingCapacity);
        sb.append(", ");
        sb.append(weights.toString());
        sb.append(")");
        return sb.toString();
    }
}

// Class for representing an AVL tree of bins
public class AVL {
    static private class Node {
        Bin bin; // Bin stored in this node
        int height; // height of this node in AVL tree
        int maxRemainingCapacity; // max capacity left among all bins in tree rooted at this node
        Node left, right; // left and right children of this node in AVL tree

        // recomputes height of this node
        void setHeight() {
            int lh = 0;
            int rh = 0;
            if (left != null){
                left.setHeight();
                lh = left.height;
            }

            if(right != null){
                right.setHeight();
                rh = right.height;
            }

            height = 1 + Math.max(lh, rh);
        }

        // recomputes max capacity among bins in tree rooted at this node
        void setMaxRemainingCapacity() {
            int lc = 0;
            int rc = 0;
            if (left != null){
                left.setMaxRemainingCapacity();
                lc = left.maxRemainingCapacity;
            }
            if(right != null){
                right.setMaxRemainingCapacity();
                rc = right.maxRemainingCapacity;
            }
            maxRemainingCapacity = Math.max(bin.remainingCapacity(), Math.max(lc, rc));
        }

        // left-rotates this node. Returns root of resulting rotated tree
        Node rotateLeft() {
            Node root = right;
            Node r1 = root.left;

            right = r1;

            this.setMaxRemainingCapacity();
            this.setHeight();

            root.left = this;

            root.setMaxRemainingCapacity();
            root.setHeight();

            return root;
        }
    }

    private static int height(Node node) {

        return node.height;
    }

    private static int maxRemainingCapacity(Node node) {

        return node.maxRemainingCapacity;
    }

    private Node root; // root of AVL tree

    public AVL() {
        this.root = null;
    }

    // adds a new bin at the end of right spine.
    private void addNewBin(Bin bin) {
        root = addNewBinRec(root, bin);
    }

    private Node addNewBinRec (Node node, Bin bin){

        if(node == null){
            node = new Node();
            node.bin = bin;
            node.height = 1;
            node.maxRemainingCapacity = bin.remainingCapacity();
        }else if (node.right == null){
            node.right = new Node();
            node.right.bin = bin;
            node.right.height = 1;
            node.right.maxRemainingCapacity = bin.remainingCapacity();
            node.setHeight();
            node.setMaxRemainingCapacity();
        }else if(node.left == null){
            node.right = addNewBinRec(node.right, bin);
            node.right = node.right.rotateLeft();
        }else {
            node.right = addNewBinRec(node.right, bin);
            node.right.setHeight();
            node.right.setMaxRemainingCapacity();
        }

        return node;
    }

    // adds an object to first suitable bin. Adds
    // a new bin if object cannot be inserted in any existing bin
    public void addFirst(int initialCapacity, int weight) {
        root = addFirstRec(root, initialCapacity, weight);
    }

    private Node addFirstRec(Node node, int c, int w){

        if (node == null || w > node.maxRemainingCapacity){
            Bin b = new Bin(c);
            b.addObject(w);
            node = addNewBinRec(node, b);
        }else if (node.left != null && node.left.maxRemainingCapacity >= w){
            node.left = addFirstRec(node.left, c, w);
        }else if (node.bin.remainingCapacity() >= w){
            node.bin.addObject(w);
        }else {
            node.right = addFirstRec(node.right, c, w);
        }

        node.setHeight();
        node.setMaxRemainingCapacity();

        return node;
    }

    public void addAll(int initialCapacity, int[] weights) {

        for (int w : weights){
            addFirst(initialCapacity, w);
        }
    }

    public List<Bin> toList() {

        return root == null ? new LinkedList<>() : toListRec(root);
    }

    private List<Bin> toListRec(Node node){
        List<Bin> l1 = new LinkedList<>();
        List<Bin> l2 = new LinkedList<>();

        if (node.left != null){
            l1 = toListRec(node.left);
        }

        l1.append(node.bin);

        if (node.right != null){
            l2 = toListRec(node.right);
        }

        for (Bin b : l2){
            l1.append(b);
        }

        return l1;
    }

    public String toString() {
        String className = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder(className);
        sb.append("(");
        stringBuild(sb, root);
        sb.append(")");
        return sb.toString();
    }

    private static void stringBuild(StringBuilder sb, Node node) {
        if(node==null)
            sb.append("null");
        else {
            sb.append(node.getClass().getSimpleName());
            sb.append("(");
            sb.append(node.bin);
            sb.append(", ");
            sb.append(node.height);
            sb.append(", ");
            sb.append(node.maxRemainingCapacity);
            sb.append(", ");
            stringBuild(sb, node.left);
            sb.append(", ");
            stringBuild(sb, node.right);
            sb.append(")");
        }
    }
}

class LinearBinPacking {
    public static List<Bin> linearBinPacking(int initialCapacity, List<Integer> weights) {
        // todo
        //  SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        //  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        return null;
    }
	
	public static Iterable<Integer> allWeights(Iterable<Bin> bins) {
        // todo
        //  SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        //  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        return null;		
	}
}