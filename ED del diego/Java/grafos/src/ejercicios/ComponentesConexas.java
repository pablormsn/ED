/*
  Estructuras de Datos
  Grado en Ingeniería Informática, del Software y de Computadores
  Tema 6. Grafos
  Pablo López
*/

package ejercicios;

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.graph.DepthFirstTraversal;
import dataStructures.graph.Graph;
import dataStructures.set.HashSet;
import dataStructures.set.Set;

public class ComponentesConexas<V> {

    private Set<Set<V>> caminos;
    private Dictionary<V, Integer> aristas;

    public ComponentesConexas(Graph<V> g) {
        caminos = new HashSet<>();
        aristas = new HashDictionary<>();

        Set<V> sinVisitar = new HashSet<>();
        for(V v: g.vertices()) sinVisitar.insert(v);

        for(int c = 0; !sinVisitar.isEmpty(); c++){
            V src = sinVisitar.iterator().next();
            aristas.insert(src,c);

            Set<V> component = new HashSet<>();
            for(V v: new DepthFirstTraversal<>(g,src).vertices()){
                component.insert(v);
                aristas.insert(v,c);
            }

            caminos.insert(component);

            for(V v : component){
                sinVisitar.delete(v);
            }
        }

    }

    public Set<Set<V>> componentes() {
        return caminos;
    }

    public boolean conectados(V x, V y) {
        return aristas.valueOf(x).equals(aristas.valueOf(y));
    }
}
