package demos.Examen;/*
  Estructuras de Datos
  Grado en Ingeniería Informática, del Software y de Computadores
  Práctica Evaluable - enero 2022
*/

import dataStructures.Examen.List;

public class ListDemo {

    public static void main(String[] args) {
        System.out.println("Test rotateLeft:");
        List<Integer> xs = List.mkList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        System.out.println("    original: " + xs);
        xs.rotateLeft(7);
        System.out.println("rotated left 7: " + xs);
/*
        System.out.println("Test rotateLeft empty :");
        List<Integer> xs1 = new List<>();
        System.out.println("    original: " + xs1);
        xs1.rotateLeft(7);
        System.out.println("rotated left on empty list 7: " + xs1);

        System.out.println("Test rotateLeft small list:");
        List<Integer> xs2 = List.mkList(new Integer[]{1, 2, 3, 4, 5});
        System.out.println("    original: " + xs2);
        xs2.rotateLeft(7);
        System.out.println("rotated left 7: " + xs2);

        System.out.println("Test removePlateaus:");
        List<Integer> ys = List.mkList(new Integer[]{1, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4});
        System.out.println("        original: " + ys);
        ys.removePlateaus();
        System.out.println("plateaus removed: " + ys);

        System.out.println("Test removePlateaus:");
        List<Integer> ys2 = List.mkList(new Integer[]{1,1,1,2, 2, 2, 3, 3, 3, 3, 4});
        System.out.println("        original: " + ys2);
        ys2.removePlateaus();
        System.out.println("plateaus removed: " + ys2);
*/
        System.out.println("Test removePlateaus on empty list:");
        List<Integer> ys1 = new List<Integer>();
        System.out.println("        original: " + ys1);
        ys1.removePlateaus();
        System.out.println("plateaus removed: " + ys1);

        System.out.println("Test removePlateaus on singleton list:");
        List<Integer> ys3 = List.mkList(new Integer[]{1});
        System.out.println("        original: " + ys3);
        ys3.removePlateaus();
        System.out.println("plateaus removed: " + ys3);
    }
}
