/**
 * Student's name:
 * Student's group:
 *
 * Data Structures. Grado en Informática. UMA.
 */

package dataStructures.graph;

import dataStructures.list.*;

public class EulerianCycle<V> {
    private List<V> eCycle;

    @SuppressWarnings("unchecked")
    public EulerianCycle(Graph<V> g) {
        Graph<V> graph = (Graph<V>) g.clone();
        eCycle = eulerianCycle(graph);
    }

    public boolean isEulerian() {
        return eCycle != null;
    }

    public List<V> eulerianCycle() {
        return eCycle;
    }

    // J.1
    private static <V> boolean isEulerian(Graph<V> g) {
        // TO DO
        if(g.vertices().isEmpty()){
            return true;
        }else{
            boolean ok = true;
            for(V vertex: g.vertices()){
                if(g.degree(vertex) % 2 != 0){
                    ok = false;
                    break;
                }
            }
            return ok;
        }
    }

    // J.2
    private static <V> void remove(Graph<V> g, V v, V u) {
        // TO DO
        // Elimino la arista
        g.deleteEdge(v,u);
        // Ahora miro esos dos vértices
        if(g.degree(v) == 0){
            g.deleteVertex(v);
        }
        if(g.degree(u) == 0){
            g.deleteVertex(u);
        }
    }

    // J.3
    private static <V> List<V> extractCycle(Graph<V> g, V v0) {
        // TO DO
        List<V> cycle = new LinkedList<>();
        // Inicialmente contiene el vértice v0
        cycle.append(v0);
        // Ahora necesitamos sus sucesores
        V v = v0;
        while(!g.successors(v).isEmpty()){
            // Cojo el primer sucesor
            V u = g.successors(v).iterator().next();
            // Lo añadimos al ciclo
            cycle.append(u);
            // Eliminamos la arista (v,u)
            remove(g,v,u);
            // Una vez eliminada repetimos proceso con el sucesor escogido
            v = u;
        }
        return cycle;
    }

    // J.4
    private static <V> void connectCycles(List<V> xs, List<V> ys) {
        // TO DO
        if(xs.isEmpty()){
            xs = ys;
        }else{
            boolean end = false;
            for(int i = 0; i < xs.size() && !end; i++){
                if(xs.get(i).equals(ys.get(0))){
                    int pos = i;
                    for(V elem: ys){
                        xs.insert(pos,elem);
                        pos++;
                    }
                    xs.remove(pos);
                    end = true;
                }
            }
        }
    }

    // J.5
    private static <V> V vertexInCommon(Graph<V> g, List<V> cycle) {
        // TO DO
        V common = null;
        boolean found = false;
        for(int i = 0; i < cycle.size() && !found; i++){
            if(g.vertices().isElem(cycle.get(i))){
                common = cycle.get(i);
                found = true;
            }
        }
        return common;
    }

    // J.6
    private static <V> List<V> eulerianCycle(Graph<V> g) {
        // TO DO
        if(!isEulerian(g)){
            return null;
        }else{
            List<V> eulerCycle = new LinkedList<>();
            eulerCycle.append(g.vertices().iterator().next());
            // Repetimos nuestro algoritmo mientras el grafo no sea vacío
            while(!g.isEmpty()){
                connectCycles(eulerCycle,extractCycle(g,vertexInCommon(g,eulerCycle)));
            }
            return eulerCycle;
        }
    }
}
