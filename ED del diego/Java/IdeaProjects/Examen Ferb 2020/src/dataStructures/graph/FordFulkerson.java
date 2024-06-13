package dataStructures.graph;

import dataStructures.list.LinkedList;
import dataStructures.list.List;
import dataStructures.set.HashSet;
import dataStructures.set.Set;
import dataStructures.tuple.Tuple2;

public class FordFulkerson<V> {
    private WeightedDiGraph<V, Integer> g; // Initial graph
    private List<WDiEdge<V, Integer>> sol; // List of edges representing maximal flow graph
    private V src;              // Source
    private V dst;              // Sink

    /**
     * Constructors and methods
     */

    public static <V> int maxFlowPath(List<WDiEdge<V, Integer>> path) {
        int max = path.get(0).getWeight();
        for (WDiEdge<V, Integer> edge : path) {
            if (edge.getWeight() < max) {
                max = edge.getWeight();
            }
        }
        return max;
    }

    public static <V> List<WDiEdge<V, Integer>> updateEdge(V x, V y, Integer p, List<WDiEdge<V, Integer>> edges) {
        List<WDiEdge<V, Integer>> aux = edges;
        boolean encontrado = false;
        for (int i = 0; i < edges.size() && !encontrado; i++) {
            if (edges.get(i).getSrc() == x && edges.get(i).getDst() == y) {
                if(aux.get(i).getWeight() + p > 0){
                    aux.insert(i,new WDiEdge<>(edges.get(i).getSrc(),aux.get(i).getWeight() + p, edges.get(i).getDst()));
                }else{
                    aux.remove(i);
                }
                encontrado = true;
            }
        }

        if(!encontrado){
            aux.append(new WDiEdge<>(x,p,y));
        }

        return aux;
    }

    public static <V> List<WDiEdge<V, Integer>> updateEdges(List<WDiEdge<V, Integer>> path, Integer p, List<WDiEdge<V, Integer>> edges) {
        return null;
    }

    public static <V> List<WDiEdge<V, Integer>> addFlow(V x, V y, Integer p, List<WDiEdge<V, Integer>> sol) {
        // TO DO
        return null;
    }

    public static <V> List<WDiEdge<V, Integer>> addFlows(List<WDiEdge<V, Integer>> path, Integer p, List<WDiEdge<V, Integer>> sol) {
        // TO DO
        return null;
    }

    public FordFulkerson(WeightedDiGraph<V, Integer> g, V src, V dst) {
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

    public static <V> boolean localEquilibrium(WeightedDiGraph<V, Integer> g, V src, V dst) {
        // TO DO
        return false;
    }

    public static <V, W> Tuple2<List<V>, List<V>> sourcesAndSinks(WeightedDiGraph<V, W> g) {
        // TO DO
        return null;
    }

    public static <V> void unifySourceAndSink(WeightedDiGraph<V, Integer> g, V newSrc, V newDst) {
        // TO DO
    }
}