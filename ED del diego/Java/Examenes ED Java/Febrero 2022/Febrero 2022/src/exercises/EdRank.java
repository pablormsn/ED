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
        diGraph = (DiGraph<T>) g.clone();
        rankDict = new AVLDictionary<>();
        for(T aux : diGraph.vertices()){
            rankDict.insert(aux,0.0);
        }
    }

    public void edRank(double rank) {
        //TODO
        for(T vertex: rankDict.keys()){
            distribute(vertex,rank);
        }
    }

    private void distribute(T node, double rank) {
        //TODO
        if(rank > THRESHOLD){
            double half = rank / (double) 2;
            rankDict.insert(node,rankDict.valueOf(node) + half);
            Set<T> ss = diGraph.successors(node);
            if(ss != null){
                double sRank = half / (double) ss.size();
                for(T vertex: ss){
                    distribute(vertex,sRank);
                }
            }
        }
    }

    public Dictionary<T, Double> edRank() {
        return rankDict;
    }
}
