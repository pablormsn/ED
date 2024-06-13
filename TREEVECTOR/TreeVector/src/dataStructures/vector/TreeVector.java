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
            return value;//devolverá el valor de la hoja
        }

        @Override
        public void set(int i, E x) {
        	//to do
            this.value = x;//setea el valor de la hoja al recibido por parámetro
        }

        @Override
        public List<E> toList() {
        	//to do
            List<E> lista = new LinkedList<>();
            lista.append(value);//añade el valor de la hoja a la lista
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
            if(i%2==0){//Si el nodo interno es par devuelvo el elemento que se encuentra en su lado izquierdo
                return left.get(i/2);
            }else{//Si el nodo interno es impar devuelvo el elemento que se encuentra en su lado derecho
                return right.get(i/2);
            }
        }

        @Override
        public void set(int i, E x) {
        	//to do
            if(i%2==0){//Si el nodo interno es par seteo el elemento que se encuentra en su lado izquierdo
                left.set(i/2,x);
            }else{//Si el nodo interno es impar seteo el elemento que se encuentra en su lado derecho
                right.set(i/2,x);
            }
        }

        @Override
        public List<E> toList() {
        	//to do
            List<E> leftList = new LinkedList<>();
            List<E> rightList = new LinkedList<>();
            for(E l : left.toList()){//Recorro la lista de la izquierda
                leftList.append(l);//Añado los elementos de la lista de la izquierda a la lista de la izquierda
            }
            for(E r : right.toList()){//Recorro la lista de la derecha
                rightList.append(r);//Añado los elementos de la lista de la derecha a la lista de la derecha
            }

            List<E> newList = intercalate(leftList, rightList);
            return newList;
        }
    }

    public TreeVector(int n, T value) {
    	//to do
        if(n<0){
            throw new VectorException("El tamaño del vector no puede ser negativo");
        }
        this.size = (int) Math.pow(2, n);
        this.root = mkTree(root, n, value);
    }

    private static <T> Tree<T> mkTree(Tree<T> t, int height, T elm){
        //to do
        if(height==0){//Si la altura es 0 devuelvo una hoja con el elemento, su raiz
            return new Leaf<>(elm);
        }
        else{
            t=new Node<>(mkTree(t, height-1, elm), mkTree(t,height-1,elm));//Si la altura es mayor que 0 devuelvo un nodo con dos hijos, el hijo izquierdo y el hijo derecho
        }
        return t;
    }

    public int size() {
    	//to do
        return this.size;
    }

    public T get(int i) {
    	//to do
        if(i<0 || i>=size){
            throw new VectorException("El índice está fuera de rango");
        }
        return root.get(i);
    }

    public void set(int i, T x) {
    	//to do
        if(i<0 || i>=size){
            throw new VectorException("El índice está fuera de rango");
        }
        root.set(i,x);
    }

    public List<T> toList() {
    	//to do
        return root.toList();
    }

    protected static <E> List<E> intercalate(List<E> xs, List<E> ys) {
    	//to do
        int elemToGet = 0;//Elemento de la nueva lista que vamos a añadir
        boolean stop = false;//Variable que nos va a decir cuando parar
        List<E> newList = new LinkedList<>();//Lista intercalada
        if(xs.isEmpty() && ys.isEmpty()){ //Si ambas listas están vacías paro de recorrer.
            stop=true;//Para
        }
        while(!stop){//Mientras no se pare
            if(xs.isEmpty() && !ys.isEmpty()){//Si la segunda lista no está vacía y la primera sí
                newList.append(ys.get(0));//Se añade a la lista intercalada
                ys.remove(0);//Se elimina de la segunda lista
                stop=true;//Ya no se añaden más, porque quedaría desequilibrado
            }else if(!xs.isEmpty() && ys.isEmpty()){
                stop=true;//Se para porque si se añade el elemento de xs quedaría desequilibrado
            }else{
                if(elemToGet%2==0){//Si la posición es par
                    newList.append(xs.get(0));//Añade el primer elemento de la primera lista
                    xs.remove(0);//Lo elimina
                }else{//Si la posición es impar
                    newList.append(ys.get(0));//Añade el primer elemento de la segunda lista
                    ys.remove(0);//Lo elimina
                }
                elemToGet++;//Pasa al siguiente elemento
            }
        }
        return newList;//Devuelve la lista intercalada
    }

    static protected boolean isPowerOfTwo(int n) {
    	//to do
        boolean esPotencia=true; //Booleano que dirá si n es potencia o no
        while(esPotencia && n>1){
            if(n%2!=0){
                esPotencia=false;
            }else{
                n = n/2;
            }
        }
        return esPotencia;
    }

    public static <E> TreeVector<E> fromList(List<E> l) {
    	//to do
        if(!isPowerOfTwo(l.size())){//Si la longitud de la lista no es potencia de 2
            throw new VectorException("La longitud debe ser potencia de 2");
        }
        return fromListRec(l);
    }

    private static <T> TreeVector<T> fromListRec(List<T> l){
        if(l.size()==1){
            return new TreeVector<>(1, l.get(0));//Devuelve un nuevo árbol de tamaño 1 y raíz el único elemento de la lista
        }else{
            List<T> pares = new ArrayList<>();
            List<T> impares = new ArrayList<>();

            for(int i=0; i< l.size(); i++){
                if(i%2==0){
                    pares.append(l.get(i));
                }else{
                    impares.append(l.get(i));
                }
            }

            TreeVector<T> right = fromListRec(impares);
            TreeVector<T> left = fromListRec(pares);

            TreeVector<T> res = new TreeVector<>(0, null);
            res.root = new Node<>(right.root, left.root);
            res.size = l.size();
            return res;
        }
    }
}
