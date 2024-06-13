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
import dataStructures.tuple.Tuple2;

public class TopologicalSortingDic<V> {

    private List<V> topSort;
    private boolean hasCycle;

    public TopologicalSortingDic(DiGraph<V> graph) {
        // Inicializar diccionario
        Dictionary<V, Integer> pendingPredecessors = new HashDictionary<>();
                for(V v : graph.vertices()){
                    pendingPredecessors.insert(v, graph.inDegree(v));
                }

                hasCycle = false;
                while (!pendingPredecessors.isEmpty() && !hasCycle){
                    Queue<V> sources = new LinkedQueue<>(); //F
                    for (Tuple2<V,Integer> t : pendingPredecessors.keysValues()) {
                        if(t._2() == 0){
                            sources.enqueue(t._1());
                        }
                    }

                    topSort = new LinkedList<>();
                    hasCycle = sources.isEmpty();

                    while(!sources.isEmpty()){
                        V source = sources.first();
                        sources.dequeue();
                        pendingPredecessors.delete(source);
                        topSort.append(source);
                        for(V suc : graph.successors(source)){
                            int n = pendingPredecessors.valueOf(suc);
                            pendingPredecessors.insert(suc, n-1);
                        }
                    }
                }
    }

    public boolean hasCycle() {
        return hasCycle;
    }

    public List<V> order() {
        return hasCycle ? null : topSort;
    }
}
