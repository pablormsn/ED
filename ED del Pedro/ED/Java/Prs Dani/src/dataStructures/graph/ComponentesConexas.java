/*
  Estructuras de Datos
  Grado en Ingeniería Informática, del Software y de Computadores
  Tema 6. Grafos
  Pablo López
*/

package dataStructures.graph;

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.graph.DepthFirstTraversal;
import dataStructures.graph.Graph;
import dataStructures.set.HashSet;
import dataStructures.set.Set;

public class ComponentesConexas<V> {

    private Set<Set<V>> componentes;
    private Dictionary<V, Integer> enComponente;

    public ComponentesConexas(Graph<V> g) {
        componentes = new HashSet<>();
        enComponente = new HashDictionary<>();

        Set<V> porVisitar = new HashSet<>();
        for (V v : g.vertices()){
            porVisitar.insert(v);
        }

        for (int c = 0; ! porVisitar.isEmpty(); c++) {
            V orig = porVisitar.iterator().next();
            enComponente.insert(orig, c);

            Set<V> componente = new HashSet<>();
            for (V v : new DepthFirstTraversal<>(g, orig).vertices()){
                componente.insert(v);
                enComponente.insert(v, c);
            }

            componentes.insert(componente);

            for (V v : componente) {
                porVisitar.delete(v);
            }
        }
    }

    public Set<Set<V>> componentes() {
        return componentes;
    }

    public boolean conectados(V x, V y) {
        return enComponente.valueOf(x).equals(enComponente.valueOf(y));
    }
}
