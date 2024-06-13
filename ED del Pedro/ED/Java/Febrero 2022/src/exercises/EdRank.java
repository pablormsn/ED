/* ------------------------------------------------------------------------------
   -- Student's name:
   -- Student's group:
   -- Identity number (DNI if Spanish/passport if Erasmus):
   --
   -- Data Structures. Grado en Informática. UMA.
   -------------------------------------------------------------------------------
*/

package exercises;

import dataStructures.dictionary.AVLDictionary;
import dataStructures.dictionary.Dictionary;
import dataStructures.graph.DiGraph;
import dataStructures.set.Set;

public class EdRank<T extends Comparable<? super T>> {
    private static final double THRESHOLD = 0.0001;
    private DiGraph<T> diGraph;
    private Dictionary<T, Double> rankDict;

    public EdRank(DiGraph<T> g) {
        // TODO
        diGraph = (DiGraph<T>) g.clone();
        rankDict = new AVLDictionary<>();
        for(T vertex: g.vertices()){
            rankDict.insert(vertex,0.0);
        }
    }

    public void edRank(double rank) {
        //TODO
        for(T vertex: diGraph.vertices()){
            distribute(vertex,rank);
        }
    }

    private void distribute(T node, double rank) {
        //TODO
        if(rank > THRESHOLD){
            double valCurrent = rankDict.valueOf(node);
            rank /= 2.0;
            rankDict.insert(node,valCurrent+rank);
            Set<T> sucesores = diGraph.successors(node);
            if(!sucesores.isEmpty()){
                rank /= (double) sucesores.size();
                for(T suc: sucesores){
                    distribute(suc,rank);
                }
            }
        }
    }

    public Dictionary<T, Double> edRank() {
        return rankDict;
    }
}
