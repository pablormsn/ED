import dataStructures.list.ArrayList;
import dataStructures.list.List;
import dataStructures.list.ListException;

class Bin{
    private int remainingCapacity;
    private List<Integer> weights;

    public Bin(int initialCapacity){
        remainingCapacity = initialCapacity;
        weights = new ArrayList<>();
    }

    public int remainingCapacity(){
        return this.remainingCapacity;
    }

    public void addObject(int weight){
        if(weight > remainingCapacity){
            throw new ListException("No cabe en nuestro cubo");
        }
        this.weights.append(weight);
        remainingCapacity = remainingCapacity-weight;
    }
}

public class AVL {
    static private class Node{
        Bin bin;
        int height;
        int maxRemainingCapacity;
        Node left,right;

        void setMaxRemainingCapacity(){
            int cl = 0, cr = 0;
            if(left != null){
                left.setMaxRemainingCapacity();
                cl = left.maxRemainingCapacity;
            }
        }

        void setHeight(){
            int lh = setHeightRec(left);
            int rh = setHeightRec(right);
            height = 1 + Math.max(lh,rh);
        }

        private static int setHeightRec(Node node){
            if(node == null){
                return 0;
            }else if(node.left == null && node.right == null){
                return 1;
            }else{
                return 1 + setHeightRec(node.left) + setHeightRec(node.right);
            }
        }
    }
    private Node root;

    private static int maxRemainingCapacity(Node n){
        return n.maxRemainingCapacity;
    }

    private static int height(Node n){
        return n.height;
    }

}
