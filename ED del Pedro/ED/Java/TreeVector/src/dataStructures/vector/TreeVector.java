/******************************************************************************
 * Student's name:
 * Student's group:
 * Data Structures. Grado en Informática. UMA.
******************************************************************************/

package dataStructures.vector;

import dataStructures.list.ArrayList;
import dataStructures.list.LinkedList;
import dataStructures.list.List;

public class TreeVector<T> {

    private int size;
    private Tree<T> root;

    private interface Tree<E> {
        E get(int index);

        void set(int index, E x);

        List<E> toList();
    }

    private static class Leaf<E> implements Tree<E> {
        private E value;

        public Leaf(E x) {
            value = x;
        }

        @Override
        public E get(int i) {
        	//to do
            return value;
        }

        @Override
        public void set(int i, E x) {
        	//to do
            this.value = x;
        }

        @Override
        public List<E> toList() {
        	//to do
            List<E> lista = new LinkedList<>();
            lista.append(value);
            return lista;
        }
    }

    private static class Node<E> implements Tree<E> {
        private Tree<E> left;
        private Tree<E> right;

        public Node(Tree<E> l, Tree<E> r) {
            left = l;
            right = r;
        }

        @Override
        public E get(int i) {
        	//to do
            if(i % 2 == 0){ // Si el elemento es par devuelvo el elemento que se encuentra en su lado izquierdo
                return left.get(i/2);
            }else{ // Elemento del lado derecho
                return right.get(i/2);
            }
        }

        @Override
        public void set(int i, E x) {
        	//to do
            if(i % 2 == 0){
                left.set(i/2,x);
            }else{
                right.set(i/2,x);
            }
        }

        @Override
        public List<E> toList() {
        	//to do
            List<E> leftList = new LinkedList<>();
            List<E> rightList = new LinkedList<>();
            for (E l: left.toList()) {
                leftList.append(l);
            }

            for (E r: right.toList()) {
                rightList.append(r);
            }

            List<E> newList = intercalate(leftList,rightList);

            return newList;
        }
    }

    public TreeVector(int n, T value) {
    	//to do
        if(n < 0){
            throw new VectorException("Invalid size");
        }
        this.size = (int) Math.pow(2,n);
        this.root = mkTree(root,n,value);
    }

    private static <T> Tree<T> mkTree(Tree<T> t, int height, T el){
        if(height == 0){
            t = new Leaf<>(el);
        }else{
            t = new Node<>(mkTree(t,height-1,el), mkTree(t, height-1, el));
        }
        return t;
    }

    public int size() {
    	//to do
        return this.size;
    }

    public T get(int i) {
    	//to do
        return root.get(i);
    }

    public void set(int i, T x) {
    	//to do
        root.set(i,x);
    }

    public List<T> toList() {
    	//to do
        return root.toList();
    }

    protected static <E> List<E> intercalate(List<E> xs, List<E> ys) {
    	//to do
        int elemToGet = 0;
        boolean stop = false;
        List<E> newList = new LinkedList<>();
        if(xs.isEmpty() && ys.isEmpty()) stop = true;
        while(!stop) {
            if (xs.isEmpty() && !ys.isEmpty()) {
                newList.append(ys.get(0));
                ys.remove(0);
                stop = true;
            } else if (!xs.isEmpty() && ys.isEmpty()){
                stop = true;
            }else{
                if(elemToGet % 2 == 0){
                    newList.append(xs.get(0));
                    xs.remove(0);
                }else{
                    newList.append(ys.get(0));
                    ys.remove(0);
                }
                elemToGet++;
            }
        }
        return newList;
    }

    static protected boolean isPowerOfTwo(int n) {
    	//to do
        boolean esPotencia = true;
        while(esPotencia && n > 1){
            if(n % 2 != 0) esPotencia = false;
            else n /= 2;
        }
        return esPotencia;
    }

    public static <E> TreeVector<E> fromList(List<E> l) {
    	//to do
        // Darle vueltas
        if (!isPowerOfTwo(l.size())){
            throw new VectorException("La longitud de la lista no es potencia de dos");
        }
        return fromListRec(l);
    }

    private static <T> TreeVector<T> fromListRec(List<T> l){
        // Compruebo si el tamaño de mi lista es 1
        if(l.size() == 1){
            return new TreeVector<>(0,l.get(0));
        }else{
            // Separamos los elementos por posiciones pares o impares
            List<T> even = new ArrayList<>();
            List<T> odd = new ArrayList<>();

            for(int i = 0; i < l.size(); i++){
                if(i % 2 == 0){
                    even.append(l.get(i));
                }else{
                    odd.append(l.get(i));
                }
            }
            // Una vez lo separamos, creamos los árboles de la derecha e izquierda
            TreeVector<T> right = fromListRec(even);
            TreeVector<T> left = fromListRec(odd);
            // Ahora creamos el TreeVector solución
            TreeVector<T> res = new TreeVector<>(0,null);
            res.root = new Node<>(right.root,left.root);
            res.size = l.size();
            return res;
        }
    }

}
