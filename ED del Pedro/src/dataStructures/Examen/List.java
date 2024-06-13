/*
  Estructuras de Datos
  2.º A Computadores, 2.º D Informática, Software, Matemáticas + Informática

  Práctica Evaluable - enero 2022

  Apellidos, Nombre: Vargas Quero, Daniel J.

  Titulación, Grupo: 2º Computadores A

  Número de PC: PC223
*/

package dataStructures.Examen;

// Solo tienes que completar los métodos 'rotateLeft' y 'removePlateaus' que aparecen al final

// NO EDITAR EL CÓDIGO DE ABAJO

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class List<T> implements Iterable<T> {

    private static class Node<E> {
        final E elem;
        Node<E> next;

        Node(E e, Node<E> nextNode) {
            elem = e;
            next = nextNode;
        }
    }

    private Node<T> first;

    public List() {
        first = null;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void append(T x) {
        Node<T> nuevo = new Node<>(x, null);
        if (isEmpty()) {
            first = nuevo;
        } else {
            Node<T> current = first;
            while (current.next != null) {
                current = current.next;
            }
            current.next = nuevo;
        }
    }

    public static <E> List<E> mkList(E[] values) {
        List<E> xs = new List<>();
        for (E v : values) {
            xs.append(v);
        }
        return xs;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", "List(", ")");
        for (Node<T> current = first; current != null; current = current.next) {
            sj.add(current.elem.toString());
        }
        return sj.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<T> {
        private Node<T> current;

        ListIterator() {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("List: iterator exhausted");
            }
            T result = current.elem;
            current = current.next;
            return result;
        }
    }

// NO EDITAR EL CÓDIGO DE ARRIBA

    /*public void rotateLeft(int n) {
        if(n < 0){
            throw new RuntimeException("Valor no válido");
        }
        if(n != 0){
            this.append(first.elem);
            first = first.next;
            rotateLeft(n - 1);
        }
    }*/

    public void rotateLeft(int n) {
        if(n < 0){
            throw new RuntimeException("Valor no válido");
        }
        if(n != 0 && first != null){
            Node<T> current = first;
            while(current.next != null){
                current = current.next;
            }
            for(int i = 0; i < n; i ++){
                current.next = first;
                current = current.next;
                first = first.next;
            }
        }
    }

    public void removePlateaus() {
        if(first != null){
            Node<T> previous = first;
            Node<T> current = first.next;
            while(current != null){
                if(previous.elem.equals(current.elem)){
                    current = current.next;
                    previous.next = current;
                }
                previous = current;
                current = current.next;
            }
        }
    }
}
