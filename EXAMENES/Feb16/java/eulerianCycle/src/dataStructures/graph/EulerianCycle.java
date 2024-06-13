/**
 * Student's name:
 * Student's group:
 *
 * Data Structures. Grado en Informática. UMA.
 */

package dataStructures.graph;

import dataStructures.list.*;
import dataStructures.set.Set;

public class EulerianCycle<V> {
    private List<V> eCycle;

    @SuppressWarnings("unchecked")
    public EulerianCycle(Graph<V> g) {
        Graph<V> graph = (Graph<V>) g.clone();
        eCycle = eulerianCycle(graph);
    }

    public boolean isEulerian() {return eCycle != null;}

    public List<V> eulerianCycle() {
        return eCycle;
    }

    // J.1
    private static <V> boolean isEulerian(Graph<V> g) {
        // TO DO
        Set<V> vertices = g.vertices();
        boolean ok = true;
        if(vertices.size() < 2){
            System.out.println("Este grafo no es conexo");
        }else{
            for(V vert : vertices){
                if(g.degree(vert)%2!=0){
                    ok=false;
                    break;
                }
            }
        }
        return ok;
    }

    // J.2
    private static <V> void remove(Graph<V> g, V v, V u) {
        // TO DO
        g.deleteEdge(v,u);
        if(g.degree(v)==0){
            g.deleteVertex(v);
        }
        if(g.degree(u)==0) {
            g.deleteVertex(u);
        }
    }

    // J.3
    private static <V> List<V> extractCycle(Graph<V> g, V v0) {
        // TO DO
        List<V> path = new LinkedList<>();
        path.append(v0);
        V v = v0;
        while(!g.successors(v).isEmpty()){
            V u = g.successors(v).iterator().next();
            path.append(u);
            remove(g,v,u);
            v=u;
        }
        return path;
    }

    // J.4
    private static <V> void connectCycles(List<V> xs, List<V> ys) {
    		// TO DO
        if(xs.isEmpty()){
            xs=ys;
        }else{
            boolean done = false;
            for(int i=0; i<xs.size() && !done; i++){
                if(xs.get(i).equals(ys.get(0))){
                    int pos=i;
                    for (V elem : ys){
                        xs.insert(pos, elem);
                        pos++;
                    }
                    xs.remove(pos);
                    done = true;
                }
            }

        }


    }

    // J.5
    private static <V> V vertexInCommon(Graph<V> g, List<V> cycle) {
    		// TO DO
        Set<V> vertices = g.vertices();
        V comun = null;
        boolean done = false;
        for(int i=0; i<cycle.size() && !done; i++){
            if(vertices.isElem(cycle.get(i))){
                comun=cycle.get(i);
                done=true;
            }
        }
    	return comun;
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
