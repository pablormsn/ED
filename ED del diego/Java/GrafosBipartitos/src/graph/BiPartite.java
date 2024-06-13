/**
 * @author Blas Ruiz, Data Structures, Grado en Inform‡tica. UMA.
 *
 * Control 2. 13-febrero-2012
 * Estudio de grafos bipartitos por coloreado con búsqueda en profundidad
 */

package graph;

import dictionary.Dictionary;
import dictionary.HashDictionary;
import stack.Stack;
import stack.StackList;


public class BiPartite<V> {
	
	public static enum Color {Red, Blue;
	}

	private static Color nextColor(Color c) {
		return (c == Color.Blue) ?Color.Red:Color.Blue; 
	}
	
	private Stack<Pair<V,Color>> stack; // stack with a pair of vertex and color
	private Dictionary<V,Color> dict; // dictionary: Vertices -> Color
	private boolean isBiColored;

	public BiPartite(Graph<V> graph) {
		dict      = new HashDictionary<V, Color>();
		stack = new StackList<Pair<V,Color>>();
		isBiColored       = true;
		if (graph.numVertices() == 0)
			return; 

		V src = graph.vertices().iterator().next(); // initial vertex
		
		stack.push(new Pair<V,Color>(src,Color.Red));
		
		while (!stack.isEmpty() && isBiColored) {
			// Sacamos el elemento de la cima de nuestra pila
			Pair<V,Color> vColor = stack.top();
			stack.pop();
         	// Comprobamos que el vértice no haya sido visitado
			Color cd = dict.valueOf(vColor.first());
			if(cd == null){ // Si nos devuelve null es que no ha sido visitado
				// Lo añadimos a nuestro diccionario
				dict.insert(vColor.first(), vColor.second());
				// Ahora todos los sucesores no visitados se añaden a la pila
				for(V vertex: graph.successors(vColor.first())){
					// Recorremos todos los sucesores y comprobamos si han sido visitados o no
					if(dict.valueOf(vertex) == null){
						stack.push(new Pair<>(vertex, nextColor(vColor.second())));
					}
				}
			}else if(cd != vColor.second()){
				isBiColored = false;
			}
		} 
	}	
	
	public Dictionary<V,Color> biColored() {
		return dict;
	}
	
	public boolean isBicolored() {
		return isBiColored;
	}
	
}
