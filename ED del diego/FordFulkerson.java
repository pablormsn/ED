

package dataStructures.graph;

import dataStructures.list.LinkedList;
import dataStructures.list.List;
import dataStructures.set.HashSet;
import dataStructures.set.Set;
import dataStructures.tuple.Tuple2;

public class FordFulkerson<V> {
    private WeightedDiGraph<V,Integer> g; // Initial graph
    private List<WDiEdge<V,Integer>> sol; // List of edges representing maximal flow graph
    private V src; 			  // Source
    private V dst; 		  	  // Sink

    /**
     * Constructors and methods
     */

    public static <V> int maxFlowPath(List<WDiEdge<V,Integer>> path) {
        // TO DO
        int minWeight = path.get(0).getWeight(); // Aquí guardamos el peso de la primera arista del camino
        for(WDiEdge<V,Integer> e: path){ // Recorremos el path
            int currentWeight = e.getWeight();
            if(minWeight > currentWeight){
                minWeight = currentWeight;
            }
        }
        return minWeight;
    }

    public static <V> List<WDiEdge<V,Integer>> updateEdge(V x, V y, Integer p, List<WDiEdge<V,Integer>> edges) {
        // TO DO
        // Debemos buscar sobre toda la lista de aristas
        boolean updated = false;
        for(int i = 0; i < edges.size() && !updated; i++){
            WDiEdge<V, Integer> current = edges.get(i);
            if(current.getSrc().equals(x) && current.getDst().equals(y)){
                if(current.getWeight()+p == 0){
                    current = new WDiEdge<>(current.getSrc(), 0,current.getDst());
                }else{
                    current = new WDiEdge<>(current.getSrc(), current.getWeight()+p, current.getDst());
                }
                updated = true;
            }
        }
        return edges;
    }

    public static <V> List<WDiEdge<V,Integer>> updateEdges(List<WDiEdge<V,Integer>> path, Integer p, List<WDiEdge<V,Integer>> edges) {
        // TO DO
        return null;
    }

    public static <V> List<WDiEdge<V,Integer>> addFlow(V x, V y, Integer p, List<WDiEdge<V,Integer>> sol) {
        // TO DO
        return null;
    }

    public static <V> List<WDiEdge<V,Integer>> addFlows(List<WDiEdge<V,Integer>> path, Integer p, List<WDiEdge<V,Integer>> sol) {
        // TO DO
        return null;
    }

    public FordFulkerson(WeightedDiGraph<V,Integer> g, V src, V dst) {
        // TO DO
    }

    public int maxFlow() {
        // TO DO
        return 0;
    }

    public int maxFlowMinCut(Set<V> set) {
        // TO DO
        return 0;
    }

    /**
     * Provided auxiliary methods
     */
    public List<WDiEdge<V, Integer>> getSolution() {
        return sol;
    }

    /**********************************************************************************
     * A partir de aquí SOLO para estudiantes a tiempo parcial sin evaluación continua.
     * ONLY for part time students.
     * ********************************************************************************/

    public static <V> boolean localEquilibrium(WeightedDiGraph<V,Integer> g, V src, V dst) {
        // TO DO
        return false;
    }
    public static <V,W> Tuple2<List<V>,List<V>> sourcesAndSinks(WeightedDiGraph<V,W> g) {
        // TO DO
        return null;
    }

    public static <V> void unifySourceAndSink(WeightedDiGraph<V,Integer> g, V newSrc, V newDst) {
        // TO DO
    }
}
