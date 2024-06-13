/**
 * @author Pepe Gallardo, Data Structures, Grado en Inform�tica. UMA.
 *
 * Connected components for an undirect graph
 */

package dataStructures.graph;

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.set.HashSet;
import dataStructures.set.Set;

public class ConnectedComponents<V> {
	Set<Set<V>> componentes;
	Dictionary<V, Integer> enComponente;

	public ConnectedComponents(Graph<V> g) {
		componentes = new HashSet<>();
		enComponente = new HashDictionary<>();

		Set<V> porVisitar = new HashSet<>();
		for (V v : g.vertices()){
			porVisitar.insert(v);
		}

		while (! porVisitar.isEmpty()){
			int nComp = 0;
			V orig = porVisitar.iterator().next();
			Set<V> componente = new HashSet<>();
			for (V v : new DepthFirstTraversal<>(g, orig).vertices()){
				componente.insert(v);
				enComponente.insert(v, nComp);
				porVisitar.delete(v);
			}
			componentes.insert(componente);
			nComp ++;
		}
	}
	
	public Set<Set<V>> components() {
		return null;
	}
	
	public boolean areConnected(V v, V w) {
		return false;
	}
	

}
