package demos.searchTree;/*
  Estructuras de Datos
  Grado en Ingeniería Informática, del Software y de Computadores
  Tema 4. Árboles
  Pablo López
*/

import dataStructures.searchTree.BST;
import dataStructures.searchTree.SearchTree;

public class BSTDemos {

    public static void main(String[] args) {
        SearchTree<Integer, String> t = new BST<>();

        t.insert(5, "five");
        t.insert(3, "three");
        t.insert(2, "two");
        t.insert(1, "one");
        t.insert(10, "ten");
        t.insert(12, "twelve");

        // imprimir recorrido de claves en preorden, inorden o postorden
        System.out.print("claves: ");
        for (Integer i : t.postOrder()) {
            System.out.print(i + " ");
        }
        System.out.println();

        // imprimir recorrido de valores (¿en qué orden salen?)
        System.out.print("valores: ");
        for (String s : t.values()) {
            System.out.print(s + " ");
        }
        System.out.println();

        // imprimir recorrido de claves y valores
        System.out.print("claves y valores: ");
        for (Integer i : t.inOrder()) {
            System.out.printf("(%s, %s) ", i, t.search(i)); // FIXME!!
        }
        System.out.println();

        System.out.println("suma de claves: " + suma(t));
    }

    public static int suma(SearchTree<Integer, String> t) {
        return 0;
    }
}
