/**
 * @author Blas Ruiz, Data Structures, Grado en Inform‡tica. UMA.
 *
 * Control 2. 13-Febrero-2012
 * Estudio de grafos bipartitos por coloreado con búsqueda en profundidad
 */

package graph;

import dictionary.Dictionary;
import dictionary.HashDictionary;
import stack.Stack;
import stack.StackList;

import java.util.Iterator;


public class BiPartite<V> {
	
	public static enum Color {Red, Blue;
	}

	private static Color nextColor(Color c) {
		return (c == Color.Blue) ?Color.Red:Color.Blue; 
	}
	
	private Stack<Pair<V,Color>> stack; // stack with pair of vertex and color
	private Dictionary<V,Color> dict; // dictionary: Vertices -> Color
	private boolean isBiColored;

	public BiPartite(Graph<V> graph) {
		dict = new HashDictionary<V, Color>();
		stack = new StackList<Pair<V,Color>>();
		isBiColored = true;
		if (graph.numVertices() == 0)
			return; 

		V src = graph.vertices().iterator().next(); // initial vertex
		
		stack.push(new Pair<V,Color>(src,Color.Red));
		
		while (!stack.isEmpty()) {
			Pair<V,Color> vColor = stack.top();
			stack.pop();
         	// completad desde aquí
			if(isElem(vColor.first(),dict)){
				if(!vColor.second().equals(dict.valueOf(vColor.first()))){
					isBiColored = false;
					return;
				}
			}else{ // Como no ha sido visitado, lo añadimos al diccionario y apilamos sus sucesores a la pila
				// Insertamos el par (vértice,color)
				dict.insert(vColor.first(), vColor.second());
				// Iteramos sobre los sucesores del vértice
				Iterator<V> vertex = graph.successors(vColor.first()).iterator();
				while(vertex.hasNext()){ // Los apilamos en la pila con el color que les corresponde
					stack.push(new Pair<>(vertex.next(),nextColor(vColor.second())));
				}
			}
		} 
	}	

	private <V> boolean isElem(V vertex, Dictionary<V,Color> d){
		boolean found = false;
		Iterator<V> keys = d.iterator();
		while(keys.hasNext() && !found){
			if(keys.next().equals(vertex)){
				found = true;
			}
		}
		return found;
	}

	public Dictionary<V,Color> biColored() {
		return dict;
	}
	
	public boolean isBicolored() {
		return isBiColored;
	}
	
}
