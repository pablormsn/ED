/********************************************************************
 * Estructuras de Datos. 2º ETSI Informática. UMA
 * PRACTICA 4.
 * Ejercicio 12 de la tercera relación. Implementar el TAD Bolsa.
 *
 * Alumno: APELLIDOS, NOMBRE
 ********************************************************************/

package dataStructures.bag;

import org.w3c.dom.Node;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedArrayBag<T extends Comparable<? super T>> implements Bag<T> {

    private final static int INITIAL_CAPACITY = 1;

    // The bag is represented by two arrays ("value" and "count") and
    // a cursor ("nextFree").
    //
    // The bag is stored in the first "nextFree-1" positions of
    // arrays "value" and "count". Cursor "nextFree" refers to the
    // first position available in both arrays (if any).
    //
    // The values are stored sorted in the array "value", the counter
    // corresponding to "value[i]" is stored in "counter[i]"; thus
    // the bag { ('a', 6), ('d', 2), ('t', 7)} is represented by:
    //
    // value = { 'a', 'd', 't' }
    // count = { 6 , 2 , 7 }
    // nextFree = 3
    //
    // The remaining positions in arrays "value" and "count" are
    // undefined (most likely null and zero, respectively).

    private T[] value; // keep this array sorted
    private int[] count; // keep only positive counters
    private int nextFree;

    public SortedArrayBag(int n) {
        value = (T[]) new Object[n];
        count = (int[]) new int[n];
        nextFree = 0;
    }

    private void ensureCapacity() {
        if (nextFree >= value.length) {
            value = Arrays.copyOf(value, 2*value.length);
            count = Arrays.copyOf(count, 2*count.length);
        }
    }

    @Override
    public boolean isEmpty() {
        return nextFree == 0;
    }

    // search loop:
    // if "item" is stored in the array "value", returns its index;
    // otherwise returns the index where "item" would be inserted

    private int locate(T item) {
        int lower = 0;
        int upper = nextFree - 1;
        int mid = 0;
        boolean found = false;

        // binary search
        while (lower <= upper && !found) {
            mid = lower + ((upper - lower) / 2); // == (lower + upper) / 2;
            found = value[mid].equals(item);
            if (!found) {
                if (value[mid].compareTo(item) > 0) {
                    upper = mid - 1;
                } else {
                    lower = mid + 1;
                }
            }
        }

        if (found)
            return mid; // the index where "item" is stored
        else
            return lower; // the index where "item" would be inserted
    }

    @Override
    public void insert(T item) {
        ensureCapacity();
        int pos = locate(item);
        if(value[pos] == item){
            count[pos] ++;
        }else if(pos == nextFree){
            value[pos] = item;
            count[pos] = 1;
            nextFree ++;
        }else {
            for (int i = nextFree; i > pos; i --) {
                value[i] = value[i - 1];
                count[i] = count[i - 1];
            }
            value[pos] = item;
            count[pos] = 1;
            nextFree ++;
        }
    }

    @Override
    public int occurrences(T item) {
        int pos = locate(item);
        if(pos != nextFree){
            return count[pos];
        }else{
            return 0;
        }
    }

    @Override
    public void delete(T item) {
        int pos = locate(item);
        if(pos < nextFree){
            if(count[pos] > 1){
                count[pos] --;
            }else{
                for(int i = pos; i < nextFree - 1; i ++){
                    value[i] = value[i + 1];
                    count[i] = count[i + 1];
                }
                nextFree --;
            }
        }
    }

    @Override
    public void copyOf(Bag<T> source) {
        // TODO
        // you cannot use insert on 'this'
    }

    @Override
    public String toString() {
        String className = getClass().getName().substring(getClass().getPackage().getName().length() + 1);
        String s = className + "(";
        for(int i = 0; i < value.length; i ++){
            s += value[i] + ";" + count[i] + (i< value.length-1 ? "," : "");
        }
        s += ")";
        return s;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayBagIterator();
    }

    private class ArrayBagIterator implements Iterator<T>{
        int current;

        public ArrayBagIterator(){
            current = 0;
        }

        public boolean hasNext(){
            return current != nextFree;
        }

        public T next(){
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            T x = value[current];
            current ++;
            return x;
        }
    }

}
