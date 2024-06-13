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
import dataStructures.tuple.Tuple2;

public class Kruskal {

	private static <V> V getRepresentante(V vertex, Dictionary<V,V> dict){
		V v = vertex;
		while(v!=dict.valueOf(v)){
			v= dict.valueOf(v);
		}
		return v;
	}

	private static <V,W> Tuple2<Tuple2<Boolean,V>,V> areConnected(WeightedEdge<V,W> edge, Dictionary<V,V> dict){
		V repO = getRepresentante(edge.source(), dict);
		V repD = getRepresentante(edge.destination(), dict);
		return new Tuple2<>(new Tuple2<>(repO.equals(repD),repO), edge.destination());
	}
	public static <V,W> Set<WeightedEdge<V,W>> kruskal(WeightedGraph<V,W> g) {

		// COMPLETAR
		Dictionary<V,V> dict = new HashDictionary<>();
		PriorityQueue<WeightedEdge<V,W>> pq = new LinkedPriorityQueue<>();
		Set<WeightedEdge<V,W>> sol = new HashSet<>();

		for(V vertex: g.vertices()){
			dict.insert(vertex,vertex);
		}
		for(WeightedEdge<V,W> edge: g.edges()){
			pq.enqueue(edge);
		}
		while (!pq.isEmpty()){
			WeightedEdge<V,W> edge = pq.first();
			Tuple2<Tuple2<Boolean, V>,V> tupla = areConnected(edge,dict);
			if(!tupla._1()._1()){//if boolean = false
				dict.insert(tupla._1()._2(),tupla._2());
				sol.insert(edge);
			}
			pq.dequeue();
		}
		return sol;
	}

	// Sólo para evaluación continua / only for part time students
	public static <V,W> Set<Set<WeightedEdge<V,W>>> kruskals(WeightedGraph<V,W> g) {

		// COMPLETAR
		
		return null;
	}
}
