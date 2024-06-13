/********************************************************************
 * Estructuras de Datos. 2º Curso. ETSI Informática. UMA
 * PRACTICA 4.
 * Ejercicio 12 de la tercera relación. Implementar el TAD Bolsa
 *
 * Alumno: APELLIDOS, NOMBRE
 ********************************************************************/

package dataStructures.bag;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedLinkedBag<T extends Comparable<? super T>> implements Bag<T> {

    static private class Node<E> {
        private E elem;
        private int count;
        private Node<E> next;

        Node(E x, int n, Node<E> node) {
            elem = x;
            count = n;
            next = node;
        }
    }

    /**
     * Invariant:
     * <p>
     * 1. keep the linked list sorted by "elem", with no duplicates
     * <p>
     * 2. the counter "count" must be positive
     * <p>
     * Example: first -> ('a', 5) -> ('d', 1) -> ('t', 3) -> ('z', 2)
     */
    private Node<T> first;

    public SortedLinkedBag() {
        first = null;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public void insert(T item) {
        // TODO
        // Método concurrencia/Joaquin padre todopoderoso
        /*
        Node<T> newNode = null;
        if(isEmpty()){
            newNode = new Node<>(item,1,null);
            first = newNode;
        }else if(first.next == null){
            // Tiene que estar ordenado
            if(first.elem.compareTo(item) == 0){
                first.count++;
            }else{
                newNode = new Node<>(item,1,null);
                if(item.compareTo(first.elem) > 0){
                    first.next = newNode;
                }else{
                    newNode.next = first;
                    first = newNode;
                }
            }
        }else{
            Node<T> previous = null;
            Node<T> current = first;
            while(current != null && current.elem.compareTo(item) < 0){
                previous = current;
                current = current.next;
            }

            boolean found = current != null && current.elem.equals(item); // Para asegurar que el nodo donde nos paramos es igual al item

            if(found){
                current.count++;
            }else{
                newNode = new Node<>(item,1,current);
                previous.next = newNode;
            }
        }*/

        // Método Pablo López
        Node<T> previous = null;
        Node<T> current = first;

        while(current != null && current.elem.compareTo(item) < 0){
            previous = current;
            current = current.next;
        }

        boolean foundIdem = current != null && current.elem.equals(item);

        if(foundIdem){
            current.count++;
        }else{
            if(previous == null){
                first = new Node(item,1,first);
            }else{
                previous.next = new Node(item,1,current);
            }
        }
    }

    @Override
    public int occurrences(T item) {
        // TODO
        Node<T> itAux = first;

        while(itAux != null && !itAux.elem.equals(item)){
            itAux = itAux.next;
        }

        boolean found = itAux != null;

        if(found){
            return itAux.count;
        }else{
            return 0;
        }
    }

    @Override
    public void delete(T item) {
        // TODO
        Node<T> previous = null;
        Node<T> current = first;

        while(current != null && !current.elem.equals(item)){
            previous = current;
            current = current.next;
        }

        boolean found = current != null;

        if(found){
            if(current.count > 1){
                current.count--;
            }else{
                if(previous == null){
                    first = current.next;
                }else{
                    previous.next = current.next;
                }
            }
        }
    }

    @Override
    public void copyOf(Bag<T> source) {
        // Copia los nodos, usando los que ya posee la bolsa actual, para no
        // crear nuevos nodos en memoria de ineficiente.
        // Current bag nodes
        Node<T> current = first;
        Node<T> previous = null;
        // Source bag nodes
        SortedLinkedBag<T> aux = (SortedLinkedBag<T>) source;
        Node<T> s_current = aux.first;

        while(s_current != null){
            // Aprovechando los nodos ya creados en la actual bolsa.
            if(current != null){
                current.elem = s_current.elem;
                current.count = s_current.count;
                // Si existe elemento previo, asignarle current como siguiente
                if(previous != null){
                    previous.next = current;
                }
                // Guardar el nodo actual y pasar al siguiente nodo no utilizado.
                previous = current;
                current = current.next;
            }else{
                current = new Node(s_current, s_current.count, null);
                // Si existe elemento previo, asignarle current como siguiente.
                // Si no, asignar como primer elemento (first)
                if(previous != null){
                    previous.next = current;
                }else{
                    first = current;
                }
                // Guardar el nodo actual.
                previous = current;
                // El próximo current null, para evitar que entre como si estuviese reemplazando un nodo de la bolsa original.
                current = null;
            }
            // Avanzar al siguiente elemento del nodo
            s_current = s_current.next;
        }
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("LinkedBag(");
        for(Node<T> p = first; p != null; p = p.next){
            output.append("(").append(p.elem).append(", ").append(p.count).append(") ");
        }
        return output.append(")").toString();
    }

    @Override
    public Iterator<T> iterator() {
        // TODO
        return new SortedLinkedBagIterator();
    }

    private class SortedLinkedBagIterator implements Iterator<T>{

        Node<T> current;
        int nElms;
        int countElm;

        public SortedLinkedBagIterator(){
            current = first;
            nElms = -1;
            countElm = 0;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if(!hasNext()){
                throw new NoSuchElementException("No hay elemento siguiente");
            }
            T x = current.elem;
            // Nodo no visitado
            // Nodo visitado una vez más
            if(nElms == -1){
                nElms = current.count;
                countElm = 1;
            }else{
                countElm++;
            }
            // Avanzamos al siguiente nodo
            if(countElm == nElms){
                current = current.next;
                nElms = -1;
            }
            return x;
        }
    }
}
