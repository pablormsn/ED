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
    }

    public void edRank(double rank) {
        //TODO
    }

    private void distribute(T node, double rank) {
        //TODO
    }

    public Dictionary<T, Double> edRank() {
        return rankDict;
    }
}
