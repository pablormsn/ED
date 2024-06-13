/* ------------------------------------------------------------------------------
   -- Student's name:
   -- Student's group:
   -- Identity number (DNI if Spanish/passport if Erasmus):
   --
   -- Data Structures. Grado en Informática. UMA.
   -------------------------------------------------------------------------------
*/

package exercises;

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.graph.DiGraph;
import dataStructures.set.HashSet;
import dataStructures.set.Set;

public class DiGraphDftTimer<V> {
  
  private int time;
  private Dictionary<V, Integer> arrivalD, departureD;
  private Set<V> visited;

// -- ESCRIBE TU SOLUCIÓN DEBAJO ----------------------------------------------
// -- WRITE YOUR SOLUTION BELOW  ----------------------------------------------
// -- EXERCISE 3

  private int checkSuccessors(V v, DiGraph<V> graph){
    // Miramos sus sucesores
    Set<V> sucesores = graph.successors(v);
    if(!sucesores.isEmpty()){
      // Como los sucesores no están vacíos, itero
      for(V suc: sucesores){
        if(!visited.isElem(suc)){
          visited.insert(suc);
          arrivalD.insert(suc,time);
          ++time;
          time = checkSuccessors(suc,graph);
          time++;
        }
      }
    }
    departureD.insert(v,time);
    return time;
  }

  public DiGraphDftTimer(DiGraph<V> diGraphg) {
    // TODO
    // Primero debo pasar por cada vértice y debo guardar por cada uno el tiempo de llegada y tiempo de salida
    time = 0;
    visited = new HashSet<>();
    arrivalD = new HashDictionary<>();
    departureD = new HashDictionary<>();
    // Ahora tendría que iterar sobre el grafo
    for(V vertex: diGraphg.vertices()){
      // Miro si esta visitado o no, porque si esta visitado no hago nada
      if(!visited.isElem(vertex)){
        // Si no ha sido visitado lo miramos
        visited.insert(vertex);
        // Actualizo tiempo de llegada
        arrivalD.insert(vertex,time);
        ++time;
        time = checkSuccessors(vertex,diGraphg);
        departureD.insert(vertex,time);
        time++;
      }
    }
  }

  public int arrivalTime(V v) {
    return arrivalD.valueOf(v);
  }

  public int departureTime(V v) {
    return departureD.valueOf(v);
  }
}
