/**
 * Student's name:
 * Student's group:
 *
 * Data Structures. Grado en Informática. UMA.
 */

package dataStructures.graph;

import dataStructures.list.*;
import dataStructures.set.HashSet;
import dataStructures.set.Set;

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

        boolean isEul = true;

        for (V v : g.vertices()){
            if(g.degree(v) % 2 != 0){
                isEul = false;
                break;
            }
        }

        return isEul;
    }

    // J.2
    private static <V> void remove(Graph<V> g, V v, V u) {

        g.deleteEdge(v, u);
        if (g.degree(v) == 0){
            g.deleteVertex(v);
        }
        if (g.degree(u) == 0){
            g.deleteVertex(u);
        }

    }

    // J.3
    private static <V> List<V> extractCycle(Graph<V> g, V v0) {
        List<V> cycle = new LinkedList<>();
        cycle.append(v0);
        Set<V> suc = g.successors(v0);
        V v = v0;

        while (! suc.isEmpty()){
            V u = suc.iterator().next();
            cycle.append(u);
            remove(g, v, u);
            v = u;
            suc = g.successors(v);
        }

        return cycle;
    }

    // J.4
    private static <V> void connectCycles(List<V> xs, List<V> ys) {

        if (xs.isEmpty()){
            xs = ys;
        }else {
            int i = 0;
            boolean found = false;
            while (i < xs.size() && !found){
                found = xs.get(i) == ys.get(0);
                i ++;
            }

            for (int j = 1; j < ys.size(); j ++){
                V v = ys.get(j);
                xs.insert(i, v);
                i ++;
            }

        }

    }

    // J.5
    private static <V> V vertexInCommon(Graph<V> g, List<V> cycle) {

        V vic = cycle.get(0);

        if (!g.vertices().isElem(vic)){
            for (V v : cycle){
                if (g.vertices().isElem(v)){
                    vic = v;
                    break;
                }
            }
        }

        return vic;
    }

    // J.6
    private static <V> List<V> eulerianCycle(Graph<V> g) {

        List<V> cycle = null;

        if (isEulerian(g)){
            V v = g.vertices().iterator().next();
            cycle = extractCycle(g, v);
            // extractCycle(g, v);

            while (!g.isEmpty()){
                V vic = vertexInCommon(g, cycle);
                List<V> cycle2 = extractCycle(g, vic);
                // extractCycle(g, vic);
                connectCycles(cycle, cycle2);

            }
        }

        return cycle;
    }
}
