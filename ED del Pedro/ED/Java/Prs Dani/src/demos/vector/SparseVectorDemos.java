package demos.vector;

import dataStructures.vector.SparseVector;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.StringJoiner;

public class SparseVectorDemos {

    public static void main(String[] args) {
        SparseVector<Integer> sv = new SparseVector<>(3, 0);
        printSparseVector(sv);
        saveTreeToDot("sv-0", sv);
        sv.set(1, 5);
        printSparseVector(sv);
        saveTreeToDot("sv-1-5", sv);
        sv.set(6, 10);
        printSparseVector(sv);
        saveTreeToDot("sv-6-10", sv);
        sv.set(7, 10);
        printSparseVector(sv);
        saveTreeToDot("sv-7-10", sv);

    }

    private static void printSparseVector(SparseVector<Integer> sv) {
        StringJoiner sj = new StringJoiner(",", "(", ")");
        for (Integer x : sv) {
            sj.add(x.toString());
        }
        System.out.println(sj);
    }

    private static <T extends Comparable<? super T>> void saveTreeToDot(String name, SparseVector<T> tree) {
        try (PrintWriter pw = new PrintWriter("dot/" + name + ".dot")) {
            pw.println(tree.toDot(name));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
