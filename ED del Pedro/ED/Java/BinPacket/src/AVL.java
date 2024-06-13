/**
 * Student's name:
 *
 * Student's group:
 */

import dataStructures.list.List;
import dataStructures.list.LinkedList;
import dataStructures.list.ListException;

import java.util.Iterator;


class Bin {
    private int remainingCapacity; // capacity left for this bin
    private List<Integer> weights; // weights of objects included in this bin

    public Bin(int initialCapacity) {
        // todo
        this.remainingCapacity = initialCapacity;
        // Inicializamos la lista de pesos de nuestros objetos, vacía
        weights = new LinkedList<>();
    }

    // returns capacity left for this bin
    public int remainingCapacity() {
        return this.remainingCapacity;
    }

    // adds a new object to this bin
    public void addObject(int weight) {
        // todo
        if(weight > remainingCapacity){
            throw new ListException("No cabe en el cubo");
        }
        weights.append(weight);
        this.remainingCapacity -= weight;
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
            // todo
            height = 1 + Math.max(left.height, right.height);
        }

        // recomputes max capacity among bins in tree rooted at this node
        void setMaxRemainingCapacity() {
            // todo
            maxRemainingCapacity = Math.max(this.bin.remainingCapacity(),Math.max(left.maxRemainingCapacity, right.maxRemainingCapacity));
        }

        // left-rotates this node. Returns root of resulting rotated tree
        Node rotateLeft() {
            // todo
            // Guardo el elemento izquierdo de mi árbol derecho
            Node rotatedTree = this.right;
            Node r1 = rotatedTree.left;

            // Hago mi nuevo árbol izquierdo
            this.right = r1;
            this.setHeight();
            this.setMaxRemainingCapacity();

            // Finalizo la rotación
            rotatedTree.left = this;
            rotatedTree.setHeight();
            rotatedTree.setMaxRemainingCapacity();

            return rotatedTree;
        }
    }

    private static int height(Node node) {
        // todo
        return node.height;
    }

    private static int maxRemainingCapacity(Node node) {
        // todo
        return node.maxRemainingCapacity;
    }

    private Node root; // root of AVL tree

    public AVL() {
        this.root = null;
    }

    // adds a new bin at the end of right spine.
    private void addNewBin(Bin bin) {
        // todo
        // Un nodo vacío
        if(root == null){
            root = new Node();
            root.bin = bin;
            root.maxRemainingCapacity = root.bin.remainingCapacity();
            root.height = 1;
            root.left = null;
            root.right = null;
        }else{
            // Iteramos recursivamente sobre nuestro árbol AVL.
            root = addNewBinRec(bin,root);
        }
    }

    private static Node addNewBinRec(Bin bin, Node root){
        // Como siempre añado en la parte derecha solo compruebo si el árbol derecho es nulo.
        if(root.right == null){
            root.right = new Node();
            root.right.bin = bin;
            root.right.right = null;
            root.right.left = null;
        }else{
            // Si el nudo derecho no es nulo itero sobre él.
            root.right = addNewBinRec(bin,root.right); // Guardo el valor que me envíe el método, dado que me devuelve el root.right más el nuevo nodo que le añadamos.
        }
        // Siempre comprobamos si hay que rotar o no.
        if((root.right == null ? 0: root.right.height) - (root.left == null ? 0: root.left.height) > 1){
            root = root.rotateLeft();
        }
        return root;
    }

    // adds an object to first suitable bin. Adds
    // a new bin if object cannot be inserted in any existing bin
    public void addFirst(int initialCapacity, int weight) {
        // todo
        // Comprobamos si el nodo donde estamos es null o la capacidad máxima no es mayor que el peso a añadir.
        if(this.root == null || root.maxRemainingCapacity < weight){
           // Creamos nuestro nuevo cubo
           Bin bin = new Bin(initialCapacity);
           bin.addObject(weight);
           addNewBin(bin);
        }else{ // Ahora tenemos que iterar recursivamente sobre nuestro árbol.
            // Para ello creamos una nueva función que devuelva un tipo Node. Así ya modificamos nuestro árbol respetando el invariante
            root = addFirstRec(initialCapacity,weight,root);
        }
    }


    private static Node addFirstRec(int initialCapacity, int weight, Node root){
        // Primero vamos a comprobar de nuevo si es nulo nuestro nodo
        if(root == null){
            // Puede haber algún caso donde nos metemos en el else, pero no tenemos la seguridad de que el nodo derecho no este vacío.
            root = new Node();
            root.right = null;
            root.left = null;
            root.bin = new Bin(initialCapacity);
            root.bin.addObject(weight);
        }else if(root.left != null && root.left.maxRemainingCapacity >= weight){ // Comprobamos que el maxRemainingCapacity de nuestro izquierdo sea mayor que el peso que queremos añadir.
            root.left = addFirstRec(initialCapacity,weight,root.left);
        }else if(root.bin.remainingCapacity() >= weight){ // Miramos el nodo actual.
            root.bin.addObject(weight);
        }else{ // En cualquier otro caso se añade el peso a un nodo del hijo derecho.
            root.right = addFirstRec(initialCapacity,weight,root.right);
        }
        // Devolvemos root
        return root;
    }
    public void addAll(int initialCapacity, int[] weights) {
        // todo
        for(int w: weights){
            addFirst(initialCapacity,w);
        }
    }

    private static List<Bin> toListRec(Node root){
        List<Bin> list = new LinkedList<>();

        if(root.left != null){
            for(Bin c: toListRec(root.left)){
                list.append(c);
            }
        }

        list.append(root.bin);

        if(root.right != null){
            for(Bin c: toListRec(root.right)){
                list.append(c);
            }
        }

        return list;
    }

    public List<Bin> toList() {
        // todo
        // Recorrido en orden
        List<Bin> list = new LinkedList<>();

        for(Bin cubo: toListRec(root.left)){
            list.append(cubo);
        }

        list.append(root.bin);

        for(Bin cubo: toListRec(root.right)){
            list.append(cubo);
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