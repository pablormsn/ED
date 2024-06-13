
/**
 * Huffman trees and codes.
 *
 * Data Structures, Grado en Informatica. UMA.
 *
 *
 * Student's name:
 * Student's group:
 */

import dataStructures.dictionary.AVLDictionary;
import dataStructures.dictionary.Dictionary;
import dataStructures.list.LinkedList;
import dataStructures.list.List;
import dataStructures.priorityQueue.BinaryHeapPriorityQueue;
import dataStructures.priorityQueue.PriorityQueue;
import dataStructures.priorityQueue.WBLeftistHeapPriorityQueue;
import dataStructures.tuple.Tuple2;

import java.util.Calendar;
import java.util.Iterator;

public class Huffman {

    private static Integer frecuencia(Character c, String s){
        int we = 0;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == c){
                we++;
            }
        }
        return we;
    }

    // Exercise 1
    public static Dictionary<Character, Integer> weights(String s) {
    	//to do
        Dictionary<Character,Integer> d = new AVLDictionary<>();

        for(int i = 0; i < s.length(); i++){
            if(!d.isDefinedAt(s.charAt(i))){
                d.insert(s.charAt(i),frecuencia(s.charAt(i),s));
            }
        }

        return d;
    }

    // Exercise 2.a
    public static PriorityQueue<WLeafTree<Character>> huffmanLeaves(String s) {
    	//to do
        Dictionary<Character,Integer> d = weights(s);
        PriorityQueue<WLeafTree<Character>> leaves = new WBLeftistHeapPriorityQueue<>();
        for(Tuple2<Character,Integer> it: d.keysValues()){
            leaves.enqueue(new WLeafTree<>(it._1(), it._2()));
        }
        return leaves;
    }

    // Exercise 2.b
    public static WLeafTree<Character> huffmanTree(String s) {
    	//to do
        PriorityQueue<WLeafTree<Character>> leaves = huffmanLeaves(s);
        int sz = weights(s).size();
        if(sz < 2){
            return null;
        }else{
            WLeafTree<Character> wl1 = leaves.first();
            leaves.dequeue();
            WLeafTree<Character> wl2 = leaves.first();
            leaves.dequeue();
            WLeafTree res = new WLeafTree<>(wl1,wl2);
            sz --;
            while(sz > 1){
                wl1 = leaves.first();
                leaves.dequeue();
                res = new WLeafTree<>(res,wl1);
                sz--;
            }
            return res;
        }
    }

    // Exercise 3.a
    public static Dictionary<Character, List<Integer>> joinDics(Dictionary<Character, List<Integer>> d1, Dictionary<Character, List<Integer>> d2) {
        //to do
        // Añadiré los elementos del segundo diccionario en el primer diccionario
        Dictionary<Character,List<Integer>> d = new AVLDictionary<>();

        for(Tuple2<Character,List<Integer>> aux: d1.keysValues()){
            d.insert(aux._1(), aux._2());
        }

        for(Tuple2<Character,List<Integer>> aux: d2.keysValues()){
            d.insert(aux._1(), aux._2());
        }
    	return d;
    }

    // Exercise 3.b
    public static Dictionary<Character, List<Integer>> prefixWith(int i, Dictionary<Character, List<Integer>> d) {
        //to do
        // Tengo que recorrer el diccionario añadiendo al inicio de cada lista el prefijo especificado
        Iterator<Tuple2<Character,List<Integer>>> it = d.keysValues().iterator();
        while(it.hasNext()){
            it.next()._2().prepend(i);
        }
    	return d;
    }

    private static Dictionary<Character,List<Integer>> huffmanCodeRec(WLeafTree<Character> ht, List<Integer> l, Dictionary<Character,List<Integer>> d){
        if(ht.isLeaf()){
            d.insert(ht.elem(),l);
        }else{
            List<Integer> lLeft = new LinkedList<>();
            List<Integer> lRight = new LinkedList<>();

            for(Integer aux: l){
                lLeft.append(aux);
                lRight.append(aux);
            }


            lLeft.append(0);
            lRight.append(1);

            huffmanCodeRec(ht.leftChild(),lLeft,d);
            huffmanCodeRec(ht.rightChild(),lRight,d);
        }
        return d;
    }

    // Exercise 3.c
    public static Dictionary<Character, List<Integer>> huffmanCode(WLeafTree<Character> ht) {
        //to do
    	return huffmanCodeRec(ht,new LinkedList<Integer>(), new AVLDictionary<Character,List<Integer>>());
    }

    // Exercise 4
    public static List<Integer> encode(String s, Dictionary<Character, List<Integer>> hc) {
        //to do
        List<Integer> code = new LinkedList<>();
        for(int i = 0; i < s.length(); i++){
            List<Integer> value = hc.valueOf(s.charAt(i));
            for(Integer b: value){
                code.append(b);
            }
        }
    	return code;
    }

    private static char insertAndDelete(List<Integer> bs, WLeafTree<Character> ht){
        if(!ht.isLeaf()){
            if(bs.get(0) == 0){
                bs.remove(0);
                return insertAndDelete(bs,ht.leftChild());
            }else{
                bs.remove(0);
                return insertAndDelete(bs,ht.rightChild());
            }
        }else{
            return ht.elem();
        }
    }

    // Exercise 5
    public static String decode(List<Integer> bits, WLeafTree<Character> ht) {
        //to do
        // Creamos un nuevo string
        String s = "";
        while(!bits.isEmpty()){
            s += insertAndDelete(bits,ht);
        }
    	return s;
    }
}
