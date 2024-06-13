/**
 * Student's name:
 *
 * Student's group:
 */

package dataStructures.graph;

import dataStructures.list.LinkedList;
import dataStructures.list.List;
import dataStructures.set.HashSet;
import dataStructures.set.Set;
import dataStructures.tuple.Tuple2;
import dataStructures.graph.WeightedBreadthFirstTraversal;

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
        if(path.isEmpty()){
            return 0;
        }else{
            int minWeight = path.get(0).getWeight(); // Aquí guardamos el peso de la primera arista del camino
            for(WDiEdge<V,Integer> e: path){ // Recorremos el path
                int currentWeight = e.getWeight();
                if(minWeight > currentWeight){
                    minWeight = currentWeight;
                }
            }
            return minWeight;
        }
    }

    public static <V> List<WDiEdge<V,Integer>> updateEdge(V x, V y, Integer p, List<WDiEdge<V,Integer>> edges) {
        // TO DO
        boolean updated = false;
        for(int i = 0; i < edges.size() && !updated; i++){
            WDiEdge<V, Integer> current = edges.get(i);
            if(current.getSrc().equals(x) && current.getDst().equals(y)){
                if(current.getWeight()+p == 0){
                    edges.remove(i);
                }else{
                    edges.set(i,new WDiEdge<>(current.getSrc(), current.getWeight()+p,current.getDst()));
                }
                updated = true;
            }
        }

        if(!updated){
            edges.append(new WDiEdge<>(x,p,y));
        }

        return edges;
    }

    public static <V> List<WDiEdge<V,Integer>> updateEdges(List<WDiEdge<V,Integer>> path, Integer p, List<WDiEdge<V,Integer>> edges) {
        // TO DO
        for(WDiEdge<V,Integer> e: path){
            edges = updateEdge(e.getSrc(),e.getDst(),p,edges);
        }
        return edges;
    }

    public static <V> List<WDiEdge<V,Integer>> addFlow(V x, V y, Integer p, List<WDiEdge<V,Integer>> sol) {
        // TO DO
        boolean added = false;
        for(int i = 0; i < sol.size() && !added; i++){
            WDiEdge<V,Integer> current = sol.get(i);
            V src = current.getSrc();
            V dst = current.getDst();
            int we = current.getWeight();
            if(src.equals(x) && dst.equals(y)){
                sol.set(i,new WDiEdge<>(x,we + p,y));
                added = true;
            }else if(src.equals(y) && we == p && dst.equals(x)){
                sol.remove(i);
                added = true;
            }else if(src.equals(y) && dst.equals(x) && we < p){
                sol.set(i,new WDiEdge<>(x,p-we,y));
                added = true;
            }else if(src.equals(y) && dst.equals(x) && we > p){
                sol.set(i,new WDiEdge<>(y,we-p,x));
                added = true;
            }
        }

        if(!added){
            sol.append(new WDiEdge<>(x,p,y));
        }

        return sol;
    }

    public static <V> List<WDiEdge<V,Integer>> addFlows(List<WDiEdge<V,Integer>> path, Integer p, List<WDiEdge<V,Integer>> sol) {
        // TO DO

        for(WDiEdge<V,Integer> e: path){
            sol = addFlow(e.getSrc(),e.getDst(),p,sol);
        }
        return sol;

    }
    private static <V> WDiEdge<V,Integer> reverseEdge(WDiEdge<V,Integer> edge){
        return new WDiEdge<>(edge.getDst(),edge.getWeight(), edge.getSrc());
    }

    private static <V> List<WDiEdge<V,Integer>> reversePath(List<WDiEdge<V,Integer>> path){
        List<WDiEdge<V,Integer>> reverseP = new LinkedList<>();
        for(int i = 0; i < path.size(); i++){
            reverseP.append(reverseEdge(path.get(i)));
        }
        return reverseP;
    }

    public FordFulkerson(WeightedDiGraph<V,Integer> g, V src, V dst) {
        // TO DO
        // Ya los tengo inicializado
        sol = new LinkedList<>();
        this.g = g;
        this.src = src;
        this.dst = dst;
        // Ahora necesito un objeto para iterar en profundidad
        WeightedBreadthFirstTraversal<V,Integer> bft = new WeightedBreadthFirstTraversal<>(g,src);
        // Sacamos un path
        List<WDiEdge<V,Integer>> path = bft.pathTo(dst);
        while(path != null){
            // Almaceno el peso mínimo
            int mf = maxFlowPath(path);
            // Creo una lista de edges
            List<WDiEdge<V,Integer>> edges = g.wDiEdges();
            // Utilizo los arcos de path para actualizar edges
            edges = updateEdges(path,-mf,edges);
            // Una vez actualizado edges, repito procedimiento con el camino inverso a path
            List<WDiEdge<V,Integer>> rPath = reversePath(path);
            // Actualizo
            edges = updateEdges(rPath,mf,edges);
            // Después de ello, modifico mi grafo
            g = new WeightedDictionaryDiGraph<>(g.vertices(),edges);
            // Una vez actualizo el grafo
            sol = addFlows(path,mf,sol);
            // Una vez modificado el grafo busco un camino
            bft = new WeightedBreadthFirstTraversal<>(g,src);
            path = bft.pathTo(dst);
        }
    }

    public int maxFlow() {
        // TO DO
        int max = 0;
        for(WDiEdge<V,Integer> e: sol){
            if(e.getSrc().equals(src)){
                max += e.getWeight();
            }
        }
        return max;
    }

    private int sumS(List<WDiEdge<V,Integer>> edges, Set<V> k_src, Set<V> k_dst, int res){
        for(WDiEdge<V,Integer> e: edges){
            if(k_src.isElem(e.getSrc()) && k_dst.isElem(e.getDst())){
                res += e.getWeight();
            }else if(k_src.isElem(e.getDst()) && k_dst.isElem(e.getSrc())){
                res -= e.getWeight();
            }
        }
        return res;
    }

    public int maxFlowMinCut(Set<V> set) {
        // TO DO
        int max = 0;
        Set<V> newSet = new HashSet<>();
        Set<V> vértices = g.vertices();
        // Inicializo el otro conjunto
        for(V vertex: vértices){
            if(!set.isElem(vertex)){
                newSet.insert(vertex);
            }
        }
        // Miro primero donde está el src y dst
        if(set.isElem(src)){
            max = sumS(sol,set,newSet,max);
        }else{
            max = sumS(sol,newSet,set,max);
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
