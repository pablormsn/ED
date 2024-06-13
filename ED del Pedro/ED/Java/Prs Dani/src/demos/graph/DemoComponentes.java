package demos.graph;/*
  Estructuras de Datos
  Grado en Ingeniería Informática, del Software y de Computadores
  Tema 6. Grafos
  Pablo López
*/

import dataStructures.graph.ComponentesConexas;
import dataStructures.graph.DictionaryGraph;
import dataStructures.graph.Graph;
import dataStructures.set.Set;


public class DemoComponentes {

    public static void main(String[] args) {

        System.out.println("Componentes de un grafo conexo:");
        Graph<Character> gConexo = mkgConexo();
        ComponentesConexas<Character> cs = new ComponentesConexas<>(gConexo);
        for (Set<Character> comp : cs.componentes()) {
            System.out.println(comp);
        }

        System.out.println("Componentes de un grafo no conexo:");
        Graph<Character> gNoConexo = mkgNoConexo();
        cs = new ComponentesConexas<>(gNoConexo);
        for (Set<Character> comp : cs.componentes()) {
            System.out.println(comp);
        }
        System.out.println("A y B conectados: " + cs.conectados('A', 'B'));
        System.out.println("A y C conectados: " + cs.conectados('A', 'C'));
    }

    private static Graph<Character> mkgConexo() {
        Graph<Character> gConexo = new DictionaryGraph<>();
        for (char c = 'A'; c <= 'H'; c++) {
            gConexo.addVertex(c);
        }
        gConexo.addEdge('A', 'C');
        gConexo.addEdge('A', 'D');
        gConexo.addEdge('A', 'H');
        gConexo.addEdge('B', 'H');
        gConexo.addEdge('C', 'A');
        gConexo.addEdge('C', 'G');
        gConexo.addEdge('D', 'A');
        gConexo.addEdge('D', 'E');
        gConexo.addEdge('D', 'F');
        gConexo.addEdge('E', 'D');
        gConexo.addEdge('E', 'F');
        gConexo.addEdge('E', 'G');
        gConexo.addEdge('E', 'H');
        gConexo.addEdge('F', 'D');
        gConexo.addEdge('F', 'D');
        gConexo.addEdge('G', 'C');
        gConexo.addEdge('G', 'E');
        gConexo.addEdge('H', 'A');
        gConexo.addEdge('H', 'B');
        gConexo.addEdge('H', 'E');
        return gConexo;
    }

    private static Graph<Character> mkgNoConexo() {
        Graph<Character> gNoConexo = new DictionaryGraph<>();
        for (char c = 'A'; c <= 'H'; c++) {
            gNoConexo.addVertex(c);
        }

        gNoConexo.addEdge('A', 'B');
        gNoConexo.addEdge('A', 'D');
        gNoConexo.addEdge('A', 'F');
        gNoConexo.addEdge('B', 'A');
        gNoConexo.addEdge('B', 'F');
        gNoConexo.addEdge('C', 'G');
        gNoConexo.addEdge('D', 'A');
        gNoConexo.addEdge('D', 'F');
        gNoConexo.addEdge('E', 'G');
        gNoConexo.addEdge('E', 'H');
        gNoConexo.addEdge('F', 'A');
        gNoConexo.addEdge('F', 'B');
        gNoConexo.addEdge('F', 'D');
        gNoConexo.addEdge('G', 'C');
        gNoConexo.addEdge('G', 'E');
        gNoConexo.addEdge('G', 'H');
        gNoConexo.addEdge('H', 'E');
        gNoConexo.addEdge('H', 'G');
        return gNoConexo;
    }
}
