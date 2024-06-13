package dataStructures.BinPacking; /**
 * Student's name:
 *
 * Student's group:
 */

import dataStructures.list.ArrayList;
import dataStructures.list.List;
import dataStructures.list.LinkedList;

import java.util.Iterator;


class Bin {
    private int remainingCapacity; // capacity left for this bin
    private List<Integer> weights; // weights of objects included in this bin

    public Bin(int initialCapacity) {
        remainingCapacity = initialCapacity;
        weights = new ArrayList<>();
    }

    // returns capacity left for this bin
    public int remainingCapacity() {
        return remainingCapacity;
    }

    // adds a new object to this bin
    public void addObject(int weight) {
        if(weight > remainingCapacity){
            throw new RuntimeException("El peso del objeto supera la capacidad restante");
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
            int lh = 0, rh = 0;
            if(left != null){
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
            int lc = 0, rc = 0;
            if(left != null){
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
            Node x = this.right;
            Node r1 = x.left;

            this.right = r1;
            this.setHeight();
            this.setMaxRemainingCapacity();

            x.left = this;
            x.setMaxRemainingCapacity();
            x.setHeight();

            return x;
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
        if(root == null){
            root = new Node();
            root.bin = bin;
        }else{
            Node current = root;
            while(current.right != null){
                current = current.right;
            }
            current.right = new Node();
            current.right.bin = bin;

            if(root.right.height - (root.left == null ? 0 : root.left.height) > 1){
                root = root.rotateLeft();
            }
        }
        root.setHeight();
        root.setMaxRemainingCapacity();
    }

    // adds an object to first suitable bin. Adds
    // a new bin if object cannot be inserted in any existing bin
    public void addFirst(int initialCapacity, int weight) {
        if(root==null || weight > root.maxRemainingCapacity){
            Bin b = new Bin(initialCapacity);
            b.addObject(weight);
            addNewBin(b);
        }else if(root.left != null && root.left.maxRemainingCapacity >= weight){
            Node current = root.left;
            while(current.maxRemainingCapacity<weight){
                current = current.left;
            }
            current.bin.addObject(weight);

        }else if(root.bin.remainingCapacity()>= weight){
            root.bin.addObject(weight);
        }else if (root.right != null){
            Node current = root.right;
            while(current.maxRemainingCapacity<weight){
                current = current.right;
            }
            current.bin.addObject(weight);
        }
        root.setMaxRemainingCapacity();
        root.setHeight();
    }

    public void addAll(int initialCapacity, int[] weights) {
        for (int w: weights) {
            addFirst(initialCapacity, w);
        }
    }

    public List<Bin> toList() {
        return (root==null) ? new ArrayList<>() : toListRec(root) ;
    }

    private List<Bin> toListRec(Node node) {
        List<Bin> list = new ArrayList<>();
        if(node.left != null){
            for(Bin b : toListRec(node.left)){
                list.append(b);
            }
        }
        list.append(node.bin);

        if(node.right != null){
            for(Bin b : toListRec(node.right)){
                list.append(b);
            }
        }

        return list;
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