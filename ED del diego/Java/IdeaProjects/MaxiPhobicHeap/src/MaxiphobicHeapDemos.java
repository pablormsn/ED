
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class MaxiphobicHeapDemos {

    public static void main(String[] args) {
        drawStepwise("amasa");
        drawStepwise("murcielago");
    }

    private static void drawStepwise(String str) {
        MaxiphobicHeap<Character> h = new MaxiphobicHeap<Character>();
        for (int i = 0; i < str.length(); i++) {
            h.insert(str.charAt(i));
            saveTreeToDot("Seq-" + str.substring(0, i + 1), h);
        }
    }

    private static <T extends Comparable<? super T>> void saveTreeToDot(String name, MaxiphobicHeap<T> tree) {
        try (PrintWriter pw = new PrintWriter("dot/" + name + ".dot")) {
            pw.println(tree.toDot(name));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
