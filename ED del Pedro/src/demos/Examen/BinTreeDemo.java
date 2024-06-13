package demos.Examen;/*
  Estructuras de Datos
  Grado en Ingeniería Informática, del Software y de Computadores
  Práctica Evaluable - enero 2022
*/

import dataStructures.Examen.BinTree;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

public class BinTreeDemo {

    public static void main(String[] args) {

        /*
           Probar 'isAuthentic' y 'makeAuthentic'.

           Para cada árbol de prueba 'tree' se generan en el directorio 'dot' los ficheros:

              - 'tree.dot' con el árbol de prueba original
              - 'tree-authentic.dot' con el árbol de prueba convertido a auténtico
         */

        Map<String, BinTree<Integer>> trees = mkBinTrees();
        for (Map.Entry<String, BinTree<Integer>> e : trees.entrySet()) {
            String treeName = e.getKey();
            BinTree<Integer> tree = e.getValue();
            saveTreeToDot(treeName, tree);
            System.out.println("Tests for " + treeName);
            if (tree.isAuthentic()) {
                System.out.println("is authentic");
            } else {
                System.out.println("is not authentic");
            }

            System.out.print("making tree authentic... ");
            tree.makeAuthentic();
            saveTreeToDot(treeName + "-authentic", tree);
            if (tree.isAuthentic()) {
                System.out.println("done");
            } else {
                System.out.println("***FAILED!***");
            }
            System.out.println();

        }

        /*
           Probar 'insert'.

           Para cada árbol de prueba 'tree' se genera en el directorio 'dot' el fichero 'tree-99.dot' con
           el 99 insertado en árbol de prueba original.
         */

        trees = mkBinTrees();
        for (Map.Entry<String, BinTree<Integer>> e : trees.entrySet()) {
            String treeName = e.getKey();
            BinTree<Integer> tree = e.getValue();
            tree.insert(99);
            saveTreeToDot(treeName + "-99", tree);
        }
        BinTree<Integer> completo = new BinTree<>();
        for (int i = 1; i < 16; i++) {
            completo.insert(i);
        }
        saveTreeToDot("completo", completo);
    }

    private static <T extends Comparable<? super T>> void saveTreeToDot(String name, BinTree<T> tree) {
        try (PrintWriter pw = new PrintWriter("dot/" + name + ".dot")) {
            pw.println(tree.toDot(name));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, BinTree<Integer>> mkBinTrees() {

        BinTree<Integer> empty = new BinTree<>();

        BinTree<Integer> singleton = new BinTree<>(5);

        BinTree<Integer> tree1 = new BinTree<>(6, new BinTree<>(3), new BinTree<>(8));

        BinTree<Integer> tree2 =
                new BinTree<>(5,
                        new BinTree<>(8),
                        new BinTree<>(4,
                                new BinTree<>(),
                                new BinTree<>(1)));

        BinTree<Integer> tree3 =
                new BinTree<>(6,
                        new BinTree<>(),
                        new BinTree<>(2,
                                new BinTree<>(),
                                new BinTree<>(9,
                                        new BinTree<>(3),
                                        new BinTree<>())));

        BinTree<Integer> tree4 =
                new BinTree<>(9,
                        new BinTree<>(6,
                                new BinTree<>(4,
                                        new BinTree<>(10),
                                        new BinTree<>()),
                                new BinTree<>(7)),
                        new BinTree<>(12,
                                new BinTree<>(),
                                new BinTree<>(23)));

        BinTree<Integer> tree5 =
                new BinTree<>(16,
                        new BinTree<>(8,
                                new BinTree<>(4,
                                        new BinTree<>(6),
                                        new BinTree<>()),
                                new BinTree<>(12)),
                        new BinTree<>(32,
                                new BinTree<>(24,
                                        new BinTree<>(20),
                                        new BinTree<>()),
                                new BinTree<>(64,
                                        new BinTree<>(48),
                                        new BinTree<>(82))));

        Map<String, BinTree<Integer>> trees = new TreeMap<>();
        trees.put("empty", empty);
        trees.put("singleton", singleton);
        trees.put("tree1", tree1);
        trees.put("tree2", tree2);
        trees.put("tree3", tree3);
        trees.put("tree4", tree4);
        trees.put("tree5", tree5);
        return trees;
    }
}
