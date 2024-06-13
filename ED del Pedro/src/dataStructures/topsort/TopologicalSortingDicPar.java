/**
 * Estructuras de Datos
 * Práctica 9 - Orden topológico en digrafos (sin clonar el grafo)
 *
 * APELLIDOS :                         NOMBRE:
 */

package dataStructures.topsort;

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.graph.DiGraph;
import dataStructures.list.LinkedList;
import dataStructures.list.List;
import dataStructures.queue.LinkedQueue;
import dataStructures.queue.Queue;
import dataStructures.set.HashSet;
import dataStructures.set.Set;
import dataStructures.tuple.Tuple2;

public class TopologicalSortingDicPar<V> {

    private List<Set<V>> topSort;
    private boolean hasCycle;

    public TopologicalSortingDicPar(DiGraph<V> graph) {
        // Inicializar diccionario
        Dictionary<V, Integer> pendingPredecessors = new HashDictionary<>();
        for (V v : graph.vertices()){
            pendingPredecessors.insert(v, graph.inDegree(v));
        }

        topSort = new LinkedList<>();
        hasCycle = false;

        while (! pendingPredecessors.isEmpty() && ! hasCycle){
            Set<V> sources = new HashSet<>();
            for (Tuple2<V, Integer> t : pendingPredecessors.keysValues()){
                if(t._2() == 0){
                    sources.insert(t._1());
                }
            }

            hasCycle = sources.isEmpty();
            topSort.append(sources);

            for (V source : sources){
                pendingPredecessors.delete(source);
                for (V suc : graph.successors(source)){
                    int n = pendingPredecessors.valueOf(suc);
                    pendingPredecessors.insert(suc, n - 1);
                }
            }
        }

    }

    public boolean hasCycle() {
        return hasCycle;
    }

    public List<Set<V>> order() {
        return hasCycle ? null : topSort;
    }
}
