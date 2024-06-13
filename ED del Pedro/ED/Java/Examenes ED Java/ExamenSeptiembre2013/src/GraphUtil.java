/** ------------------------------------------------------------------------------
  * Estructuras de Datos. 2º Curso. ETSI Informática. UMA
  *
  * Control del día 4-9-2013
  * 
  * Diámetro de un grafo conexo 
  *
  * (completa y sustituye los siguientes datos)
  * Titulación: Grado en Ingeniería …………………………………… [Informática | del Software | de Computadores].
  *
  * Alumno: APELLIDOS, NOMBRE
  *
  * -------------------------------------------------------------------------------
  */

import dataStructures.graph.BreadthFirstTraversal;
import dataStructures.graph.Graph;

public class GraphUtil {

	/**
	 * LENGTH: Calcula el número de elementos que contiene un iterable
	 * 
	 * @param it  El iterador
	 * @return   Número de elementos en el iterador
	 */
	public static <T> int length(Iterable<T> it) {
		// TO DO
		int res = 0;

		for(T aux: it){
			res++;
		}

	    return res;
	}

	/**
	 * ECCENTRICITY: Calcula la excentricidad de un vértice en un grafo El algoritmo toma la
	 * longitud del camino máximo en un recorrido en profundidad del grafo
	 * comenzando en el vértice dado.
	 * 
	 * @param graph    Grafo
	 * @param v        Vértice del grafo
	 * @return         Excentricidad del vértice
	 */
	public static <T> int eccentricity(Graph<T> graph, T v) {
		// TO DO
		// Voy a iterar sobre todos los caminos mínimos y sacar sus longitudes. Luego de ello, sacamos la longitud máxima
		int maxLength = 0;
		BreadthFirstTraversal<T> bft = new BreadthFirstTraversal<>(graph,v);
		for(Iterable<T> aux: bft.paths()){
			int pathLength = length(aux)-1;
			if(pathLength >= maxLength){
				maxLength = pathLength;
			}
		}
	    return maxLength;
	}

	/**
	 * DIAMETER: Se define como la máxima excentricidad de los vértices del grafo.
	 * 
	 * @param graph
	 * @return
	 */

	public static <T> int diameter(Graph<T> graph) {
		// TO DO
		int maxEcc = 0;
		// Calcular las excentricidades de todos los vértices. Y mientras las saco, voy comparándolas hasta encontrar la mayor
		for(T aux: graph.vertices()){
			int eccVertex = eccentricity(graph,aux);
			if(eccVertex >= maxEcc){
				maxEcc = eccVertex;
			}
		}
	    return maxEcc;
	}
	
	/** 
	 * Estima y justifica la complejidad del método diameter
	 */
}
