/*
  Estructuras de Datos
  Grado en Ingeniería Informática, del Software y de Computadores
  Tema 3. TAD Lineales
  Pablo López
*/

import dataStructures.set.Set;
import dataStructures.set.SortedLinkedSet;

public class SetClient {

    public static void main(String[] args) {
        Set<Integer> s = new SortedLinkedSet<>();

        int[] xs = {35, 7, 1, 5, 7, 2, 52, 18, 0, 5};
        for (int x : xs) {  // iterador
            s.insert(x);
        }

        System.out.println(s);

        s.delete(7);
        s.delete(0);
        s.delete(52);
        s.delete(5);
        s.delete(100);
        s.delete(-1);
        s.delete(13);

        System.out.println(s);

        // uso del iterador

        // suma de elementos de un conjunto

        int sum = 0;
        for (int x : s) {
            sum += x;
        }
        System.out.println(sum);

        // máximo de un conjunto

        if (s.size() != 0) {
            int maximo = s.iterator().next();  // solo funciona si no está vacío
            for (int i : s) {
                if (maximo < i) {
                    maximo = i;
                }
            }
            System.out.println(maximo);
        }
    }
}
