/**
 * Student's name:
 *
 * Student's group:
 */

package dataStructures.graph;

import dataStructures.list.LinkedList;
import dataStructures.list.List;
import dataStructures.set.Set;
import dataStructures.tuple.Tuple2;

import java.lang.reflect.WildcardType;

public class FordFulkerson<V> {
    private WeightedDiGraph<V,Integer> g; // Initial graph
    private List<WDiEdge<V,Integer>> sol; // List of edges representing maximal flow graph
    private V src; 			  // Source
    private V dst; 		  	  // Sink

    /**
     * Constructors and methods
     */

    public static <V> int maxFlowPath(List<WDiEdge<V,Integer>> path) {
        int min = path.get(0).getWeight();

        for (WDiEdge<V,Integer> wde : path){
            int w = wde.getWeight();
            if(w < min){
                min = w;
            }
        }

        return min;
    }

    public static <V> List<WDiEdge<V,Integer>> updateEdge(V x, V y, Integer p, List<WDiEdge<V,Integer>> edges) {
        boolean found = false;
        int i = 0;
        while (! found && i < edges.size()){
            WDiEdge<V,Integer> edge = edges.get(i);
            if(edge.getSrc().equals(x) && edge.getDst().equals(y)){
                if(edge.getWeight() + p == 0){
                    edges.remove(i);
                }else {
                    edges.set(i, new WDiEdge<>(x, p + edge.getWeight(), y));
                }
                found = true;
            }
            i ++;
        }

        if(! found){
            edges.append(new WDiEdge<>(x, p, y));
        }

        return edges;
    }

    public static <V> List<WDiEdge<V,Integer>> updateEdges(List<WDiEdge<V,Integer>> path, Integer p, List<WDiEdge<V,Integer>> edges) {

        if(path != null){
            for (WDiEdge<V, Integer> wde : path){
                edges = updateEdge(wde.getSrc(), wde.getDst(), p, edges);
            }
        }
        return edges;
    }

    public static <V> List<WDiEdge<V,Integer>> addFlow(V x, V y, Integer p, List<WDiEdge<V,Integer>> sol) {
        boolean found = false;
        int i = 0;

        while (! found && i < sol.size()){
            WDiEdge<V,Integer> edge = sol.get(i);

            if(edge.getSrc().equals(x) && edge.getDst().equals(y)){
                sol.set(i,new WDiEdge<>(x, p + edge.getWeight(), y));
                found = true;
            }else if(edge.getSrc().equals(y) && edge.getDst().equals(x)){
                if(edge.getWeight().equals(p)){
                    sol.remove(i);
                }else if(edge.getWeight() < p){
                    sol.set(i,new WDiEdge<>(x, p - edge.getWeight(), y));
                }else {
                    sol.set(i,new WDiEdge<>(x, edge.getWeight() - p, y));
                }
                found = true;
            }
            i ++;
        }

        if (! found){
            sol.append(new WDiEdge<>(x, p, y));
        }

        return sol;
    }

    public static <V> List<WDiEdge<V,Integer>> addFlows(List<WDiEdge<V,Integer>> path, Integer p, List<WDiEdge<V,Integer>> sol) {

        if(path != null){
            for (WDiEdge<V, Integer> wde : path){
                sol = addFlow(wde.getSrc(), wde.getDst(), p, sol);
            }
        }
        return sol;
    }

    public FordFulkerson(WeightedDiGraph<V,Integer> g, V src, V dst) {
        List<WDiEdge<V, Integer>> sol = new LinkedList<>();
        WeightedDiGraph<V, Integer> wdg = g;
        WTraversal<V, Integer> paths = new WeightedBreadthFirstTraversal<>(wdg, src);
        List<WDiEdge<V, Integer>> path = paths.pathTo(dst);

        while (path != null){
            int mf = maxFlowPath(path);
            List<WDiEdge<V, Integer>> edges = wdg.wDiEdges();
            edges = updateEdges(path, (- mf), edges);
            WTraversal<V, Integer> revPaths = new WeightedBreadthFirstTraversal<>(wdg, dst);
            List<WDiEdge<V, Integer>> revPath = revPaths.pathTo(src);
            edges = updateEdges(revPath, mf, edges);
            wdg = new WeightedDictionaryDiGraph<>(wdg.vertices(), edges);
            sol = addFlows(path, mf, sol);
            paths = new WeightedBreadthFirstTraversal<>(wdg, src);
            path = paths.pathTo(dst);
        }

        this.g = g;
        this.src = src;
        this.dst = dst;
        this.sol = sol;

    }

    public int maxFlow() {
        int max = 0;

        for (WDiEdge<V, Integer> wde : sol){
            if(wde.getSrc().equals(src)){
                max += wde.getWeight();
            }
        }
        return max;
    }

    public int maxFlowMinCut(Set<V> set) {
        int sum1 = 0;
        int sum2 = 0;
        int max = 0;
        List<WDiEdge<V, Integer>> edges = g.wDiEdges();

        for (WDiEdge<V, Integer> edge : edges){
            if(set.isElem(edge.getSrc()) && ! set.isElem(edge.getDst())){
                sum1 += edge.getWeight();
            }else if(set.isElem(edge.getDst()) && ! set.isElem(edge.getSrc())){
                sum2 += edge.getWeight();
            }
        }
        if(set.isElem(src)){
            max = sum1 - sum2;
        }else {
            max = sum2 - sum1;
        }
        return max;
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
