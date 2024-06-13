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
		dict      = new HashDictionary<V, Color>();
		stack = new StackList<Pair<V,Color>>();
		isBiColored       = true;
		if (graph.numVertices() == 0)
			return; 

		V src = graph.vertices().iterator().next(); // initial vertex
		
		stack.push(new Pair<V,Color>(src,Color.Red));
		
		while (!stack.isEmpty()) {
			Pair<V,Color> vColor = stack.top();
			stack.pop();
         	if(!isDefinedAt(vColor.first(), dict)){
				 dict.insert(vColor.first(), vColor.second());
				 for(V v : graph.successors(vColor.first())){
					 if(!isDefinedAt(v, dict)) {
						 if(vColor.second().equals(Color.Red)){
							 stack.push(new Pair<>(v, Color.Blue));
						 } else{
							 stack.push(new Pair<>(v, Color.Red));
						 }
					 }
				 }
			} else{
				 if(!vColor.second().equals(dict.valueOf(vColor.first()))){
					 isBiColored = false;
					 break;
				 }
			}
		} 
	}

	private boolean isDefinedAt(V fst, Dictionary<V, Color> dict) {
		for(V v : dict.keys()){
			if(v == fst) return true;
		}
		return false;
	}

	public Dictionary<V,Color> biColored() {
		return dict;
	}
	
	public boolean isBicolored() {
		return isBiColored;
	}
	
}
