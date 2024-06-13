/********************************************************************
 * Estructuras de Datos. 2º ETSI Informática. UMA
 * PRACTICA 4.
 * Ejercicio 12 de la tercera relación. Implementar el TAD Bolsa.
 *
 * Alumno: APELLIDOS, NOMBRE
 ********************************************************************/

package dataStructures.bag;

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

    public SortedArrayBag(){
        value = (T[]) new Comparable[INITIAL_CAPACITY];
        count = new int[INITIAL_CAPACITY];
        nextFree = 0;
    }

    // Se llama al método para comprobar que aún queda espacio en la bolsa. En caso contrario el tamaño de la bolsa incrementa
    private void ensureCapacity() {
        if(this.nextFree >= value.length && this.nextFree >= count.length){
            value = Arrays.copyOf(value, value.length*2);
            count = Arrays.copyOf(count, count.length*2);
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

    private void movePositionsForward(int pos){
        for(int i = nextFree; i > pos; i--){
            value[i] = value[i-1];
            count[i] = count[i-1];
        }
        //for(int i = 0; i < (nextFree - pos); i++){
            //value[nextFree - i] = value[nextFree - i - 1];
          //  count[nextFree - i] = count[nextFree - i - 1];
        //}
    }

    @Override
    public void insert(T item) {
        // Como es una bolsa ordenada, itero sobre la lista de elementos hasta encontrar el elemento mayor al que quiero insertar
        ensureCapacity();
        int pos = locate(item);
        if(value[pos] != null && value[pos].compareTo(item) == 0){
            count[pos]++;
        }else{
            movePositionsForward(pos);
            value[pos] = item;
            count[pos] = 1;
            nextFree++;
        }
    }

    @Override
    public int occurrences(T item) {
        int o = 0;
        int pos = locate(item);
        if(value[pos] != null && value[pos].compareTo(item) == 0){
            o = count[pos];
        }
        return o;
    }

    @Override
    public void delete(T item) {
        // Mal
        int pos = locate(item);
        if(pos != nextFree+1 && pos != -1){
            if(value[pos].compareTo(item) == 0){
                for(int i = pos; i < nextFree; i++){
                    value[i] = value[i+1];
                    count[i] = count[i+1];
                }
                nextFree--;
            }
        }
    }

    public void copyOf(Bag<T> source) {
        // you cannot use insert on 'this'
        // ...
        SortedArrayBag<T> aux = (SortedArrayBag<T>) source;
        value = (T[]) new Comparable[aux.value.length];
        count = new int[aux.count.length];
        nextFree = aux.nextFree;
        for(int i = 0; i < aux.value.length; i++){
            this.value[i] = aux.value[i];
            this.count[i] = aux.count[i];
        }
    }

    @Override
    public String toString() {
        String classname = getClass().getName().substring(getClass().getPackage().getName().length()+1);
        String s = classname + "(";
        for(int i = 0; i < value.length; i++){
            s += value[i] + ";" + count[i] + (i < value.length-1 ? "," : "");
        }
        s += ")";
        return s;
    }

    @Override
    public Iterator<T> iterator() {
        return new SortedArrayBagIterator();
    }

    private class SortedArrayBagIterator implements Iterator<T>{

        int position;
        int counter;
        int i;

        public SortedArrayBagIterator(){
            position = 0;
            counter = -1;
            i = 0;
        }

        @Override
        public boolean hasNext() {
            return position < nextFree;
        }

        @Override
        public T next() {
            if(!hasNext()) throw new NoSuchElementException();
            T x = value[position];
            // Ver si hay que avanzar, o seguir recorriendo el contador
            if(counter == -1){
                counter = count[position];
                i = 1;
            }
            // Ir contando
            if(i < counter){
                i++;
            }else{
                counter = -1;
                position++;
            }
            return x;
        }
    }
}
