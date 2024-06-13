import dataStructures.graph.DiGraph;
import dataStructures.graph.DictionaryDiGraph;
import exercises.EdRank;

public class EdRankDemo {
    public static void main(String[] args) {
        DiGraph<Character> g3 = new DictionaryDiGraph<>(); // eulerian

        g3.addVertex('A');
        g3.addVertex('B');
        g3.addVertex('C');

        g3.addDiEdge('A', 'B');
        g3.addDiEdge('B', 'C');
        g3.addDiEdge('C', 'A');

        EdRank<Character> pwg3 = new EdRank<>(g3);
        pwg3.edRank(1);
        System.out.println("Test for digraph g3");
        System.out.println(pwg3.edRank());

        DiGraph<Character> g4 = new DictionaryDiGraph<>();

        g4.addVertex('A');
        g4.addVertex('B');
        g4.addVertex('C');
        g4.addVertex('D');
        g4.addVertex('E');
        g4.addVertex('F');
        g4.addVertex('G');
        g4.addVertex('H');

        g4.addDiEdge('A', 'C');
        g4.addDiEdge('A', 'D');
        g4.addDiEdge('B', 'C');
        g4.addDiEdge('B', 'F');
        g4.addDiEdge('D', 'F');
        g4.addDiEdge('E', 'B');
        g4.addDiEdge('E', 'H');
        g4.addDiEdge('F', 'G');
        g4.addDiEdge('F', 'H');
        g4.addDiEdge('G', 'E');
        g4.addDiEdge('G', 'H');

        EdRank<Character> pwg4 = new EdRank<>(g4);
        pwg4.edRank(1);
        System.out.println("Test for digraph g4");
        System.out.println(pwg4.edRank());


        DiGraph<Character> g5 = new DictionaryDiGraph<>();

        for (char c = 'A'; c <= 'E'; c++) {
            g5.addVertex(c);
        }

        g5.addDiEdge('A', 'C');
        g5.addDiEdge('A', 'D');
        g5.addDiEdge('B', 'C');
        g5.addDiEdge('B', 'E');
        g5.addDiEdge('C', 'D');
        g5.addDiEdge('C', 'E');
        g5.addDiEdge('C', 'A');


        EdRank<Character> pwg5 = new EdRank<>(g5);
        pwg5.edRank(1);
        System.out.println("Test for digraph g5");
        System.out.println(pwg5.edRank());


        DiGraph<Character> g6 = new DictionaryDiGraph<>(); // eulerian

        for (char c = 'A'; c <= 'F'; c++) {
            g6.addVertex(c);
        }

        g6.addDiEdge('A', 'B');
        g6.addDiEdge('A', 'E');
        g6.addDiEdge('B', 'C');
        g6.addDiEdge('B', 'D');
        g6.addDiEdge('B', 'E');
        g6.addDiEdge('C', 'D');
        g6.addDiEdge('C', 'E');
        g6.addDiEdge('C', 'F');
        g6.addDiEdge('D', 'E');
        g6.addDiEdge('D', 'F');

        EdRank<Character> pwg6 = new EdRank<>(g6);
        pwg6.edRank(1);
        System.out.println("Test for digraph g6");
        System.out.println(pwg6.edRank());

        DiGraph<Character> g7 = new DictionaryDiGraph<>(); // eulerian

        for (char c = 'A'; c <= 'G'; c++) {
            g7.addVertex(c);
        }

        g7.addDiEdge('A', 'B');
        g7.addDiEdge('A', 'C');
        g7.addDiEdge('B', 'A');
        g7.addDiEdge('B', 'C');
        g7.addDiEdge('B', 'D');
        g7.addDiEdge('C', 'A');
        g7.addDiEdge('C', 'B');
        g7.addDiEdge('C', 'E');
        g7.addDiEdge('D', 'B');
        g7.addDiEdge('D', 'F');
        g7.addDiEdge('D', 'E');
        g7.addDiEdge('E', 'C');
        g7.addDiEdge('E', 'D');
        g7.addDiEdge('E', 'G');
        g7.addDiEdge('F', 'D');
        g7.addDiEdge('G', 'E');

        EdRank<Character> pwg7 = new EdRank<>(g7);
        pwg7.edRank(1);
        System.out.println("Test for digraph g7");
        System.out.println(pwg7.edRank());

        DiGraph<Character> g10 = new DictionaryDiGraph<>(); // eulerian

        for (char c = 'A'; c <= 'J'; c++) {
            g10.addVertex(c);
        }

        g10.addDiEdge('A', 'E');
        g10.addDiEdge('A', 'J');
        g10.addDiEdge('A', 'I');
        g10.addDiEdge('B', 'A');
        g10.addDiEdge('B', 'I');
        g10.addDiEdge('B', 'H');
        g10.addDiEdge('C', 'D');
        g10.addDiEdge('C', 'G');
        g10.addDiEdge('C', 'H');
        g10.addDiEdge('D', 'E');
        g10.addDiEdge('D', 'F');
        g10.addDiEdge('D', 'G');
        g10.addDiEdge('E', 'D');
        g10.addDiEdge('E', 'F');
        g10.addDiEdge('E', 'J');
        g10.addDiEdge('F', 'E');
        g10.addDiEdge('F', 'D');
        g10.addDiEdge('F', 'G');
        g10.addDiEdge('G', 'D');
        g10.addDiEdge('G', 'C');
        g10.addDiEdge('G', 'H');
        g10.addDiEdge('H', 'G');
        g10.addDiEdge('H', 'I');
        g10.addDiEdge('H', 'C');
        g10.addDiEdge('I', 'A');
        g10.addDiEdge('I', 'B');
        g10.addDiEdge('I', 'J');
        g10.addDiEdge('J', 'E');
        g10.addDiEdge('J', 'F');
        g10.addDiEdge('J', 'A');

        EdRank<Character> pwg10 = new EdRank<>(g10);
        pwg10.edRank(1);
        System.out.println("Test for digraph g10");
        System.out.println(pwg10.edRank());
    }
}
