/**----------------------------------------------
 * -- Estructuras de Datos.  2018/19
 * -- 2º Curso del Grado en Ingeniería [Informática | del Software | de Computadores].
 * -- Escuela Técnica Superior de Ingeniería en Informática. UMA
 * --
 * -- Examen 4 de febrero de 2019
 * --
 * -- ALUMNO/NAME:
 * -- GRADO/STUDIES:
 * -- NÚM. MÁQUINA/MACHINE NUMBER:
 * --
 * ----------------------------------------------
 */

import dataStructures.graph.WeightedGraph;
import dataStructures.graph.WeightedGraph.WeightedEdge;

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.priorityQueue.PriorityQueue;
import dataStructures.priorityQueue.LinkedPriorityQueue;
import dataStructures.set.Set;
import dataStructures.set.HashSet;
import dataStructures.tuple.Tuple2; // Me sobraría

public class Kruskal {

	private static <V> V getRep(V vertex, Dictionary<V,V> dict){
		V v = vertex;
		while(v != dict.valueOf(v)){
			v = dict.valueOf(v);
		}
		return v;
	}

	private static <V,W> Tuple2<Tuple2<Boolean,V>,V> areConnected(WeightedEdge<V,W> edge, Dictionary<V,V> dict){
		V repS = getRep(edge.source(),dict);
		V repD = getRep(edge.destination(),dict);
		return new Tuple2<>(new Tuple2<>(repS.equals(repD),repS), edge.destination());
	}

	public static <V,W> Set<WeightedEdge<V,W>> kruskal(WeightedGraph<V,W> g) {
		// Creamos el diccionario, la cola de prioridad y nuestro conjunto solución
		Dictionary<V,V> dict = new HashDictionary<>();
		PriorityQueue<WeightedEdge<V,W>> e = new LinkedPriorityQueue<>();
		Set<WeightedEdge<V,W>> sol = new HashSet<>();
		// Ahora inicializo ambos
		// Diccionario
		for(V vertex: g.vertices()){
			dict.insert(vertex,vertex);
		}
		// Cola de prioridad
		for(WeightedEdge<V,W> edge: g.edges()){
			e.enqueue(edge);
		}
		// Tras inicializarlos realizamos el algoritmo
		while(!e.isEmpty()){
			// Sacamos la primera arista
			WeightedEdge<V,W> edge = e.first();
			// Primero miramos que los vértices extraídos de la cola estén conectados
			Tuple2<Tuple2<Boolean,V>,V> t = areConnected(edge,dict);
			if(!t._1()._1()){
				// Actualizo el diccionario
				dict.insert(t._1()._2(),t._2());
				// Añado la arista al bosque de recubrimiento
				sol.insert(edge);
			}
			e.dequeue();
		}
		return sol;
	}

	// Sólo para evaluación continua / only for part time students
	public static <V,W> Set<Set<WeightedEdge<V,W>>> kruskals(WeightedGraph<V,W> g) {

		// COMPLETAR
		
		return null;
	}
}
